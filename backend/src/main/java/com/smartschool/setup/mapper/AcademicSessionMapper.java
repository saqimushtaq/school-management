package com.smartschool.setup.mapper;

import com.smartschool.setup.dto.AcademicSessionRequest;
import com.smartschool.setup.dto.AcademicSessionResponse;
import com.smartschool.setup.entity.AcademicSession;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for AcademicSession entity
 * Requirement: SET-01
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AcademicSessionMapper {

    AcademicSession toEntity(AcademicSessionRequest request);

    AcademicSessionResponse toResponse(AcademicSession entity);

    List<AcademicSessionResponse> toResponseList(List<AcademicSession> entities);

    void updateEntityFromRequest(AcademicSessionRequest request, @MappingTarget AcademicSession entity);
}
