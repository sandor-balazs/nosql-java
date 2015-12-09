package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Milestone;
import com.github.sandor_balazs.nosql_java.web.rest.dto.MilestoneDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Milestone.
 */
public interface MilestoneService {

    /**
     * Save a milestone.
     * @return the persisted entity
     */
    public MilestoneDTO save(MilestoneDTO milestoneDTO);

    /**
     *  get all the milestones.
     *  @return the list of entities
     */
    public List<MilestoneDTO> findAll();

    /**
     *  get the "id" milestone.
     *  @return the entity
     */
    public MilestoneDTO findOne(String id);

    /**
     *  delete the "id" milestone.
     */
    public void delete(String id);
}
