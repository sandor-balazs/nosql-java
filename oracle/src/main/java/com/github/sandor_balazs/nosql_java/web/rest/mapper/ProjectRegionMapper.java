package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectRegion and its DTO ProjectRegionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectRegionMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.code", target = "projectCode")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    ProjectRegionDTO projectRegionToProjectRegionDTO(ProjectRegion projectRegion);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "statusId", target = "status")
    ProjectRegion projectRegionDTOToProjectRegion(ProjectRegionDTO projectRegionDTO);

    default Project projectFromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

    default Region regionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }

    default Status statusFromId(Long id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }
}
