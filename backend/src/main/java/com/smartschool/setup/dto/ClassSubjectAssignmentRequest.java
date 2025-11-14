package com.smartschool.setup.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Request DTO for assigning subjects to a class
 * Requirement: SET-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectAssignmentRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotEmpty(message = "At least one subject must be selected")
    private Set<Long> subjectIds;
}
