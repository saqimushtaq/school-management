package com.smartschool.setup.repository;

import com.smartschool.setup.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SchoolClass entity
 * Requirement: SET-02
 */
@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    /**
     * Check if a class with the given name exists
     */
    boolean existsByName(String name);

    /**
     * Check if a class with the given name exists (excluding a specific ID)
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Find a class by ID with subjects eagerly loaded
     */
    @Query("SELECT c FROM SchoolClass c LEFT JOIN FETCH c.subjects WHERE c.id = :id")
    Optional<SchoolClass> findByIdWithSubjects(Long id);
}
