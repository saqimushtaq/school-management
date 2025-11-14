package com.smartschool.setup.repository;

import com.smartschool.setup.entity.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for AcademicSession entity
 * Requirement: SET-01
 */
@Repository
public interface AcademicSessionRepository extends JpaRepository<AcademicSession, Long> {

    /**
     * Find the current academic session
     */
    Optional<AcademicSession> findByIsCurrentTrue();

    /**
     * Check if a session with the given name exists
     */
    boolean existsByName(String name);

    /**
     * Check if a session with the given name exists (excluding a specific ID)
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * Set all sessions as not current
     */
    @Modifying
    @Query("UPDATE AcademicSession a SET a.isCurrent = false WHERE a.isCurrent = true")
    void unsetAllCurrentSessions();
}
