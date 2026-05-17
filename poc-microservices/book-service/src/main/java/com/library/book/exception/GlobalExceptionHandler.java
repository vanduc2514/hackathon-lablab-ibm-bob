package com.library.book.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for Book Service
 * Handles all exceptions and returns RFC 7807 Problem Details
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle BookNotFoundException
     * @param ex Exception
     * @return Problem detail with HTTP 404
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleBookNotFoundException(BookNotFoundException ex) {
        log.error("Book not found: {}", ex.getMessage());
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Book Not Found");
        problemDetail.setType(URI.create("https://api.library.com/errors/book-not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    /**
     * Handle AuthorNotFoundException
     * @param ex Exception
     * @return Problem detail with HTTP 404
     */
    @ExceptionHandler(AuthorNotFoundException.class)
    public ProblemDetail handleAuthorNotFoundException(AuthorNotFoundException ex) {
        log.error("Author not found: {}", ex.getMessage());
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        problemDetail.setTitle("Author Not Found");
        problemDetail.setType(URI.create("https://api.library.com/errors/author-not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    /**
     * Handle DuplicateIsbnException
     * @param ex Exception
     * @return Problem detail with HTTP 409
     */
    @ExceptionHandler(DuplicateIsbnException.class)
    public ProblemDetail handleDuplicateIsbnException(DuplicateIsbnException ex) {
        log.error("Duplicate ISBN: {}", ex.getMessage());
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
        problemDetail.setTitle("Duplicate ISBN");
        problemDetail.setType(URI.create("https://api.library.com/errors/duplicate-isbn"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    /**
     * Handle validation errors
     * @param ex Exception
     * @return Problem detail with HTTP 400 and field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields"
        );
        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create("https://api.library.com/errors/validation-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", errors);
        
        return problemDetail;
    }

    /**
     * Handle IllegalArgumentException
     * @param ex Exception
     * @return Problem detail with HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage());
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        problemDetail.setTitle("Invalid Request");
        problemDetail.setType(URI.create("https://api.library.com/errors/invalid-request"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }

    /**
     * Handle all other exceptions
     * @param ex Exception
     * @return Problem detail with HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Unexpected error: ", ex);
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        );
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.library.com/errors/internal-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        return problemDetail;
    }
}

// Made with Bob
