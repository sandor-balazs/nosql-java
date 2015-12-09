package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employment and its DTO EmploymentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmploymentMapper {

    EmploymentDTO employmentToEmploymentDTO(Employment employment);

    Employment employmentDTOToEmployment(EmploymentDTO employmentDTO);
}
