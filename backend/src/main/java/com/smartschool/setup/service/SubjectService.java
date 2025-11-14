package com.smartschool.setup.service;

import com.smartschool.exception.DuplicateResourceException;
import com.smartschool.exception.ResourceNotFoundException;
import com.smartschool.setup.dto.SubjectRequest;
import com.smartschool.setup.dto.SubjectResponse;
import com.smartschool.setup.entity.Subject;
import com.smartschool.setup.mapper.SubjectMapper;
import com.smartschool.setup.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing Subjects
 * Requirement: SET-04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    /**
     * Create a new subject
     */
    @Transactional
    public SubjectResponse createSubject(SubjectRequest request) {
        log.info("Creating new subject: {}", request.getName());

        // Check for duplicate name
        if (subjectRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Subject", "name", request.getName());
        }

        // Check for duplicate code if provided
        if (request.getCode() != null && !request.getCode().isBlank() &&
                subjectRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Subject", "code", request.getCode());
        }

        Subject subject = subjectMapper.toEntity(request);
        Subject savedSubject = subjectRepository.save(subject);

        log.info("Subject created successfully with ID: {}", savedSubject.getId());
        return subjectMapper.toResponse(savedSubject);
    }

    /**
     * Get all subjects
     */
    @Transactional(readOnly = true)
    public List<SubjectResponse> getAllSubjects() {
        log.info("Fetching all subjects");
        List<Subject> subjects = subjectRepository.findAll();
        return subjectMapper.toResponseList(subjects);
    }

    /**
     * Get subject by ID
     */
    @Transactional(readOnly = true)
    public SubjectResponse getSubjectById(Long id) {
        log.info("Fetching subject with ID: {}", id);
        Subject subject = findSubjectByIdOrThrow(id);
        return subjectMapper.toResponse(subject);
    }

    /**
     * Update a subject
     */
    @Transactional
    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        log.info("Updating subject with ID: {}", id);

        Subject subject = findSubjectByIdOrThrow(id);

        // Check for duplicate name (excluding current subject)
        if (subjectRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("Subject", "name", request.getName());
        }

        // Check for duplicate code if provided (excluding current subject)
        if (request.getCode() != null && !request.getCode().isBlank() &&
                subjectRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new DuplicateResourceException("Subject", "code", request.getCode());
        }

        subjectMapper.updateEntityFromRequest(request, subject);
        Subject updatedSubject = subjectRepository.save(subject);

        log.info("Subject updated successfully with ID: {}", updatedSubject.getId());
        return subjectMapper.toResponse(updatedSubject);
    }

    /**
     * Delete a subject
     */
    @Transactional
    public void deleteSubject(Long id) {
        log.info("Deleting subject with ID: {}", id);

        Subject subject = findSubjectByIdOrThrow(id);
        subjectRepository.delete(subject);

        log.info("Subject deleted successfully with ID: {}", id);
    }

    /**
     * Helper method to find subject by ID or throw exception
     */
    private Subject findSubjectByIdOrThrow(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
    }
}
