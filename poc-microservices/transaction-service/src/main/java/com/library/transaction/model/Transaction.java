package com.library.transaction.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Transaction Entity
 * Represents a book transaction (issue/return) in the library system
 */
@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull(message = "Book ID is required")
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @NotNull(message = "Transaction status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "fine_amount")
    @Builder.Default
    private Double fineAmount = 0.0;

    @Column(length = 500)
    private String remarks;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (issueDate == null) {
            issueDate = LocalDateTime.now();
        }
        if (dueDate == null) {
            // Default: 14 days from issue date
            dueDate = issueDate.plusDays(14);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Check if transaction is overdue
     * @return true if overdue
     */
    public boolean isOverdue() {
        return status == TransactionStatus.ISSUED && 
               LocalDateTime.now().isAfter(dueDate);
    }

    /**
     * Calculate fine for overdue transaction
     * Fine: $1 per day overdue
     * @return fine amount
     */
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        long daysOverdue = java.time.Duration.between(dueDate, LocalDateTime.now()).toDays();
        return daysOverdue * 1.0; // $1 per day
    }
}

// Made with Bob
