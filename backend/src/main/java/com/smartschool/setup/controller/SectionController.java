package com.smartschool.setup.controller;

import com.smartschool.setup.dto.SectionRequest;
import com.smartschool.setup.dto.SectionResponse;
import com.smartschool.setup.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Section Management
 * Requirement: SET-03
 */
@Tag(name = "Sections", description = "APIs for managing sections")
@RestController
@RequestMapping("/api/setup/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @Operation(summary = "Create a new section")
    @PostMapping
    public ResponseEntity<SectionResponse> createSection(
            @Valid @RequestBody SectionRequest request) {
        SectionResponse response = sectionService.createSection(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all sections")
    @GetMapping
    public ResponseEntity<List<SectionResponse>> getAllSections() {
        List<SectionResponse> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    @Operation(summary = "Get sections by class ID")
    @GetMapping("/by-class/{classId}")
    public ResponseEntity<List<SectionResponse>> getSectionsByClassId(@PathVariable Long classId) {
        List<SectionResponse> sections = sectionService.getSectionsByClassId(classId);
        return ResponseEntity.ok(sections);
    }

    @Operation(summary = "Get section by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SectionResponse> getSectionById(@PathVariable Long id) {
        SectionResponse response = sectionService.getSectionById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a section")
    @PutMapping("/{id}")
    public ResponseEntity<SectionResponse> updateSection(
            @PathVariable Long id,
            @Valid @RequestBody SectionRequest request) {
        SectionResponse response = sectionService.updateSection(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a section")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}
