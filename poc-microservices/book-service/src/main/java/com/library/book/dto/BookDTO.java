package com.library.book.dto;

import com.library.book.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Book title is required")
    private String title;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    private Boolean available;

    private LocalDateTime publishedDate;

    private String isbn;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

// Made with Bob
