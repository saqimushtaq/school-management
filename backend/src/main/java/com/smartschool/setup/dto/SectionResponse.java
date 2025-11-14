package com.smartschool.setup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for Section
 * Requirement: SET-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {

    private Long id;
    private String name;
    private Long classId;
    private String className;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
