package com.library.book.service;

import com.library.book.dto.BookDTO;
import com.library.book.exception.AuthorNotFoundException;
import com.library.book.exception.BookNotFoundException;
import com.library.book.exception.DuplicateIsbnException;
import com.library.book.model.Book;
import com.library.book.model.Genre;
import com.library.book.repository.AuthorRepository;
import com.library.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Book business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    /**
     * Create a new book
     * @param bookDTO Book data
     * @return Created book DTO
     */
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO.getTitle());
        
        // Validate author exists
        if (!authorRepository.existsById(bookDTO.getAuthorId())) {
            throw new AuthorNotFoundException(bookDTO.getAuthorId());
        }
        
        // Check for duplicate ISBN
        if (bookDTO.getIsbn() != null && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateIsbnException(bookDTO.getIsbn());
        }
        
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .genre(bookDTO.getGenre())
                .authorId(bookDTO.getAuthorId())
                .available(bookDTO.getAvailable() != null ? bookDTO.getAvailable() : true)
                .publishedDate(bookDTO.getPublishedDate())
                .isbn(bookDTO.getIsbn())
                .description(bookDTO.getDescription())
                .build();
        
        Book savedBook = bookRepository.save(book);
        log.info("Book created with ID: {}", savedBook.getId());
        
        return convertToDTO(savedBook);
    }

    /**
     * Get book by ID
     * @param id Book ID
     * @return Book DTO
     */
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return convertToDTO(book);
    }

    /**
     * Get all books
     * @return List of book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search books by genre
     * @param genre Book genre
     * @return List of book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByGenre(Genre genre) {
        log.info("Searching books by genre: {}", genre);
        return bookRepository.findByGenre(genre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search books by author ID
     * @param authorId Author ID
     * @return List of book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        log.info("Searching books by author ID: {}", authorId);
        
        // Validate author exists
        if (!authorRepository.existsById(authorId)) {
            throw new AuthorNotFoundException(authorId);
        }
        
        return bookRepository.findByAuthorId(authorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search books by title (case-insensitive)
     * @param title Title search term
     * @return List of book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByTitle(String title) {
        log.info("Searching books by title: {}", title);
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get available books
     * @return List of available book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> getAvailableBooks() {
        log.info("Fetching available books");
        return bookRepository.findByAvailable(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get available books by genre
     * @param genre Book genre
     * @return List of available book DTOs
     */
    @Transactional(readOnly = true)
    public List<BookDTO> getAvailableBooksByGenre(Genre genre) {
        log.info("Fetching available books by genre: {}", genre);
        return bookRepository.findByGenreAndAvailable(genre, true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update book
     * @param id Book ID
     * @param bookDTO Updated book data
     * @return Updated book DTO
     */
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        log.info("Updating book with ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        // Validate author exists if author is being changed
        if (!bookDTO.getAuthorId().equals(book.getAuthorId()) &&
            !authorRepository.existsById(bookDTO.getAuthorId())) {
            throw new AuthorNotFoundException(bookDTO.getAuthorId());
        }
        
        // Check for duplicate ISBN if ISBN is being changed
        if (bookDTO.getIsbn() != null && 
            !bookDTO.getIsbn().equals(book.getIsbn()) &&
            bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new DuplicateIsbnException(bookDTO.getIsbn());
        }
        
        // Update fields
        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setAvailable(bookDTO.getAvailable());
        book.setPublishedDate(bookDTO.getPublishedDate());
        book.setIsbn(bookDTO.getIsbn());
        book.setDescription(bookDTO.getDescription());
        
        Book updatedBook = bookRepository.save(book);
        log.info("Book updated successfully with ID: {}", id);
        
        return convertToDTO(updatedBook);
    }

    /**
     * Update book availability
     * @param id Book ID
     * @param available Availability status
     * @return Updated book DTO
     */
    public BookDTO updateBookAvailability(Long id, Boolean available) {
        log.info("Updating book availability for ID: {} to {}", id, available);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        book.setAvailable(available);
        Book updatedBook = bookRepository.save(book);
        
        log.info("Book availability updated successfully for ID: {}", id);
        return convertToDTO(updatedBook);
    }

    /**
     * Delete book
     * @param id Book ID
     */
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        
        bookRepository.delete(book);
        log.info("Book deleted successfully with ID: {}", id);
    }

    /**
     * Check if book is available
     * @param id Book ID
     * @return true if available
     */
    @Transactional(readOnly = true)
    public boolean isBookAvailable(Long id) {
        log.info("Checking availability for book ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return book.getAvailable();
    }

    /**
     * Convert Book entity to DTO
     * @param book Book entity
     * @return Book DTO
     */
    private BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .authorId(book.getAuthorId())
                .available(book.getAvailable())
                .publishedDate(book.getPublishedDate())
                .isbn(book.getIsbn())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}

// Made with Bob
