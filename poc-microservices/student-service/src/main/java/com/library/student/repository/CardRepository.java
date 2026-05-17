package com.library.student.repository;

import com.library.student.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Card Repository
 * 
 * Data access layer for Card entity.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
}

// Made with Bob
