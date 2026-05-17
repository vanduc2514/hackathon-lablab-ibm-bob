package com.library.book.exception;

/**
 * Exception thrown when an author is not found
 */
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {
        super("Author not found with ID: " + id);
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}

// Made with Bob
