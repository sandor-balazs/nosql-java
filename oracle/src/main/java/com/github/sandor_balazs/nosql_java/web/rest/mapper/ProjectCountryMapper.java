package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectCountry and its DTO ProjectCountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectCountryMapper {

    @Mapping(source = "projectRegion.id", target = "projectRegionId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    ProjectCountryDTO projectCountryToProjectCountryDTO(ProjectCountry projectCountry);

    @Mapping(source = "projectRegionId", target = "projectRegion")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "statusId", target = "status")
    ProjectCountry projectCountryDTOToProjectCountry(ProjectCountryDTO projectCountryDTO);

    default ProjectRegion projectRegionFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectRegion projectRegion = new ProjectRegion();
        projectRegion.setId(id);
        return projectRegion;
    }

    default Country countryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Country country = new Country();
        country.setId(id);
        return country;
    }

    default Status statusFromId(Long id) {
        if (id == null) {
            return null;
        }
        Status status = new Status();
        status.setId(id);
        return status;
    }
}
