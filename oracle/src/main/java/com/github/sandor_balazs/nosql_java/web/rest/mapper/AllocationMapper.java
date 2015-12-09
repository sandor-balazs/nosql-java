package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Allocation and its DTO AllocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AllocationMapper {

    @Mapping(source = "projectCountry.id", target = "projectCountryId")
    @Mapping(source = "employment.id", target = "employmentId")
    @Mapping(source = "skill.id", target = "skillId")
    AllocationDTO allocationToAllocationDTO(Allocation allocation);

    @Mapping(source = "projectCountryId", target = "projectCountry")
    @Mapping(source = "employmentId", target = "employment")
    @Mapping(source = "skillId", target = "skill")
    Allocation allocationDTOToAllocation(AllocationDTO allocationDTO);

    default ProjectCountry projectCountryFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectCountry projectCountry = new ProjectCountry();
        projectCountry.setId(id);
        return projectCountry;
    }

    default Employment employmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employment employment = new Employment();
        employment.setId(id);
        return employment;
    }

    default Skill skillFromId(Long id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
