package com.smartschool.setup.service;

import com.smartschool.exception.DuplicateResourceException;
import com.smartschool.exception.ResourceNotFoundException;
import com.smartschool.setup.dto.ClassSubjectAssignmentRequest;
import com.smartschool.setup.dto.SchoolClassRequest;
import com.smartschool.setup.dto.SchoolClassResponse;
import com.smartschool.setup.entity.SchoolClass;
import com.smartschool.setup.entity.Subject;
import com.smartschool.setup.mapper.SchoolClassMapper;
import com.smartschool.setup.repository.SchoolClassRepository;
import com.smartschool.setup.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service for managing School Classes
 * Requirement: SET-02, SET-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassMapper schoolClassMapper;

    /**
     * Create a new school class
     */
    @Transactional
    public SchoolClassResponse createClass(SchoolClassRequest request) {
        log.info("Creating new school class: {}", request.getName());

        // Check for duplicate name
        if (schoolClassRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("School Class", "name", request.getName());
        }

        SchoolClass schoolClass = schoolClassMapper.toEntity(request);
        SchoolClass savedClass = schoolClassRepository.save(schoolClass);

        log.info("School class created successfully with ID: {}", savedClass.getId());
        return schoolClassMapper.toResponse(savedClass);
    }

    /**
     * Get all school classes
     */
    @Transactional(readOnly = true)
    public List<SchoolClassResponse> getAllClasses() {
        log.info("Fetching all school classes");
        List<SchoolClass> classes = schoolClassRepository.findAll();
        return schoolClassMapper.toResponseList(classes);
    }

    /**
     * Get school class by ID
     */
    @Transactional(readOnly = true)
    public SchoolClassResponse getClassById(Long id) {
        log.info("Fetching school class with ID: {}", id);
        SchoolClass schoolClass = findClassByIdOrThrow(id);
        return schoolClassMapper.toResponse(schoolClass);
    }

    /**
     * Get school class by ID with subjects
     */
    @Transactional(readOnly = true)
    public SchoolClassResponse getClassByIdWithSubjects(Long id) {
        log.info("Fetching school class with ID and subjects: {}", id);
        SchoolClass schoolClass = schoolClassRepository.findByIdWithSubjects(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Class", "id", id));
        return schoolClassMapper.toResponse(schoolClass);
    }

    /**
     * Update a school class
     */
    @Transactional
    public SchoolClassResponse updateClass(Long id, SchoolClassRequest request) {
        log.info("Updating school class with ID: {}", id);

        SchoolClass schoolClass = findClassByIdOrThrow(id);

        // Check for duplicate name (excluding current class)
        if (schoolClassRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("School Class", "name", request.getName());
        }

        schoolClassMapper.updateEntityFromRequest(request, schoolClass);
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);

        log.info("School class updated successfully with ID: {}", updatedClass.getId());
        return schoolClassMapper.toResponse(updatedClass);
    }

    /**
     * Delete a school class
     */
    @Transactional
    public void deleteClass(Long id) {
        log.info("Deleting school class with ID: {}", id);

        SchoolClass schoolClass = findClassByIdOrThrow(id);
        schoolClassRepository.delete(schoolClass);

        log.info("School class deleted successfully with ID: {}", id);
    }

    /**
     * Assign subjects to a class (SET-05)
     */
    @Transactional
    public SchoolClassResponse assignSubjectsToClass(ClassSubjectAssignmentRequest request) {
        log.info("Assigning {} subjects to class ID: {}", request.getSubjectIds().size(), request.getClassId());

        SchoolClass schoolClass = schoolClassRepository.findByIdWithSubjects(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("School Class", "id", request.getClassId()));

        // Fetch all subjects
        List<Subject> subjects = subjectRepository.findByIdIn(request.getSubjectIds());

        if (subjects.size() != request.getSubjectIds().size()) {
            throw new ResourceNotFoundException("One or more subjects not found");
        }

        // Assign subjects to class
        schoolClass.setSubjects(new HashSet<>(subjects));
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);

        log.info("Subjects assigned successfully to class ID: {}", request.getClassId());
        return schoolClassMapper.toResponse(updatedClass);
    }

    /**
     * Remove subjects from a class
     */
    @Transactional
    public SchoolClassResponse removeSubjectsFromClass(Long classId, Set<Long> subjectIds) {
        log.info("Removing {} subjects from class ID: {}", subjectIds.size(), classId);

        SchoolClass schoolClass = schoolClassRepository.findByIdWithSubjects(classId)
                .orElseThrow(() -> new ResourceNotFoundException("School Class", "id", classId));

        // Remove subjects
        schoolClass.getSubjects().removeIf(subject -> subjectIds.contains(subject.getId()));
        SchoolClass updatedClass = schoolClassRepository.save(schoolClass);

        log.info("Subjects removed successfully from class ID: {}", classId);
        return schoolClassMapper.toResponse(updatedClass);
    }

    /**
     * Helper method to find class by ID or throw exception
     */
    private SchoolClass findClassByIdOrThrow(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Class", "id", id));
    }
}
