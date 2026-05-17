package com.library.book.controller;

import com.library.book.dto.AuthorDTO;
import com.library.book.dto.BookDTO;
import com.library.book.service.AuthorService;
import com.library.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Author Service
 * Handles all author-related HTTP requests
 */
@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    /**
     * Create a new author
     * @param authorDTO Author data
     * @return Created author with HTTP 201
     */
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        log.info("Creating new author: {}", authorDTO.getName());
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        log.info("Author created successfully with ID: {}", createdAuthor.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    /**
     * Get author by ID
     * @param id Author ID
     * @return Author data with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        log.info("Fetching author with ID: {}", id);
        AuthorDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    /**
     * Get author by email
     * @param email Author email
     * @return Author data with HTTP 200
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<AuthorDTO> getAuthorByEmail(@PathVariable String email) {
        log.info("Fetching author with email: {}", email);
        AuthorDTO author = authorService.getAuthorByEmail(email);
        return ResponseEntity.ok(author);
    }

    /**
     * Get all authors
     * @return List of authors with HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        log.info("Fetching all authors");
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    /**
     * Update author information
     * @param id Author ID
     * @param authorDTO Updated author data
     * @return Updated author with HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorDTO authorDTO) {
        log.info("Updating author with ID: {}", id);
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
        log.info("Author updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedAuthor);
    }

    /**
     * Delete author
     * @param id Author ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        log.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
        log.info("Author deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all books by author
     * @param id Author ID
     * @return List of books with HTTP 200
     */
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long id) {
        log.info("Fetching books for author ID: {}", id);
        List<BookDTO> books = bookService.getBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }
}

// Made with Bob
