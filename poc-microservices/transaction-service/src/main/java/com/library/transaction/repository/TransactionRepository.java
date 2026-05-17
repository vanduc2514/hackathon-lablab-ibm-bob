package com.library.transaction.repository;

import com.library.transaction.model.Transaction;
import com.library.transaction.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Transaction entity
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find transactions by student ID
     * @param studentId Student ID
     * @return List of transactions
     */
    List<Transaction> findByStudentId(Long studentId);

    /**
     * Find transactions by book ID
     * @param bookId Book ID
     * @return List of transactions
     */
    List<Transaction> findByBookId(Long bookId);

    /**
     * Find transactions by status
     * @param status Transaction status
     * @return List of transactions
     */
    List<Transaction> findByStatus(TransactionStatus status);

    /**
     * Find active transaction for a book
     * @param bookId Book ID
     * @param status Transaction status (ISSUED)
     * @return Optional transaction
     */
    Optional<Transaction> findByBookIdAndStatus(Long bookId, TransactionStatus status);

    /**
     * Find active transactions for a student
     * @param studentId Student ID
     * @param status Transaction status (ISSUED)
     * @return List of transactions
     */
    List<Transaction> findByStudentIdAndStatus(Long studentId, TransactionStatus status);

    /**
     * Find overdue transactions
     * @param status Transaction status (ISSUED)
     * @param currentDate Current date
     * @return List of overdue transactions
     */
    List<Transaction> findByStatusAndDueDateBefore(TransactionStatus status, LocalDateTime currentDate);

    /**
     * Count active transactions for a student
     * @param studentId Student ID
     * @param status Transaction status (ISSUED)
     * @return Count of active transactions
     */
    long countByStudentIdAndStatus(Long studentId, TransactionStatus status);
}

// Made with Bob
