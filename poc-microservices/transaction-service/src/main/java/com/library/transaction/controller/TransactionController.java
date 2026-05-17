package com.library.transaction.controller;

import com.library.transaction.dto.IssueBookRequest;
import com.library.transaction.dto.ReturnBookRequest;
import com.library.transaction.dto.TransactionDTO;
import com.library.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Transaction Service
 * Handles all transaction-related HTTP requests with Saga orchestration
 */
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Issue book to student (Saga orchestration)
     * @param request Issue book request
     * @return Created transaction with HTTP 201
     */
    @PostMapping("/issue")
    public ResponseEntity<TransactionDTO> issueBook(@Valid @RequestBody IssueBookRequest request) {
        log.info("Issuing book {} to student {}", request.getBookId(), request.getStudentId());
        TransactionDTO transaction = transactionService.issueBook(request);
        log.info("Book issued successfully. Transaction ID: {}", transaction.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    /**
     * Return book (Saga orchestration)
     * @param request Return book request
     * @return Updated transaction with HTTP 200
     */
    @PostMapping("/return")
    public ResponseEntity<TransactionDTO> returnBook(@Valid @RequestBody ReturnBookRequest request) {
        log.info("Returning book for transaction ID: {}", request.getTransactionId());
        TransactionDTO transaction = transactionService.returnBook(request);
        log.info("Book returned successfully. Transaction ID: {}", transaction.getId());
        return ResponseEntity.ok(transaction);
    }

    /**
     * Get transaction by ID
     * @param id Transaction ID
     * @return Transaction data with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        log.info("Fetching transaction with ID: {}", id);
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    /**
     * Get transactions by student ID
     * @param studentId Student ID
     * @return List of transactions with HTTP 200
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStudent(@PathVariable Long studentId) {
        log.info("Fetching transactions for student ID: {}", studentId);
        List<TransactionDTO> transactions = transactionService.getTransactionsByStudentId(studentId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions by book ID
     * @param bookId Book ID
     * @return List of transactions with HTTP 200
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByBook(@PathVariable Long bookId) {
        log.info("Fetching transactions for book ID: {}", bookId);
        List<TransactionDTO> transactions = transactionService.getTransactionsByBookId(bookId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get all active transactions
     * @return List of active transactions with HTTP 200
     */
    @GetMapping("/active")
    public ResponseEntity<List<TransactionDTO>> getActiveTransactions() {
        log.info("Fetching active transactions");
        List<TransactionDTO> transactions = transactionService.getActiveTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get all overdue transactions
     * @return List of overdue transactions with HTTP 200
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<TransactionDTO>> getOverdueTransactions() {
        log.info("Fetching overdue transactions");
        List<TransactionDTO> transactions = transactionService.getOverdueTransactions();
        return ResponseEntity.ok(transactions);
    }
}

// Made with Bob
