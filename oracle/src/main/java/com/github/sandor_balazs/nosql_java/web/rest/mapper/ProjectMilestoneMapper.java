package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectMilestone and its DTO ProjectMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMilestoneMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.code", target = "projectCode")
    @Mapping(source = "milestone.id", target = "milestoneId")
    @Mapping(source = "milestone.name", target = "milestoneName")
    ProjectMilestoneDTO projectMilestoneToProjectMilestoneDTO(ProjectMilestone projectMilestone);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "milestoneId", target = "milestone")
    ProjectMilestone projectMilestoneDTOToProjectMilestone(ProjectMilestoneDTO projectMilestoneDTO);

    default Project projectFromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

    default Milestone milestoneFromId(Long id) {
        if (id == null) {
            return null;
        }
        Milestone milestone = new Milestone();
        milestone.setId(id);
        return milestone;
    }
}
