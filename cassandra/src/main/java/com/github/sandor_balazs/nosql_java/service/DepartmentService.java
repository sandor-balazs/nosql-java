package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Department;
import com.github.sandor_balazs.nosql_java.web.rest.dto.DepartmentDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Department.
 */
public interface DepartmentService {

    /**
     * Save a department.
     * @return the persisted entity
     */
    public DepartmentDTO save(DepartmentDTO departmentDTO);

    /**
     *  get all the departments.
     *  @return the list of entities
     */
    public List<DepartmentDTO> findAll();

    /**
     *  get the "id" department.
     *  @return the entity
     */
    public DepartmentDTO findOne(String id);

    /**
     *  delete the "id" department.
     */
    public void delete(String id);
}
