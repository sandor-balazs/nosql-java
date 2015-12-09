package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Region;
import com.github.sandor_balazs.nosql_java.web.rest.dto.RegionDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Region.
 */
public interface RegionService {

    /**
     * Save a region.
     * @return the persisted entity
     */
    public RegionDTO save(RegionDTO regionDTO);

    /**
     *  get all the regions.
     *  @return the list of entities
     */
    public List<RegionDTO> findAll();

    /**
     *  get the "id" region.
     *  @return the entity
     */
    public RegionDTO findOne(String id);

    /**
     *  delete the "id" region.
     */
    public void delete(String id);
}
