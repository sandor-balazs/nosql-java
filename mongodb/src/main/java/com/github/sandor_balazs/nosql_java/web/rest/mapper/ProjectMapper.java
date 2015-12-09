package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper {

    ProjectDTO projectToProjectDTO(Project project);

    Project projectDTOToProject(ProjectDTO projectDTO);
}
