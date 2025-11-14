package com.smartschool.setup.repository;

import com.smartschool.setup.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Section entity
 * Requirement: SET-03
 */
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    /**
     * Find all sections for a specific class
     */
    @Query("SELECT s FROM Section s WHERE s.schoolClass.id = :classId")
    List<Section> findBySchoolClassId(Long classId);

    /**
     * Check if a section with the given name exists for a specific class
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Section s WHERE s.schoolClass.id = :classId AND s.name = :name")
    boolean existsBySchoolClassIdAndName(Long classId, String name);

    /**
     * Check if a section with the given name exists for a specific class (excluding a specific ID)
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Section s WHERE s.schoolClass.id = :classId AND s.name = :name AND s.id != :id")
    boolean existsBySchoolClassIdAndNameAndIdNot(Long classId, String name, Long id);
}
