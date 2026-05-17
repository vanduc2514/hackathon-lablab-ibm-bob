package com.library.transaction.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for returning a book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookRequest {

    @NotNull(message = "Transaction ID is required")
    private Long transactionId;

    private String remarks;
}

// Made with Bob
