package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Allocation;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Allocation.
 */
public interface AllocationService {

    /**
     * Save a allocation.
     * @return the persisted entity
     */
    public AllocationDTO save(AllocationDTO allocationDTO);

    /**
     *  get all the allocations.
     *  @return the list of entities
     */
    public List<AllocationDTO> findAll();

    /**
     *  get the "id" allocation.
     *  @return the entity
     */
    public AllocationDTO findOne(Long id);

    /**
     *  delete the "id" allocation.
     */
    public void delete(Long id);
}
