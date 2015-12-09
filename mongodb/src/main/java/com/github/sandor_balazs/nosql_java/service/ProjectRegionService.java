package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ProjectRegion.
 */
public interface ProjectRegionService {

    /**
     * Save a projectRegion.
     * @return the persisted entity
     */
    public ProjectRegionDTO save(ProjectRegionDTO projectRegionDTO);

    /**
     *  get all the projectRegions.
     *  @return the list of entities
     */
    public List<ProjectRegionDTO> findAll();

    /**
     *  get the "id" projectRegion.
     *  @return the entity
     */
    public ProjectRegionDTO findOne(String id);

    /**
     *  delete the "id" projectRegion.
     */
    public void delete(String id);
}
