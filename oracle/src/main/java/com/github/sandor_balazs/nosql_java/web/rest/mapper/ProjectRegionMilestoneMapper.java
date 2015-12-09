package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectRegionMilestone and its DTO ProjectRegionMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectRegionMilestoneMapper {

    @Mapping(source = "projectRegion.id", target = "projectRegionId")
    @Mapping(source = "milestone.id", target = "milestoneId")
    @Mapping(source = "milestone.name", target = "milestoneName")
    ProjectRegionMilestoneDTO projectRegionMilestoneToProjectRegionMilestoneDTO(ProjectRegionMilestone projectRegionMilestone);

    @Mapping(source = "projectRegionId", target = "projectRegion")
    @Mapping(source = "milestoneId", target = "milestone")
    ProjectRegionMilestone projectRegionMilestoneDTOToProjectRegionMilestone(ProjectRegionMilestoneDTO projectRegionMilestoneDTO);

    default ProjectRegion projectRegionFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectRegion projectRegion = new ProjectRegion();
        projectRegion.setId(id);
        return projectRegion;
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
