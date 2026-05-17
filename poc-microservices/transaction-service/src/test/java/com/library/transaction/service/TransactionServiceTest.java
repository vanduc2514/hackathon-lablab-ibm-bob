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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionService with Saga orchestration
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction Service Tests")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private StudentServiceClient studentServiceClient;

    @Mock
    private BookServiceClient bookServiceClient;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction testTransaction;
    private IssueBookRequest issueBookRequest;
    private ReturnBookRequest returnBookRequest;

    @BeforeEach
    void setUp() {
        testTransaction = Transaction.builder()
                .id(1L)
                .studentId(1L)
                .bookId(1L)
                .status(TransactionStatus.ISSUED)
                .issueDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(14))
                .fineAmount(0.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        issueBookRequest = IssueBookRequest.builder()
                .studentId(1L)
                .bookId(1L)
                .remarks("Test issue")
                .build();

        returnBookRequest = ReturnBookRequest.builder()
                .transactionId(1L)
                .remarks("Test return")
                .build();
    }

    @Test
    @DisplayName("Should issue book successfully with Saga")
    void testIssueBook_Success() {
        // Given
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(true);
        when(bookServiceClient.isBookAvailable(anyLong())).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);
        doNothing().when(bookServiceClient).updateBookAvailability(anyLong(), anyBoolean());

        // When
        TransactionDTO result = transactionService.issueBook(issueBookRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStudentId()).isEqualTo(1L);
        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(TransactionStatus.ISSUED);

        // Verify Saga steps
        verify(studentServiceClient).isCardActive(1L);
        verify(bookServiceClient).isBookAvailable(1L);
        verify(transactionRepository).save(any(Transaction.class));
        verify(bookServiceClient).updateBookAvailability(1L, false);
    }

    @Test
    @DisplayName("Should fail Saga when student card is not active")
    void testIssueBook_CardNotActive() {
        // Given
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> transactionService.issueBook(issueBookRequest))
                .isInstanceOf(SagaException.class)
                .hasMessageContaining("card is not active");

        // Verify Saga stopped at step 1
        verify(studentServiceClient).isCardActive(1L);
        verify(bookServiceClient, never()).isBookAvailable(anyLong());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should fail Saga when book is not available")
    void testIssueBook_BookNotAvailable() {
        // Given
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(true);
        when(bookServiceClient.isBookAvailable(anyLong())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> transactionService.issueBook(issueBookRequest))
                .isInstanceOf(SagaException.class)
                .hasMessageContaining("not available");

        // Verify Saga stopped at step 2
        verify(studentServiceClient).isCardActive(1L);
        verify(bookServiceClient).isBookAvailable(1L);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should compensate when book availability update fails")
    void testIssueBook_CompensationOnFailure() {
        // Given
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(true);
        when(bookServiceClient.isBookAvailable(anyLong())).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);
        doThrow(new RuntimeException("Service unavailable"))
                .when(bookServiceClient).updateBookAvailability(anyLong(), anyBoolean());

        // When & Then
        assertThatThrownBy(() -> transactionService.issueBook(issueBookRequest))
                .isInstanceOf(SagaException.class);

        // Verify compensation: transaction deleted
        verify(transactionRepository).delete(any(Transaction.class));
    }

    @Test
    @DisplayName("Should return book successfully with Saga")
    void testReturnBook_Success() {
        // Given
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);
        doNothing().when(bookServiceClient).updateBookAvailability(anyLong(), anyBoolean());

        // When
        TransactionDTO result = transactionService.returnBook(returnBookRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(TransactionStatus.RETURNED);

        // Verify Saga steps
        verify(transactionRepository).findById(1L);
        verify(transactionRepository).save(any(Transaction.class));
        verify(bookServiceClient).updateBookAvailability(1L, true);
    }

    @Test
    @DisplayName("Should calculate fine for overdue book")
    void testReturnBook_WithFine() {
        // Given
        testTransaction.setDueDate(LocalDateTime.now().minusDays(5)); // 5 days overdue
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);
        doNothing().when(bookServiceClient).updateBookAvailability(anyLong(), anyBoolean());

        // When
        TransactionDTO result = transactionService.returnBook(returnBookRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFineAmount()).isGreaterThan(0);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should fail when transaction not found")
    void testReturnBook_TransactionNotFound() {
        // Given
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> transactionService.returnBook(returnBookRequest))
                .isInstanceOf(TransactionNotFoundException.class);

        verify(transactionRepository).findById(1L);
        verify(bookServiceClient, never()).updateBookAvailability(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("Should fail when transaction is not in ISSUED status")
    void testReturnBook_InvalidStatus() {
        // Given
        testTransaction.setStatus(TransactionStatus.RETURNED);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));

        // When & Then
        assertThatThrownBy(() -> transactionService.returnBook(returnBookRequest))
                .isInstanceOf(SagaException.class)
                .hasMessageContaining("not in ISSUED status");

        verify(transactionRepository).findById(1L);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Should get transaction by ID")
    void testGetTransactionById() {
        // Given
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));

        // When
        TransactionDTO result = transactionService.getTransactionById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(transactionRepository).findById(1L);
    }

    @Test
    @DisplayName("Should get transactions by student ID")
    void testGetTransactionsByStudentId() {
        // Given
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByStudentId(1L)).thenReturn(transactions);

        // When
        List<TransactionDTO> result = transactionService.getTransactionsByStudentId(1L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudentId()).isEqualTo(1L);
        verify(transactionRepository).findByStudentId(1L);
    }

    @Test
    @DisplayName("Should get transactions by book ID")
    void testGetTransactionsByBookId() {
        // Given
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByBookId(1L)).thenReturn(transactions);

        // When
        List<TransactionDTO> result = transactionService.getTransactionsByBookId(1L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBookId()).isEqualTo(1L);
        verify(transactionRepository).findByBookId(1L);
    }

    @Test
    @DisplayName("Should get active transactions")
    void testGetActiveTransactions() {
        // Given
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByStatus(TransactionStatus.ISSUED)).thenReturn(transactions);

        // When
        List<TransactionDTO> result = transactionService.getActiveTransactions();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(TransactionStatus.ISSUED);
        verify(transactionRepository).findByStatus(TransactionStatus.ISSUED);
    }

    @Test
    @DisplayName("Should get overdue transactions")
    void testGetOverdueTransactions() {
        // Given
        testTransaction.setDueDate(LocalDateTime.now().minusDays(1));
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByStatusAndDueDateBefore(
                eq(TransactionStatus.ISSUED), any(LocalDateTime.class)))
                .thenReturn(transactions);

        // When
        List<TransactionDTO> result = transactionService.getOverdueTransactions();

        // Then
        assertThat(result).hasSize(1);
        verify(transactionRepository).findByStatusAndDueDateBefore(
                eq(TransactionStatus.ISSUED), any(LocalDateTime.class));
    }
}

// Made with Bob
