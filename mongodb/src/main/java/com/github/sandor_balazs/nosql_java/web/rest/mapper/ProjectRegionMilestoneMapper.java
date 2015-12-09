package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectRegionMilestone and its DTO ProjectRegionMilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectRegionMilestoneMapper {

    ProjectRegionMilestoneDTO projectRegionMilestoneToProjectRegionMilestoneDTO(ProjectRegionMilestone projectRegionMilestone);

    ProjectRegionMilestone projectRegionMilestoneDTOToProjectRegionMilestone(ProjectRegionMilestoneDTO projectRegionMilestoneDTO);
}
