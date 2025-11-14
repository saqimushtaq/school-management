package com.smartschool.setup.service;

import com.smartschool.exception.DuplicateResourceException;
import com.smartschool.exception.InvalidOperationException;
import com.smartschool.exception.ResourceNotFoundException;
import com.smartschool.setup.dto.AcademicSessionRequest;
import com.smartschool.setup.dto.AcademicSessionResponse;
import com.smartschool.setup.entity.AcademicSession;
import com.smartschool.setup.mapper.AcademicSessionMapper;
import com.smartschool.setup.repository.AcademicSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing Academic Sessions
 * Requirement: SET-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AcademicSessionService {

    private final AcademicSessionRepository academicSessionRepository;
    private final AcademicSessionMapper academicSessionMapper;

    /**
     * Create a new academic session
     */
    @Transactional
    public AcademicSessionResponse createSession(AcademicSessionRequest request) {
        log.info("Creating new academic session: {}", request.getName());

        // Check for duplicate name
        if (academicSessionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Academic Session", "name", request.getName());
        }

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidOperationException("End date cannot be before start date");
        }

        // If this session should be current, unset all other current sessions
        if (Boolean.TRUE.equals(request.getIsCurrent())) {
            academicSessionRepository.unsetAllCurrentSessions();
        }

        AcademicSession session = academicSessionMapper.toEntity(request);
        AcademicSession savedSession = academicSessionRepository.save(session);

        log.info("Academic session created successfully with ID: {}", savedSession.getId());
        return academicSessionMapper.toResponse(savedSession);
    }

    /**
     * Get all academic sessions
     */
    @Transactional(readOnly = true)
    public List<AcademicSessionResponse> getAllSessions() {
        log.info("Fetching all academic sessions");
        List<AcademicSession> sessions = academicSessionRepository.findAll();
        return academicSessionMapper.toResponseList(sessions);
    }

    /**
     * Get academic session by ID
     */
    @Transactional(readOnly = true)
    public AcademicSessionResponse getSessionById(Long id) {
        log.info("Fetching academic session with ID: {}", id);
        AcademicSession session = findSessionByIdOrThrow(id);
        return academicSessionMapper.toResponse(session);
    }

    /**
     * Get the current academic session
     */
    @Transactional(readOnly = true)
    public AcademicSessionResponse getCurrentSession() {
        log.info("Fetching current academic session");
        AcademicSession session = academicSessionRepository.findByIsCurrentTrue()
                .orElseThrow(() -> new ResourceNotFoundException("No current academic session is set"));
        return academicSessionMapper.toResponse(session);
    }

    /**
     * Update an academic session
     */
    @Transactional
    public AcademicSessionResponse updateSession(Long id, AcademicSessionRequest request) {
        log.info("Updating academic session with ID: {}", id);

        AcademicSession session = findSessionByIdOrThrow(id);

        // Check for duplicate name (excluding current session)
        if (academicSessionRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateResourceException("Academic Session", "name", request.getName());
        }

        // Validate dates
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidOperationException("End date cannot be before start date");
        }

        // If this session should be current, unset all other current sessions
        if (Boolean.TRUE.equals(request.getIsCurrent())) {
            academicSessionRepository.unsetAllCurrentSessions();
        }

        academicSessionMapper.updateEntityFromRequest(request, session);
        AcademicSession updatedSession = academicSessionRepository.save(session);

        log.info("Academic session updated successfully with ID: {}", updatedSession.getId());
        return academicSessionMapper.toResponse(updatedSession);
    }

    /**
     * Set a session as the current session
     */
    @Transactional
    public AcademicSessionResponse setCurrentSession(Long id) {
        log.info("Setting academic session {} as current", id);

        AcademicSession session = findSessionByIdOrThrow(id);

        // Unset all other current sessions
        academicSessionRepository.unsetAllCurrentSessions();

        // Set this session as current
        session.setIsCurrent(true);
        AcademicSession updatedSession = academicSessionRepository.save(session);

        log.info("Academic session {} set as current successfully", id);
        return academicSessionMapper.toResponse(updatedSession);
    }

    /**
     * Delete an academic session
     */
    @Transactional
    public void deleteSession(Long id) {
        log.info("Deleting academic session with ID: {}", id);

        AcademicSession session = findSessionByIdOrThrow(id);

        if (Boolean.TRUE.equals(session.getIsCurrent())) {
            throw new InvalidOperationException("Cannot delete the current academic session");
        }

        academicSessionRepository.delete(session);
        log.info("Academic session deleted successfully with ID: {}", id);
    }

    /**
     * Helper method to find session by ID or throw exception
     */
    private AcademicSession findSessionByIdOrThrow(Long id) {
        return academicSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Academic Session", "id", id));
    }
}
