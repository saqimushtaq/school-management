package com.smartschool.setup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Academic Session
 * Requirement: SET-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSessionResponse {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
