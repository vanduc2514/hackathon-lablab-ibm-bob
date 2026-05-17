package com.library.transaction.exception;

/**
 * Exception thrown when Saga orchestration fails
 */
public class SagaException extends RuntimeException {

    public SagaException(String message) {
        super(message);
    }

    public SagaException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Made with Bob
