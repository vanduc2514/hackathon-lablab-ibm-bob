package com.library.student.service;

import com.library.student.dto.StudentDTO;
import com.library.student.exception.DuplicateEmailException;
import com.library.student.exception.StudentNotFoundException;
import com.library.student.model.Card;
import com.library.student.model.CardStatus;
import com.library.student.model.Student;
import com.library.student.repository.CardRepository;
import com.library.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Student Service
 * 
 * Business logic for student management.
 * Migrated from monolith with improved error handling and validation.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CardRepository cardRepository;

    /**
     * Create a new student with an activated card
     */
    @Transactional
    public Student createStudent(StudentDTO studentDTO) {
        log.info("Creating student with email: {}", studentDTO.getEmailId());
        
        // Check for duplicate email
        if (studentRepository.existsByEmailId(studentDTO.getEmailId())) {
            throw new DuplicateEmailException("Student with email " + studentDTO.getEmailId() + " already exists");
        }

        // Create and save card first
        Card card = Card.builder()
                .cardStatus(CardStatus.ACTIVATED)
                .build();
        card = cardRepository.save(card);
        
        // Create student with card
        Student student = Student.builder()
                .emailId(studentDTO.getEmailId())
                .name(studentDTO.getName())
                .age(studentDTO.getAge())
                .country(studentDTO.getCountry())
                .card(card)
                .build();
        
        student = studentRepository.save(student);
        log.info("Student created successfully with ID: {} and card ID: {}", student.getId(), card.getId());
        
        return student;
    }

    /**
     * Get student by ID
     */
    public Student getStudentById(Integer id) {
        log.debug("Fetching student with ID: {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + id));
    }

    /**
     * Get student by email
     */
    public Student getStudentByEmail(String emailId) {
        log.debug("Fetching student with email: {}", emailId);
        return studentRepository.findByEmailId(emailId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with email: " + emailId));
    }

    /**
     * Update student details
     */
    @Transactional
    public Student updateStudent(Integer id, StudentDTO studentDTO) {
        log.info("Updating student with ID: {}", id);
        
        Student student = getStudentById(id);
        
        // Check if email is being changed and if new email already exists
        if (!student.getEmailId().equals(studentDTO.getEmailId()) && 
            studentRepository.existsByEmailId(studentDTO.getEmailId())) {
            throw new DuplicateEmailException("Email " + studentDTO.getEmailId() + " is already in use");
        }
        
        student.setEmailId(studentDTO.getEmailId());
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setCountry(studentDTO.getCountry());
        
        student = studentRepository.save(student);
        log.info("Student updated successfully with ID: {}", id);
        
        return student;
    }

    /**
     * Delete student (deactivate card and delete student)
     */
    @Transactional
    public void deleteStudent(Integer id) {
        log.info("Deleting student with ID: {}", id);
        
        Student student = getStudentById(id);
        
        // Deactivate card
        if (student.getCard() != null) {
            Card card = student.getCard();
            card.setCardStatus(CardStatus.DEACTIVATED);
            cardRepository.save(card);
            log.info("Card deactivated for student ID: {}", id);
        }
        
        // Delete student
        studentRepository.delete(student);
        log.info("Student deleted successfully with ID: {}", id);
    }

    /**
     * Get student's card
     */
    public Card getStudentCard(Integer studentId) {
        log.debug("Fetching card for student ID: {}", studentId);
        Student student = getStudentById(studentId);
        return student.getCard();
    }

    /**
     * Validate if student's card is active
     */
    public boolean isCardActive(Integer studentId) {
        Student student = getStudentById(studentId);
        return student.getCard() != null && 
               student.getCard().getCardStatus() == CardStatus.ACTIVATED;
    }

    /**
     * Get count of issued books for a student
     * This will be called by Transaction Service
     */
    public int getIssuedBooksCount(Integer studentId) {
        // For POC, return 0. In full implementation, this would query Transaction Service
        log.debug("Getting issued books count for student ID: {}", studentId);
        return 0;
    }
}

// Made with Bob
