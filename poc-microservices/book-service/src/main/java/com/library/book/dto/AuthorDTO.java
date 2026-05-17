package com.library.book.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Author
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;

    @NotBlank(message = "Author name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    private String country;

    private String biography;
}

// Made with Bob
