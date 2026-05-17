package com.library.student.exception;

/**
 * Duplicate Email Exception
 * 
 * Thrown when attempting to create/update a student with an email that already exists.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
public class DuplicateEmailException extends RuntimeException {
    
    public DuplicateEmailException(String message) {
        super(message);
    }
}

// Made with Bob
