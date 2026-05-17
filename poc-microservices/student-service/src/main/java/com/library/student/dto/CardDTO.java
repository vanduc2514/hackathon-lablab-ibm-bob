package com.library.student.dto;

import com.library.student.model.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Card Data Transfer Object
 * 
 * Used for API responses.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {

    private Integer id;
    private CardStatus cardStatus;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}

// Made with Bob
