package com.smartschool.setup.repository;

import com.smartschool.setup.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository for Subject entity
 * Requirement: SET-04, SET-05
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    /**
     * Check if a subject with the given name exists
     */
    boolean existsByName(String name);

    /**
     * Check if a subject with the given name exists (excluding a specific ID)
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Check if a subject with the given code exists
     */
    boolean existsByCode(String code);

    /**
     * Check if a subject with the given code exists (excluding a specific ID)
     */
    boolean existsByCodeAndIdNot(String code, Long id);

    /**
     * Find all subjects by their IDs
     */
    List<Subject> findByIdIn(Set<Long> ids);
}
