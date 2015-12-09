package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectCountry and its DTO ProjectCountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectCountryMapper {

    ProjectCountryDTO projectCountryToProjectCountryDTO(ProjectCountry projectCountry);

    ProjectCountry projectCountryDTOToProjectCountry(ProjectCountryDTO projectCountryDTO);
}
