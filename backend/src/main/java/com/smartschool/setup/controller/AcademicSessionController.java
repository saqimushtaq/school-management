package com.smartschool.setup.controller;

import com.smartschool.setup.dto.AcademicSessionRequest;
import com.smartschool.setup.dto.AcademicSessionResponse;
import com.smartschool.setup.service.AcademicSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Academic Session Management
 * Requirement: SET-01
 */
@Tag(name = "Academic Sessions", description = "APIs for managing academic sessions")
@RestController
@RequestMapping("/api/setup/academic-sessions")
@RequiredArgsConstructor
public class AcademicSessionController {

    private final AcademicSessionService academicSessionService;

    @Operation(summary = "Create a new academic session")
    @PostMapping
    public ResponseEntity<AcademicSessionResponse> createSession(
            @Valid @RequestBody AcademicSessionRequest request) {
        AcademicSessionResponse response = academicSessionService.createSession(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all academic sessions")
    @GetMapping
    public ResponseEntity<List<AcademicSessionResponse>> getAllSessions() {
        List<AcademicSessionResponse> sessions = academicSessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @Operation(summary = "Get academic session by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AcademicSessionResponse> getSessionById(@PathVariable Long id) {
        AcademicSessionResponse response = academicSessionService.getSessionById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get the current academic session")
    @GetMapping("/current")
    public ResponseEntity<AcademicSessionResponse> getCurrentSession() {
        AcademicSessionResponse response = academicSessionService.getCurrentSession();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an academic session")
    @PutMapping("/{id}")
    public ResponseEntity<AcademicSessionResponse> updateSession(
            @PathVariable Long id,
            @Valid @RequestBody AcademicSessionRequest request) {
        AcademicSessionResponse response = academicSessionService.updateSession(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Set a session as current")
    @PatchMapping("/{id}/set-current")
    public ResponseEntity<AcademicSessionResponse> setCurrentSession(@PathVariable Long id) {
        AcademicSessionResponse response = academicSessionService.setCurrentSession(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an academic session")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        academicSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
