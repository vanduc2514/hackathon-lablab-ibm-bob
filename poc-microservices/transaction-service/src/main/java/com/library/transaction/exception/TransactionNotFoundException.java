package com.library.transaction.exception;

/**
 * Exception thrown when a transaction is not found
 */
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Transaction not found with ID: " + id);
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}

// Made with Bob
