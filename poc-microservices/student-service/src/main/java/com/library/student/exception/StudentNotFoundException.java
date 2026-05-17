package com.library.student.exception;

/**
 * Student Not Found Exception
 * 
 * Thrown when a student is not found in the database.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message) {
        super(message);
    }
}

// Made with Bob
