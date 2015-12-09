package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Status;
import com.github.sandor_balazs.nosql_java.web.rest.dto.StatusDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Status.
 */
public interface StatusService {

    /**
     * Save a status.
     * @return the persisted entity
     */
    public StatusDTO save(StatusDTO statusDTO);

    /**
     *  get all the statuss.
     *  @return the list of entities
     */
    public List<StatusDTO> findAll();

    /**
     *  get the "id" status.
     *  @return the entity
     */
    public StatusDTO findOne(String id);

    /**
     *  delete the "id" status.
     */
    public void delete(String id);
}
