package com.library.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Student Service Application
 * 
 * Manages student and library card information.
 * Part of the microservices migration from monolithic architecture.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }
}

// Made with Bob
