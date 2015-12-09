package com.github.sandor_balazs.nosql_java.web.rest.mapper;

import com.github.sandor_balazs.nosql_java.domain.*;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employment and its DTO EmploymentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmploymentMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.userId", target = "employeeUserId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    EmploymentDTO employmentToEmploymentDTO(Employment employment);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "departmentId", target = "department")
    Employment employmentDTOToEmployment(EmploymentDTO employmentDTO);

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
