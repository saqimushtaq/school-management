package com.smartschool.setup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating/updating a Section
 * Requirement: SET-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionRequest {

    @NotBlank(message = "Section name is required")
    private String name;

    @NotNull(message = "Class ID is required")
    private Long classId;
}
