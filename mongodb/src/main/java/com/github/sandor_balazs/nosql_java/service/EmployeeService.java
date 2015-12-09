package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Employee;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmployeeDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Employee.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     * @return the persisted entity
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO);

    /**
     *  get all the employees.
     *  @return the list of entities
     */
    public List<EmployeeDTO> findAll();

    /**
     *  get the "id" employee.
     *  @return the entity
     */
    public EmployeeDTO findOne(String id);

    /**
     *  delete the "id" employee.
     */
    public void delete(String id);
}
