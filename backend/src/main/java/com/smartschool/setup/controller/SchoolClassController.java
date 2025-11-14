package com.smartschool.setup.controller;

import com.smartschool.setup.dto.ClassSubjectAssignmentRequest;
import com.smartschool.setup.dto.SchoolClassRequest;
import com.smartschool.setup.dto.SchoolClassResponse;
import com.smartschool.setup.service.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST Controller for School Class Management
 * Requirement: SET-02, SET-05
 */
@Tag(name = "School Classes", description = "APIs for managing school classes")
@RestController
@RequestMapping("/api/setup/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @Operation(summary = "Create a new school class")
    @PostMapping
    public ResponseEntity<SchoolClassResponse> createClass(
            @Valid @RequestBody SchoolClassRequest request) {
        SchoolClassResponse response = schoolClassService.createClass(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all school classes")
    @GetMapping
    public ResponseEntity<List<SchoolClassResponse>> getAllClasses() {
        List<SchoolClassResponse> classes = schoolClassService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @Operation(summary = "Get school class by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> getClassById(@PathVariable Long id) {
        SchoolClassResponse response = schoolClassService.getClassById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get school class by ID with subjects")
    @GetMapping("/{id}/with-subjects")
    public ResponseEntity<SchoolClassResponse> getClassByIdWithSubjects(@PathVariable Long id) {
        SchoolClassResponse response = schoolClassService.getClassByIdWithSubjects(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a school class")
    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassResponse> updateClass(
            @PathVariable Long id,
            @Valid @RequestBody SchoolClassRequest request) {
        SchoolClassResponse response = schoolClassService.updateClass(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a school class")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign subjects to a class (SET-05)")
    @PostMapping("/assign-subjects")
    public ResponseEntity<SchoolClassResponse> assignSubjectsToClass(
            @Valid @RequestBody ClassSubjectAssignmentRequest request) {
        SchoolClassResponse response = schoolClassService.assignSubjectsToClass(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove subjects from a class")
    @DeleteMapping("/{classId}/subjects")
    public ResponseEntity<SchoolClassResponse> removeSubjectsFromClass(
            @PathVariable Long classId,
            @RequestBody Set<Long> subjectIds) {
        SchoolClassResponse response = schoolClassService.removeSubjectsFromClass(classId, subjectIds);
        return ResponseEntity.ok(response);
    }
}
