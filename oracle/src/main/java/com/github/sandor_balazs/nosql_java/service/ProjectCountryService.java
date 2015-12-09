package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ProjectCountry.
 */
public interface ProjectCountryService {

    /**
     * Save a projectCountry.
     * @return the persisted entity
     */
    public ProjectCountryDTO save(ProjectCountryDTO projectCountryDTO);

    /**
     *  get all the projectCountrys.
     *  @return the list of entities
     */
    public List<ProjectCountryDTO> findAll();

    /**
     *  get the "id" projectCountry.
     *  @return the entity
     */
    public ProjectCountryDTO findOne(Long id);

    /**
     *  delete the "id" projectCountry.
     */
    public void delete(Long id);
}
