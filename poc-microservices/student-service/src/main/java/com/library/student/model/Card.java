package com.library.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Card Entity
 * 
 * Represents a library card associated with a student.
 * Migrated from monolith with javax.* → jakarta.* namespace change.
 * 
 * @author Migration Team
 * @version 1.0.0
 */
@Entity
@Table(name = "card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    @Builder.Default
    private CardStatus cardStatus = CardStatus.ACTIVATED;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    // Note: In microservices, we don't have bidirectional relationships
    // Student relationship is managed at application level
    // Books and Transactions are in separate services
}

// Made with Bob
