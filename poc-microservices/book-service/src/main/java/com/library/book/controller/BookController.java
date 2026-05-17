package com.library.book.controller;

import com.library.book.dto.BookDTO;
import com.library.book.model.Genre;
import com.library.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Book Service
 * Handles all book-related HTTP requests
 */
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * Create a new book
     * @param bookDTO Book data
     * @return Created book with HTTP 201
     */
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO.getTitle());
        BookDTO createdBook = bookService.createBook(bookDTO);
        log.info("Book created successfully with ID: {}", createdBook.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * Get book by ID
     * @param id Book ID
     * @return Book data with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        log.info("Fetching book with ID: {}", id);
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Get all books or search by parameters
     * @param genre Optional genre filter
     * @param title Optional title search
     * @param authorId Optional author ID filter
     * @param available Optional availability filter
     * @return List of books with HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Boolean available) {
        
        log.info("Searching books - genre: {}, title: {}, authorId: {}, available: {}", 
                genre, title, authorId, available);
        
        List<BookDTO> books;
        
        // Priority: specific searches first, then general
        if (title != null && !title.isEmpty()) {
            books = bookService.searchBooksByTitle(title);
        } else if (genre != null && available != null && available) {
            books = bookService.getAvailableBooksByGenre(genre);
        } else if (genre != null) {
            books = bookService.getBooksByGenre(genre);
        } else if (authorId != null) {
            books = bookService.getBooksByAuthorId(authorId);
        } else if (available != null && available) {
            books = bookService.getAvailableBooks();
        } else {
            books = bookService.getAllBooks();
        }
        
        return ResponseEntity.ok(books);
    }

    /**
     * Get available books
     * @return List of available books with HTTP 200
     */
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        log.info("Fetching available books");
        List<BookDTO> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get books by genre
     * @param genre Book genre
     * @return List of books with HTTP 200
     */
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Genre genre) {
        log.info("Fetching books by genre: {}", genre);
        List<BookDTO> books = bookService.getBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }

    /**
     * Get books by author ID
     * @param authorId Author ID
     * @return List of books with HTTP 200
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        log.info("Fetching books by author ID: {}", authorId);
        List<BookDTO> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    /**
     * Update book information
     * @param id Book ID
     * @param bookDTO Updated book data
     * @return Updated book with HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookDTO bookDTO) {
        log.info("Updating book with ID: {}", id);
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        log.info("Book updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Update book availability
     * @param id Book ID
     * @param available Availability status
     * @return Updated book with HTTP 200
     */
    @PatchMapping("/{id}/availability")
    public ResponseEntity<BookDTO> updateBookAvailability(
            @PathVariable Long id,
            @RequestParam Boolean available) {
        log.info("Updating book availability for ID: {} to {}", id, available);
        BookDTO updatedBook = bookService.updateBookAvailability(id, available);
        log.info("Book availability updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Delete book
     * @param id Book ID
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        log.info("Book deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if book is available
     * @param id Book ID
     * @return Boolean indicating availability with HTTP 200
     */
    @GetMapping("/{id}/available")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable Long id) {
        log.info("Checking availability for book ID: {}", id);
        boolean available = bookService.isBookAvailable(id);
        return ResponseEntity.ok(available);
    }
}

// Made with Bob
