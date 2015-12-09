package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Funding;
import com.github.sandor_balazs.nosql_java.web.rest.dto.FundingDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Funding.
 */
public interface FundingService {

    /**
     * Save a funding.
     * @return the persisted entity
     */
    public FundingDTO save(FundingDTO fundingDTO);

    /**
     *  get all the fundings.
     *  @return the list of entities
     */
    public List<FundingDTO> findAll();

    /**
     *  get the "id" funding.
     *  @return the entity
     */
    public FundingDTO findOne(String id);

    /**
     *  delete the "id" funding.
     */
    public void delete(String id);
}
