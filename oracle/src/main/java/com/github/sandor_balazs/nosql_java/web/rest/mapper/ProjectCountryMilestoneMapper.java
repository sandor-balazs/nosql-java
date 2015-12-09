package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectCountryMilestone and its DTO ProjectCountryMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectCountryMilestoneMapper {

    @Mapping(source = "projectCountry.id", target = "projectCountryId")
    @Mapping(source = "milestone.id", target = "milestoneId")
    @Mapping(source = "milestone.name", target = "milestoneName")
    ProjectCountryMilestoneDTO projectCountryMilestoneToProjectCountryMilestoneDTO(ProjectCountryMilestone projectCountryMilestone);

    @Mapping(source = "projectCountryId", target = "projectCountry")
    @Mapping(source = "milestoneId", target = "milestone")
    ProjectCountryMilestone projectCountryMilestoneDTOToProjectCountryMilestone(ProjectCountryMilestoneDTO projectCountryMilestoneDTO);

    default ProjectCountry projectCountryFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectCountry projectCountry = new ProjectCountry();
        projectCountry.setId(id);
        return projectCountry;
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
