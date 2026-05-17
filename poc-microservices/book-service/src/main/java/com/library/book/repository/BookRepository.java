package com.library.book.repository;

import com.library.book.model.Book;
import com.library.book.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find books by genre
     * @param genre Book genre
     * @return List of books
     */
    List<Book> findByGenre(Genre genre);

    /**
     * Find books by author ID
     * @param authorId Author ID
     * @return List of books
     */
    List<Book> findByAuthorId(Long authorId);

    /**
     * Find available books
     * @param available Availability status
     * @return List of books
     */
    List<Book> findByAvailable(Boolean available);

    /**
     * Find books by title containing (case-insensitive search)
     * @param title Title search term
     * @return List of books
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Find books by genre and availability
     * @param genre Book genre
     * @param available Availability status
     * @return List of books
     */
    List<Book> findByGenreAndAvailable(Genre genre, Boolean available);

    /**
     * Find books by author ID and availability
     * @param authorId Author ID
     * @param available Availability status
     * @return List of books
     */
    List<Book> findByAuthorIdAndAvailable(Long authorId, Boolean available);

    /**
     * Check if book exists by ISBN
     * @param isbn Book ISBN
     * @return true if exists
     */
    boolean existsByIsbn(String isbn);
}

// Made with Bob
