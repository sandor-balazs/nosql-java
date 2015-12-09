package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    @Mapping(source = "funding.id", target = "fundingId")
    @Mapping(source = "funding.name", target = "fundingName")
    ProjectDTO projectToProjectDTO(Project project);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "fundingId", target = "funding")
    Project projectDTOToProject(ProjectDTO projectDTO);

    default Status statusFromId(Long id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }

    default Funding fundingFromId(Long id) {
        if (id == null) {
            return null;
        }
        Funding funding = new Funding();
        funding.setId(id);
        return funding;
    }
}
