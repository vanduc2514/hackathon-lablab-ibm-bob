package com.library.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for issuing a book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    private String remarks;
}

// Made with Bob
