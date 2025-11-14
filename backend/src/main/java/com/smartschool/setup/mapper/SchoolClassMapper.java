package com.smartschool.setup.mapper;

import com.smartschool.setup.dto.SchoolClassRequest;
import com.smartschool.setup.dto.SchoolClassResponse;
import com.smartschool.setup.entity.SchoolClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for SchoolClass entity
 * Requirement: SET-02
 */
@Mapper(
    componentModel = "spring",
    uses = {SubjectMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SchoolClassMapper {

    SchoolClass toEntity(SchoolClassRequest request);

    @Mapping(target = "sectionCount", expression = "java(entity.getSections() != null ? entity.getSections().size() : 0)")
    @Mapping(target = "subjectCount", expression = "java(entity.getSubjects() != null ? entity.getSubjects().size() : 0)")
    @Mapping(target = "subjects", source = "subjects")
    SchoolClassResponse toResponse(SchoolClass entity);

    List<SchoolClassResponse> toResponseList(List<SchoolClass> entities);

    void updateEntityFromRequest(SchoolClassRequest request, @MappingTarget SchoolClass entity);
}
