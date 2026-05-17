package com.library.transaction.model;

/**
 * Transaction Status Enumeration
 */
public enum TransactionStatus {
    ISSUED,      // Book has been issued to student
    RETURNED,    // Book has been returned
    OVERDUE,     // Book return is overdue
    CANCELLED    // Transaction was cancelled/compensated
}

// Made with Bob
