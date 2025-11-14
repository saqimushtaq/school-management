package com.smartschool.setup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO for creating/updating an Academic Session
 * Requirement: SET-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSessionRequest {

    @NotBlank(message = "Session name is required")
    private String name;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private Boolean isCurrent;
}
