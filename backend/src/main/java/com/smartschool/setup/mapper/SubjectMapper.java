package com.smartschool.setup.mapper;

import com.smartschool.setup.dto.SubjectRequest;
import com.smartschool.setup.dto.SubjectResponse;
import com.smartschool.setup.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for Subject entity
 * Requirement: SET-04
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SubjectMapper {

    Subject toEntity(SubjectRequest request);

    SubjectResponse toResponse(Subject entity);

    List<SubjectResponse> toResponseList(List<Subject> entities);

    void updateEntityFromRequest(SubjectRequest request, @MappingTarget Subject entity);
}
