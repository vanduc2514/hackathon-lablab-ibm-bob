package com.library.transaction.e2e;

import com.library.transaction.client.BookServiceClient;
import com.library.transaction.client.StudentServiceClient;
import com.library.transaction.dto.IssueBookRequest;
import com.library.transaction.dto.ReturnBookRequest;
import com.library.transaction.dto.TransactionDTO;
import com.library.transaction.model.TransactionStatus;
import com.library.transaction.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * End-to-End tests for complete book issue and return flow
 */
@SpringBootTest
@Transactional
@DisplayName("E2E: Issue and Return Book Flow")
class IssueReturnBookE2ETest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private StudentServiceClient studentServiceClient;

    @MockBean
    private BookServiceClient bookServiceClient;

    @Test
    @DisplayName("E2E: Complete issue and return book flow")
    void testCompleteIssueReturnFlow() {
        // Setup: Mock external service calls
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(true);
        when(bookServiceClient.isBookAvailable(anyLong())).thenReturn(true);
        doNothing().when(bookServiceClient).updateBookAvailability(anyLong(), anyBoolean());

        // Step 1: Issue book
        IssueBookRequest issueRequest = IssueBookRequest.builder()
                .studentId(1L)
                .bookId(1L)
                .remarks("E2E test issue")
                .build();

        TransactionDTO issuedTransaction = transactionService.issueBook(issueRequest);

        // Verify issue
        assertThat(issuedTransaction).isNotNull();
        assertThat(issuedTransaction.getId()).isNotNull();
        assertThat(issuedTransaction.getStatus()).isEqualTo(TransactionStatus.ISSUED);
        assertThat(issuedTransaction.getStudentId()).isEqualTo(1L);
        assertThat(issuedTransaction.getBookId()).isEqualTo(1L);

        // Verify Saga steps for issue
        verify(studentServiceClient).isCardActive(1L);
        verify(bookServiceClient).isBookAvailable(1L);
        verify(bookServiceClient).updateBookAvailability(1L, false);

        // Step 2: Return book
        ReturnBookRequest returnRequest = ReturnBookRequest.builder()
                .transactionId(issuedTransaction.getId())
                .remarks("E2E test return")
                .build();

        TransactionDTO returnedTransaction = transactionService.returnBook(returnRequest);

        // Verify return
        assertThat(returnedTransaction).isNotNull();
        assertThat(returnedTransaction.getId()).isEqualTo(issuedTransaction.getId());
        assertThat(returnedTransaction.getStatus()).isEqualTo(TransactionStatus.RETURNED);
        assertThat(returnedTransaction.getReturnDate()).isNotNull();

        // Verify Saga steps for return
        verify(bookServiceClient).updateBookAvailability(1L, true);

        // Verify complete flow
        assertThat(returnedTransaction.getIssueDate()).isNotNull();
        assertThat(returnedTransaction.getReturnDate()).isAfter(returnedTransaction.getIssueDate());
    }

    @Test
    @DisplayName("E2E: Issue book with inactive card should fail")
    void testIssueBookWithInactiveCard() {
        // Setup: Student card is not active
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(false);

        // Attempt to issue book
        IssueBookRequest issueRequest = IssueBookRequest.builder()
                .studentId(1L)
                .bookId(1L)
                .build();

        // Verify failure
        try {
            transactionService.issueBook(issueRequest);
            assert false : "Should have thrown SagaException";
        } catch (Exception e) {
            assertThat(e.getMessage()).contains("card is not active");
        }

        // Verify no book availability update
        verify(bookServiceClient, never()).updateBookAvailability(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("E2E: Issue unavailable book should fail")
    void testIssueUnavailableBook() {
        // Setup: Book is not available
        when(studentServiceClient.isCardActive(anyLong())).thenReturn(true);
        when(bookServiceClient.isBookAvailable(anyLong())).thenReturn(false);

        // Attempt to issue book
        IssueBookRequest issueRequest = IssueBookRequest.builder()
                .studentId(1L)
                .bookId(1L)
                .build();

        // Verify failure
        try {
            transactionService.issueBook(issueRequest);
            assert false : "Should have thrown SagaException";
        } catch (Exception e) {
            assertThat(e.getMessage()).contains("not available");
        }

        // Verify no book availability update
        verify(bookServiceClient, never()).updateBookAvailability(anyLong(), anyBoolean());
    }
}

// Made with Bob
