package com.library.book.service;

import com.library.book.dto.AuthorDTO;
import com.library.book.exception.AuthorNotFoundException;
import com.library.book.model.Author;
import com.library.book.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Author business logic
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Create a new author
     * @param authorDTO Author data
     * @return Created author DTO
     */
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        log.info("Creating new author: {}", authorDTO.getName());
        
        // Check for duplicate email
        if (authorDTO.getEmail() != null && authorRepository.existsByEmail(authorDTO.getEmail())) {
            throw new IllegalArgumentException("Author with email " + authorDTO.getEmail() + " already exists");
        }
        
        Author author = Author.builder()
                .name(authorDTO.getName())
                .email(authorDTO.getEmail())
                .age(authorDTO.getAge())
                .country(authorDTO.getCountry())
                .biography(authorDTO.getBiography())
                .build();
        
        Author savedAuthor = authorRepository.save(author);
        log.info("Author created with ID: {}", savedAuthor.getId());
        
        return convertToDTO(savedAuthor);
    }

    /**
     * Get author by ID
     * @param id Author ID
     * @return Author DTO
     */
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(Long id) {
        log.info("Fetching author with ID: {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return convertToDTO(author);
    }

    /**
     * Get author by email
     * @param email Author email
     * @return Author DTO
     */
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorByEmail(String email) {
        log.info("Fetching author with email: {}", email);
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with email: " + email));
        return convertToDTO(author);
    }

    /**
     * Get all authors
     * @return List of author DTOs
     */
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAllAuthors() {
        log.info("Fetching all authors");
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update author
     * @param id Author ID
     * @param authorDTO Updated author data
     * @return Updated author DTO
     */
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        log.info("Updating author with ID: {}", id);
        
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        
        // Check for duplicate email if email is being changed
        if (authorDTO.getEmail() != null && 
            !authorDTO.getEmail().equals(author.getEmail()) &&
            authorRepository.existsByEmail(authorDTO.getEmail())) {
            throw new IllegalArgumentException("Author with email " + authorDTO.getEmail() + " already exists");
        }
        
        // Update fields
        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setAge(authorDTO.getAge());
        author.setCountry(authorDTO.getCountry());
        author.setBiography(authorDTO.getBiography());
        
        Author updatedAuthor = authorRepository.save(author);
        log.info("Author updated successfully with ID: {}", id);
        
        return convertToDTO(updatedAuthor);
    }

    /**
     * Delete author
     * @param id Author ID
     */
    public void deleteAuthor(Long id) {
        log.info("Deleting author with ID: {}", id);
        
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        
        authorRepository.delete(author);
        log.info("Author deleted successfully with ID: {}", id);
    }

    /**
     * Convert Author entity to DTO
     * @param author Author entity
     * @return Author DTO
     */
    private AuthorDTO convertToDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .age(author.getAge())
                .country(author.getCountry())
                .biography(author.getBiography())
                .build();
    }
}

// Made with Bob
