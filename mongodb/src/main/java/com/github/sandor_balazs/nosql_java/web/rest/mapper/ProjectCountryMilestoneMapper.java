package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectCountryMilestone and its DTO ProjectCountryMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectCountryMilestoneMapper {

    ProjectCountryMilestoneDTO projectCountryMilestoneToProjectCountryMilestoneDTO(ProjectCountryMilestone projectCountryMilestone);

    ProjectCountryMilestone projectCountryMilestoneDTOToProjectCountryMilestone(ProjectCountryMilestoneDTO projectCountryMilestoneDTO);
}
