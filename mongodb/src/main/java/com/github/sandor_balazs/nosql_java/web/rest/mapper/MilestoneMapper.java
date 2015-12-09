package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.MilestoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Milestone and its DTO MilestoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MilestoneMapper {

    MilestoneDTO milestoneToMilestoneDTO(Milestone milestone);

    Milestone milestoneDTOToMilestone(MilestoneDTO milestoneDTO);
}
