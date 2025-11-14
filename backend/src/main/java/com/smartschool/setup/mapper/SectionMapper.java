package com.smartschool.setup.mapper;

import com.smartschool.setup.dto.SectionRequest;
import com.smartschool.setup.dto.SectionResponse;
import com.smartschool.setup.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * MapStruct mapper for Section entity
 * Requirement: SET-03
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SectionMapper {

    @Mapping(target = "schoolClass", ignore = true)
    Section toEntity(SectionRequest request);

    @Mapping(target = "classId", source = "schoolClass.id")
    @Mapping(target = "className", source = "schoolClass.name")
    SectionResponse toResponse(Section entity);

    List<SectionResponse> toResponseList(List<Section> entities);

    @Mapping(target = "schoolClass", ignore = true)
    void updateEntityFromRequest(SectionRequest request, @MappingTarget Section entity);
}
