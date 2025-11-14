package com.smartschool.setup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for School Class
 * Requirement: SET-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassResponse {

    private Long id;
    private String name;
    private Integer sectionCount;
    private Integer subjectCount;
    private List<SubjectResponse> subjects;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
