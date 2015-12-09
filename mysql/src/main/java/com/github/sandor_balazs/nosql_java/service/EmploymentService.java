package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Employment;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Employment.
 */
public interface EmploymentService {

    /**
     * Save a employment.
     * @return the persisted entity
     */
    public EmploymentDTO save(EmploymentDTO employmentDTO);

    /**
     *  get all the employments.
     *  @return the list of entities
     */
    public List<EmploymentDTO> findAll();

    /**
     *  get the "id" employment.
     *  @return the entity
     */
    public EmploymentDTO findOne(Long id);

    /**
     *  delete the "id" employment.
     */
    public void delete(Long id);
}
