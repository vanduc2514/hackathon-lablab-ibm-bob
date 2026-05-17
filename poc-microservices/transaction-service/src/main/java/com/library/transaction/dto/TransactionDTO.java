package com.library.transaction.dto;

import com.library.transaction.model.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    private TransactionStatus status;

    private LocalDateTime issueDate;

    private LocalDateTime returnDate;

    private LocalDateTime dueDate;

    private Double fineAmount;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

// Made with Bob
