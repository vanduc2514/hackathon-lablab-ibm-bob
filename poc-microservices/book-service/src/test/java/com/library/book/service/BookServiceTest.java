package com.library.book.service;

import com.library.book.dto.BookDTO;
import com.library.book.exception.AuthorNotFoundException;
import com.library.book.exception.BookNotFoundException;
import com.library.book.exception.DuplicateIsbnException;
import com.library.book.model.Book;
import com.library.book.model.Genre;
import com.library.book.repository.AuthorRepository;
import com.library.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Book Service Tests")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private BookDTO testBookDTO;

    @BeforeEach
    void setUp() {
        testBook = Book.builder()
                .id(1L)
                .title("Test Book")
                .genre(Genre.FICTION)
                .authorId(1L)
                .available(true)
                .isbn("ISBN-123")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testBookDTO = BookDTO.builder()
                .title("Test Book")
                .genre(Genre.FICTION)
                .authorId(1L)
                .available(true)
                .isbn("ISBN-123")
                .description("Test Description")
                .build();
    }

    @Test
    @DisplayName("Should create book successfully")
    void testCreateBook_Success() {
        // Given
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.existsByIsbn(any())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // When
        BookDTO result = bookService.createBook(testBookDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Book");
        assertThat(result.getGenre()).isEqualTo(Genre.FICTION);
        verify(authorRepository).existsById(1L);
        verify(bookRepository).existsByIsbn("ISBN-123");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw exception when creating book with non-existent author")
    void testCreateBook_AuthorNotFound() {
        // Given
        when(authorRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> bookService.createBook(testBookDTO))
                .isInstanceOf(AuthorNotFoundException.class);

        verify(authorRepository).existsById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw exception when creating book with duplicate ISBN")
    void testCreateBook_DuplicateIsbn() {
        // Given
        when(authorRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.existsByIsbn(any())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> bookService.createBook(testBookDTO))
                .isInstanceOf(DuplicateIsbnException.class);

        verify(bookRepository).existsByIsbn("ISBN-123");
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should get book by ID successfully")
    void testGetBookById_Success() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // When
        BookDTO result = bookService.getBookById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when book not found")
    void testGetBookById_NotFound() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> bookService.getBookById(1L))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository).findById(1L);
    }

    @Test
    @DisplayName("Should get books by genre")
    void testGetBooksByGenre() {
        // Given
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findByGenre(Genre.FICTION)).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getBooksByGenre(Genre.FICTION);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGenre()).isEqualTo(Genre.FICTION);
        verify(bookRepository).findByGenre(Genre.FICTION);
    }

    @Test
    @DisplayName("Should get books by author ID")
    void testGetBooksByAuthorId() {
        // Given
        List<Book> books = Arrays.asList(testBook);
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findByAuthorId(1L)).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getBooksByAuthorId(1L);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAuthorId()).isEqualTo(1L);
        verify(bookRepository).findByAuthorId(1L);
    }

    @Test
    @DisplayName("Should search books by title")
    void testSearchBooksByTitle() {
        // Given
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(books);

        // When
        List<BookDTO> result = bookService.searchBooksByTitle("Test");

        // Then
        assertThat(result).hasSize(1);
        verify(bookRepository).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    @DisplayName("Should get available books")
    void testGetAvailableBooks() {
        // Given
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findByAvailable(true)).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getAvailableBooks();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAvailable()).isTrue();
        verify(bookRepository).findByAvailable(true);
    }

    @Test
    @DisplayName("Should update book availability")
    void testUpdateBookAvailability() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // When
        BookDTO result = bookService.updateBookAvailability(1L, false);

        // Then
        assertThat(result).isNotNull();
        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should check if book is available")
    void testIsBookAvailable() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // When
        boolean result = bookService.isBookAvailable(1L);

        // Then
        assertThat(result).isTrue();
        verify(bookRepository).findById(1L);
    }

    @Test
    @DisplayName("Should delete book successfully")
    void testDeleteBook() {
        // Given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        doNothing().when(bookRepository).delete(any(Book.class));

        // When
        bookService.deleteBook(1L);

        // Then
        verify(bookRepository).findById(1L);
        verify(bookRepository).delete(testBook);
    }
}

// Made with Bob
