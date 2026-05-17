package com.library.student.controller;

import com.library.student.dto.CardDTO;
import com.library.student.dto.StudentDTO;
import com.library.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Student Service
 * Handles all student-related HTTP requests
 */
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    /**
     * Create a new student
     * @param studentDTO Student data
     * @return Created student with HTTP 201
     */
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        log.info("Creating new student with email: {}", studentDTO.getEmailId());
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        log.info("Student created successfully with ID: {}", createdStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /**
     * Get student by ID
     * @param id Student ID
     * @return Student data with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        log.info("Fetching student with ID: {}", id);
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * Get student by email
     * @param email Student email
     * @return Student data with HTTP 200
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email) {
        log.info("Fetching student with email: {}", email);
        StudentDTO student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(student);
    }

    /**
     * Update student information
     * @param id Student ID
     * @param studentDTO Updated student data
     * @return Updated student with HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        log.info("Student updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Delete student
     * @param id Student ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("Deleting student with ID: {}", id);
        studentService.deleteStudent(id);
        log.info("Student deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get student's library card
     * @param id Student ID
     * @return Card data with HTTP 200
     */
    @GetMapping("/{id}/card")
    public ResponseEntity<CardDTO> getStudentCard(@PathVariable Long id) {
        log.info("Fetching card for student ID: {}", id);
        CardDTO card = studentService.getStudentCard(id);
        return ResponseEntity.ok(card);
    }

    /**
     * Check if student's card is active
     * @param id Student ID
     * @return Boolean indicating card status with HTTP 200
     */
    @GetMapping("/{id}/card/active")
    public ResponseEntity<Boolean> isCardActive(@PathVariable Long id) {
        log.info("Checking card status for student ID: {}", id);
        boolean isActive = studentService.isCardActive(id);
        return ResponseEntity.ok(isActive);
    }

    /**
     * Get count of books issued to student
     * @param id Student ID
     * @return Count of issued books with HTTP 200
     */
    @GetMapping("/{id}/issued-books-count")
    public ResponseEntity<Integer> getIssuedBooksCount(@PathVariable Long id) {
        log.info("Fetching issued books count for student ID: {}", id);
        int count = studentService.getIssuedBooksCount(id);
        return ResponseEntity.ok(count);
    }
}

// Made with Bob
