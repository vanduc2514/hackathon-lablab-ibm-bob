package com.library.student.repository;

import com.library.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Student Repository
 * 
 * Data access layer for Student entity.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    Optional<Student> findByEmailId(String emailId);
    
    boolean existsByEmailId(String emailId);
}

// Made with Bob
