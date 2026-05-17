package com.library.transaction.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign Client for Book Service
 * Includes circuit breaker for fault tolerance
 */
@FeignClient(name = "book-service", fallback = BookServiceClient.BookServiceFallback.class)
public interface BookServiceClient {

    /**
     * Check if book is available
     * @param bookId Book ID
     * @return true if available
     */
    @GetMapping("/api/v1/books/{bookId}/available")
    @CircuitBreaker(name = "bookService", fallbackMethod = "isBookAvailableFallback")
    Boolean isBookAvailable(@PathVariable Long bookId);

    /**
     * Update book availability
     * @param bookId Book ID
     * @param available Availability status
     */
    @PatchMapping("/api/v1/books/{bookId}/availability")
    @CircuitBreaker(name = "bookService", fallbackMethod = "updateBookAvailabilityFallback")
    void updateBookAvailability(@PathVariable Long bookId, @RequestParam Boolean available);

    /**
     * Fallback class for Book Service
     */
    @Component
    @Slf4j
    class BookServiceFallback implements BookServiceClient {

        @Override
        public Boolean isBookAvailable(Long bookId) {
            log.error("Book Service is unavailable. Fallback: returning false for book ID: {}", bookId);
            return false;
        }

        @Override
        public void updateBookAvailability(Long bookId, Boolean available) {
            log.error("Book Service is unavailable. Fallback: cannot update availability for book ID: {}", bookId);
            throw new RuntimeException("Book Service is unavailable");
        }
    }
}

// Made with Bob
