package com.library.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Fallback Controller
 * 
 * Provides fallback responses when circuit breakers are open
 * or services are unavailable.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/students")
    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> studentServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Student Service is currently unavailable");
        response.put("message", "Please try again later");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/books")
    @PostMapping("/books")
    public ResponseEntity<Map<String, Object>> bookServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Book Service is currently unavailable");
        response.put("message", "Please try again later");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/transactions")
    @PostMapping("/transactions")
    public ResponseEntity<Map<String, Object>> transactionServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Transaction Service is currently unavailable");
        response.put("message", "Please try again later");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}

// Made with Bob
