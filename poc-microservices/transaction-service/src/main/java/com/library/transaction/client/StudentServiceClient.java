package com.library.transaction.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign Client for Student Service
 * Includes circuit breaker for fault tolerance
 */
@FeignClient(name = "student-service", fallback = StudentServiceClient.StudentServiceFallback.class)
public interface StudentServiceClient {

    /**
     * Check if student's card is active
     * @param studentId Student ID
     * @return true if card is active
     */
    @GetMapping("/api/v1/students/{studentId}/card/active")
    @CircuitBreaker(name = "studentService", fallbackMethod = "isCardActiveFallback")
    Boolean isCardActive(@PathVariable Long studentId);

    /**
     * Get count of books issued to student
     * @param studentId Student ID
     * @return Count of issued books
     */
    @GetMapping("/api/v1/students/{studentId}/issued-books-count")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getIssuedBooksCountFallback")
    Integer getIssuedBooksCount(@PathVariable Long studentId);

    /**
     * Fallback class for Student Service
     */
    @Component
    @Slf4j
    class StudentServiceFallback implements StudentServiceClient {

        @Override
        public Boolean isCardActive(Long studentId) {
            log.error("Student Service is unavailable. Fallback: returning false for student ID: {}", studentId);
            return false;
        }

        @Override
        public Integer getIssuedBooksCount(Long studentId) {
            log.error("Student Service is unavailable. Fallback: returning 0 for student ID: {}", studentId);
            return 0;
        }
    }
}

// Made with Bob
