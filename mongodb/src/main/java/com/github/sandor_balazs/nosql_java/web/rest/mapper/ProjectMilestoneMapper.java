package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectMilestone and its DTO ProjectMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMilestoneMapper {

    ProjectMilestoneDTO projectMilestoneToProjectMilestoneDTO(ProjectMilestone projectMilestone);

    ProjectMilestone projectMilestoneDTOToProjectMilestone(ProjectMilestoneDTO projectMilestoneDTO);
}
