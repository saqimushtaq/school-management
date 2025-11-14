package com.smartschool.setup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating/updating a School Class
 * Requirement: SET-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassRequest {

    @NotBlank(message = "Class name is required")
    private String name;
}
