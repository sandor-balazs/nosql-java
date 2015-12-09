package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    DepartmentDTO departmentToDepartmentDTO(Department department);

    @Mapping(source = "locationId", target = "location")
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
