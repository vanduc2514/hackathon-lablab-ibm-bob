package com.library.transaction.service;

import com.library.transaction.client.BookServiceClient;
import com.library.transaction.client.StudentServiceClient;
import com.library.transaction.dto.IssueBookRequest;
import com.library.transaction.dto.ReturnBookRequest;
import com.library.transaction.dto.TransactionDTO;
import com.library.transaction.exception.SagaException;
import com.library.transaction.exception.TransactionNotFoundException;
import com.library.transaction.model.Transaction;
import com.library.transaction.model.TransactionStatus;
import com.library.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Transaction business logic with Saga orchestration
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StudentServiceClient studentServiceClient;
    private final BookServiceClient bookServiceClient;

    /**
     * Issue book to student using Saga orchestration
     * Saga Steps:
     * 1. Validate student card is active
     * 2. Check book availability
     * 3. Create transaction
     * 4. Update book availability to false
     * 
     * @param request Issue book request
     * @return Created transaction DTO
     */
    public TransactionDTO issueBook(IssueBookRequest request) {
        log.info("Starting Saga: Issue book {} to student {}", request.getBookId(), request.getStudentId());
        
        Transaction transaction = null;
        boolean bookUpdated = false;
        
        try {
            // Step 1: Validate student card is active
            log.info("Saga Step 1: Validating student card for student ID: {}", request.getStudentId());
            Boolean isCardActive = studentServiceClient.isCardActive(request.getStudentId());
            if (!isCardActive) {
                throw new SagaException("Student card is not active for student ID: " + request.getStudentId());
            }
            log.info("Saga Step 1: Student card is active");
            
            // Step 2: Check book availability
            log.info("Saga Step 2: Checking book availability for book ID: {}", request.getBookId());
            Boolean isBookAvailable = bookServiceClient.isBookAvailable(request.getBookId());
            if (!isBookAvailable) {
                throw new SagaException("Book is not available for book ID: " + request.getBookId());
            }
            log.info("Saga Step 2: Book is available");
            
            // Step 3: Create transaction
            log.info("Saga Step 3: Creating transaction");
            transaction = Transaction.builder()
                    .studentId(request.getStudentId())
                    .bookId(request.getBookId())
                    .status(TransactionStatus.ISSUED)
                    .issueDate(LocalDateTime.now())
                    .dueDate(LocalDateTime.now().plusDays(14))
                    .remarks(request.getRemarks())
                    .build();
            
            transaction = transactionRepository.save(transaction);
            log.info("Saga Step 3: Transaction created with ID: {}", transaction.getId());
            
            // Step 4: Update book availability
            log.info("Saga Step 4: Updating book availability to false");
            bookServiceClient.updateBookAvailability(request.getBookId(), false);
            bookUpdated = true;
            log.info("Saga Step 4: Book availability updated");
            
            log.info("Saga completed successfully: Transaction ID: {}", transaction.getId());
            return convertToDTO(transaction);
            
        } catch (Exception e) {
            log.error("Saga failed: {}", e.getMessage());
            
            // Compensation: Rollback changes
            if (transaction != null && transaction.getId() != null) {
                log.info("Compensation: Deleting transaction ID: {}", transaction.getId());
                transactionRepository.delete(transaction);
            }
            
            if (bookUpdated) {
                log.info("Compensation: Restoring book availability");
                try {
                    bookServiceClient.updateBookAvailability(request.getBookId(), true);
                } catch (Exception ex) {
                    log.error("Compensation failed: Could not restore book availability", ex);
                }
            }
            
            throw new SagaException("Failed to issue book: " + e.getMessage(), e);
        }
    }

    /**
     * Return book using Saga orchestration
     * Saga Steps:
     * 1. Validate transaction exists and is ISSUED
     * 2. Calculate fine if overdue
     * 3. Update transaction status to RETURNED
     * 4. Update book availability to true
     * 
     * @param request Return book request
     * @return Updated transaction DTO
     */
    public TransactionDTO returnBook(ReturnBookRequest request) {
        log.info("Starting Saga: Return book for transaction ID: {}", request.getTransactionId());
        
        Transaction originalTransaction = null;
        boolean bookUpdated = false;
        
        try {
            // Step 1: Validate transaction exists and is ISSUED
            log.info("Saga Step 1: Validating transaction ID: {}", request.getTransactionId());
            Transaction transaction = transactionRepository.findById(request.getTransactionId())
                    .orElseThrow(() -> new TransactionNotFoundException(request.getTransactionId()));
            
            if (transaction.getStatus() != TransactionStatus.ISSUED) {
                throw new SagaException("Transaction is not in ISSUED status: " + transaction.getStatus());
            }
            
            // Store original state for compensation
            originalTransaction = Transaction.builder()
                    .id(transaction.getId())
                    .status(transaction.getStatus())
                    .returnDate(transaction.getReturnDate())
                    .fineAmount(transaction.getFineAmount())
                    .build();
            
            log.info("Saga Step 1: Transaction validated");
            
            // Step 2: Calculate fine if overdue
            log.info("Saga Step 2: Calculating fine");
            double fine = transaction.calculateFine();
            if (fine > 0) {
                log.info("Saga Step 2: Book is overdue. Fine calculated: ${}", fine);
                transaction.setFineAmount(fine);
                transaction.setStatus(TransactionStatus.OVERDUE);
            }
            
            // Step 3: Update transaction status
            log.info("Saga Step 3: Updating transaction status to RETURNED");
            transaction.setStatus(TransactionStatus.RETURNED);
            transaction.setReturnDate(LocalDateTime.now());
            if (request.getRemarks() != null) {
                transaction.setRemarks(transaction.getRemarks() + " | " + request.getRemarks());
            }
            
            transaction = transactionRepository.save(transaction);
            log.info("Saga Step 3: Transaction updated");
            
            // Step 4: Update book availability
            log.info("Saga Step 4: Updating book availability to true");
            bookServiceClient.updateBookAvailability(transaction.getBookId(), true);
            bookUpdated = true;
            log.info("Saga Step 4: Book availability updated");
            
            log.info("Saga completed successfully: Transaction ID: {}", transaction.getId());
            return convertToDTO(transaction);
            
        } catch (Exception e) {
            log.error("Saga failed: {}", e.getMessage());
            
            // Compensation: Rollback changes
            if (originalTransaction != null && originalTransaction.getId() != null) {
                log.info("Compensation: Restoring transaction to original state");
                Transaction transaction = transactionRepository.findById(originalTransaction.getId())
                        .orElse(null);
                if (transaction != null) {
                    transaction.setStatus(originalTransaction.getStatus());
                    transaction.setReturnDate(originalTransaction.getReturnDate());
                    transaction.setFineAmount(originalTransaction.getFineAmount());
                    transactionRepository.save(transaction);
                }
            }
            
            if (bookUpdated) {
                log.info("Compensation: Restoring book availability to false");
                try {
                    Transaction transaction = transactionRepository.findById(request.getTransactionId())
                            .orElse(null);
                    if (transaction != null) {
                        bookServiceClient.updateBookAvailability(transaction.getBookId(), false);
                    }
                } catch (Exception ex) {
                    log.error("Compensation failed: Could not restore book availability", ex);
                }
            }
            
            throw new SagaException("Failed to return book: " + e.getMessage(), e);
        }
    }

    /**
     * Get transaction by ID
     * @param id Transaction ID
     * @return Transaction DTO
     */
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(Long id) {
        log.info("Fetching transaction with ID: {}", id);
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return convertToDTO(transaction);
    }

    /**
     * Get transactions by student ID
     * @param studentId Student ID
     * @return List of transaction DTOs
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByStudentId(Long studentId) {
        log.info("Fetching transactions for student ID: {}", studentId);
        return transactionRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get transactions by book ID
     * @param bookId Book ID
     * @return List of transaction DTOs
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByBookId(Long bookId) {
        log.info("Fetching transactions for book ID: {}", bookId);
        return transactionRepository.findByBookId(bookId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get active transactions
     * @return List of active transaction DTOs
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getActiveTransactions() {
        log.info("Fetching active transactions");
        return transactionRepository.findByStatus(TransactionStatus.ISSUED).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get overdue transactions
     * @return List of overdue transaction DTOs
     */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getOverdueTransactions() {
        log.info("Fetching overdue transactions");
        return transactionRepository.findByStatusAndDueDateBefore(
                TransactionStatus.ISSUED, 
                LocalDateTime.now()
        ).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Transaction entity to DTO
     * @param transaction Transaction entity
     * @return Transaction DTO
     */
    private TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .studentId(transaction.getStudentId())
                .bookId(transaction.getBookId())
                .status(transaction.getStatus())
                .issueDate(transaction.getIssueDate())
                .returnDate(transaction.getReturnDate())
                .dueDate(transaction.getDueDate())
                .fineAmount(transaction.getFineAmount())
                .remarks(transaction.getRemarks())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}

// Made with Bob
