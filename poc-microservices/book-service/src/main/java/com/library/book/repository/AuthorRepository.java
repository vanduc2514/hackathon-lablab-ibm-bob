package com.library.book.repository;

import com.library.book.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Author entity
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Find author by email
     * @param email Author email
     * @return Optional of Author
     */
    Optional<Author> findByEmail(String email);

    /**
     * Check if author exists by email
     * @param email Author email
     * @return true if exists
     */
    boolean existsByEmail(String email);

    /**
     * Find author by name
     * @param name Author name
     * @return Optional of Author
     */
    Optional<Author> findByName(String name);
}

// Made with Bob
