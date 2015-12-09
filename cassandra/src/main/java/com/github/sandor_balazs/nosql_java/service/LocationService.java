package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Location;
import com.github.sandor_balazs.nosql_java.web.rest.dto.LocationDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Location.
 */
public interface LocationService {

    /**
     * Save a location.
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO locationDTO);

    /**
     *  get all the locations.
     *  @return the list of entities
     */
    public List<LocationDTO> findAll();

    /**
     *  get the "id" location.
     *  @return the entity
     */
    public LocationDTO findOne(String id);

    /**
     *  delete the "id" location.
     */
    public void delete(String id);
}
