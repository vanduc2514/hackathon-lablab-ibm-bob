package com.library.student.service;

import com.library.student.dto.CardDTO;
import com.library.student.dto.StudentDTO;
import com.library.student.exception.DuplicateEmailException;
import com.library.student.exception.StudentNotFoundException;
import com.library.student.model.Card;
import com.library.student.model.CardStatus;
import com.library.student.model.Student;
import com.library.student.repository.CardRepository;
import com.library.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Student Service Tests")
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;
    private Card testCard;
    private StudentDTO testStudentDTO;

    @BeforeEach
    void setUp() {
        testCard = Card.builder()
                .id(1L)
                .cardNo("CARD-001")
                .status(CardStatus.ACTIVATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testStudent = Student.builder()
                .id(1L)
                .name("John Doe")
                .emailId("john.doe@example.com")
                .age(20)
                .country("USA")
                .card(testCard)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testStudentDTO = StudentDTO.builder()
                .name("John Doe")
                .emailId("john.doe@example.com")
                .age(20)
                .country("USA")
                .build();
    }

    @Test
    @DisplayName("Should create student successfully")
    void testCreateStudent_Success() {
        // Given
        when(studentRepository.existsByEmailId(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        // When
        StudentDTO result = studentService.createStudent(testStudentDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmailId()).isEqualTo("john.doe@example.com");
        verify(studentRepository).existsByEmailId("john.doe@example.com");
        verify(studentRepository).save(any(Student.class));
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw exception when creating student with duplicate email")
    void testCreateStudent_DuplicateEmail() {
        // Given
        when(studentRepository.existsByEmailId(anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> studentService.createStudent(testStudentDTO))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("already exists");

        verify(studentRepository).existsByEmailId("john.doe@example.com");
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should get student by ID successfully")
    void testGetStudentById_Success() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // When
        StudentDTO result = studentService.getStudentById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when student not found by ID")
    void testGetStudentById_NotFound() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("not found with ID: 1");

        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should get student by email successfully")
    void testGetStudentByEmail_Success() {
        // Given
        when(studentRepository.findByEmailId(anyString())).thenReturn(Optional.of(testStudent));

        // When
        StudentDTO result = studentService.getStudentByEmail("john.doe@example.com");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmailId()).isEqualTo("john.doe@example.com");
        verify(studentRepository).findByEmailId("john.doe@example.com");
    }

    @Test
    @DisplayName("Should update student successfully")
    void testUpdateStudent_Success() {
        // Given
        StudentDTO updateDTO = StudentDTO.builder()
                .name("John Updated")
                .emailId("john.doe@example.com")
                .age(21)
                .country("Canada")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.existsByEmailId(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        // When
        StudentDTO result = studentService.updateStudent(1L, updateDTO);

        // Then
        assertThat(result).isNotNull();
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw exception when updating with duplicate email")
    void testUpdateStudent_DuplicateEmail() {
        // Given
        StudentDTO updateDTO = StudentDTO.builder()
                .name("John Updated")
                .emailId("different@example.com")
                .age(21)
                .country("Canada")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.existsByEmailId("different@example.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> studentService.updateStudent(1L, updateDTO))
                .isInstanceOf(DuplicateEmailException.class);

        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should delete student successfully")
    void testDeleteStudent_Success() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        doNothing().when(studentRepository).delete(any(Student.class));

        // When
        studentService.deleteStudent(1L);

        // Then
        verify(studentRepository).findById(1L);
        verify(studentRepository).delete(testStudent);
        assertThat(testCard.getStatus()).isEqualTo(CardStatus.DEACTIVATED);
    }

    @Test
    @DisplayName("Should get student card successfully")
    void testGetStudentCard_Success() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // When
        CardDTO result = studentService.getStudentCard(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCardNo()).isEqualTo("CARD-001");
        assertThat(result.getStatus()).isEqualTo(CardStatus.ACTIVATED);
        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should check if card is active")
    void testIsCardActive_True() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // When
        boolean result = studentService.isCardActive(1L);

        // Then
        assertThat(result).isTrue();
        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return false when card is deactivated")
    void testIsCardActive_False() {
        // Given
        testCard.setStatus(CardStatus.DEACTIVATED);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // When
        boolean result = studentService.isCardActive(1L);

        // Then
        assertThat(result).isFalse();
        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should get issued books count")
    void testGetIssuedBooksCount() {
        // Given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        // When
        int result = studentService.getIssuedBooksCount(1L);

        // Then
        assertThat(result).isEqualTo(0); // Placeholder implementation
        verify(studentRepository).findById(1L);
    }
}

// Made with Bob
