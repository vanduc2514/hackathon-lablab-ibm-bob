package com.library.student.integration;

import com.library.student.dto.StudentDTO;
import com.library.student.model.CardStatus;
import com.library.student.model.Student;
import com.library.student.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for Student Service
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Student Service Integration Tests")
class StudentServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    private StudentDTO testStudentDTO;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        
        testStudentDTO = StudentDTO.builder()
                .name("Integration Test Student")
                .emailId("integration@test.com")
                .age(22)
                .country("Test Country")
                .build();
    }

    @Test
    @DisplayName("Should create student via REST API")
    void testCreateStudent_Integration() throws Exception {
        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStudentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Integration Test Student"))
                .andExpect(jsonPath("$.emailId").value("integration@test.com"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    @DisplayName("Should get student by ID via REST API")
    void testGetStudentById_Integration() throws Exception {
        // Create student first
        String response = mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStudentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        StudentDTO created = objectMapper.readValue(response, StudentDTO.class);

        // Get student by ID
        mockMvc.perform(get("/api/v1/students/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("Integration Test Student"));
    }

    @Test
    @DisplayName("Should return 404 when student not found")
    void testGetStudentById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Student Not Found"));
    }

    @Test
    @DisplayName("Should validate student creation with invalid data")
    void testCreateStudent_ValidationError() throws Exception {
        StudentDTO invalidStudent = StudentDTO.builder()
                .name("") // Invalid: blank name
                .emailId("invalid-email") // Invalid: not an email
                .age(-1) // Invalid: negative age
                .build();

        mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation Error"));
    }
}

// Made with Bob
