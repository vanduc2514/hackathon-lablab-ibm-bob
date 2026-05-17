package com.library.book.exception;

/**
 * Exception thrown when a book is not found
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book not found with ID: " + id);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}

// Made with Bob
