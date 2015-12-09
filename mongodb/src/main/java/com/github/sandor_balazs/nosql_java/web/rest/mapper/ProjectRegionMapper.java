package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectRegion and its DTO ProjectRegionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectRegionMapper {

    ProjectRegionDTO projectRegionToProjectRegionDTO(ProjectRegion projectRegion);

    ProjectRegion projectRegionDTOToProjectRegion(ProjectRegionDTO projectRegionDTO);
}
