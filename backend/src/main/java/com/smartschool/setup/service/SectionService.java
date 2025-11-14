package com.smartschool.setup.service;

import com.smartschool.exception.DuplicateResourceException;
import com.smartschool.exception.ResourceNotFoundException;
import com.smartschool.setup.dto.SectionRequest;
import com.smartschool.setup.dto.SectionResponse;
import com.smartschool.setup.entity.SchoolClass;
import com.smartschool.setup.entity.Section;
import com.smartschool.setup.mapper.SectionMapper;
import com.smartschool.setup.repository.SchoolClassRepository;
import com.smartschool.setup.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing Sections
 * Requirement: SET-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SectionMapper sectionMapper;

    /**
     * Create a new section
     */
    @Transactional
    public SectionResponse createSection(SectionRequest request) {
        log.info("Creating new section: {} for class ID: {}", request.getName(), request.getClassId());

        // Verify class exists
        SchoolClass schoolClass = findClassByIdOrThrow(request.getClassId());

        // Check for duplicate section name within the class
        if (sectionRepository.existsBySchoolClassIdAndName(request.getClassId(), request.getName())) {
            throw new DuplicateResourceException(
                    String.format("Section '%s' already exists for class '%s'", request.getName(), schoolClass.getName())
            );
        }

        Section section = sectionMapper.toEntity(request);
        section.setSchoolClass(schoolClass);

        Section savedSection = sectionRepository.save(section);

        log.info("Section created successfully with ID: {}", savedSection.getId());
        return sectionMapper.toResponse(savedSection);
    }

    /**
     * Get all sections
     */
    @Transactional(readOnly = true)
    public List<SectionResponse> getAllSections() {
        log.info("Fetching all sections");
        List<Section> sections = sectionRepository.findAll();
        return sectionMapper.toResponseList(sections);
    }

    /**
     * Get all sections for a specific class
     */
    @Transactional(readOnly = true)
    public List<SectionResponse> getSectionsByClassId(Long classId) {
        log.info("Fetching sections for class ID: {}", classId);

        // Verify class exists
        findClassByIdOrThrow(classId);

        List<Section> sections = sectionRepository.findBySchoolClassId(classId);
        return sectionMapper.toResponseList(sections);
    }

    /**
     * Get section by ID
     */
    @Transactional(readOnly = true)
    public SectionResponse getSectionById(Long id) {
        log.info("Fetching section with ID: {}", id);
        Section section = findSectionByIdOrThrow(id);
        return sectionMapper.toResponse(section);
    }

    /**
     * Update a section
     */
    @Transactional
    public SectionResponse updateSection(Long id, SectionRequest request) {
        log.info("Updating section with ID: {}", id);

        Section section = findSectionByIdOrThrow(id);

        // If class is being changed, verify new class exists
        if (!section.getSchoolClass().getId().equals(request.getClassId())) {
            SchoolClass newClass = findClassByIdOrThrow(request.getClassId());
            section.setSchoolClass(newClass);
        }

        // Check for duplicate section name within the class (excluding current section)
        if (sectionRepository.existsBySchoolClassIdAndNameAndIdNot(request.getClassId(), request.getName(), id)) {
            throw new DuplicateResourceException(
                    String.format("Section '%s' already exists for this class", request.getName())
            );
        }

        sectionMapper.updateEntityFromRequest(request, section);
        Section updatedSection = sectionRepository.save(section);

        log.info("Section updated successfully with ID: {}", updatedSection.getId());
        return sectionMapper.toResponse(updatedSection);
    }

    /**
     * Delete a section
     */
    @Transactional
    public void deleteSection(Long id) {
        log.info("Deleting section with ID: {}", id);

        Section section = findSectionByIdOrThrow(id);
        sectionRepository.delete(section);

        log.info("Section deleted successfully with ID: {}", id);
    }

    /**
     * Helper method to find section by ID or throw exception
     */
    private Section findSectionByIdOrThrow(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
    }

    /**
     * Helper method to find class by ID or throw exception
     */
    private SchoolClass findClassByIdOrThrow(Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Class", "id", id));
    }
}
