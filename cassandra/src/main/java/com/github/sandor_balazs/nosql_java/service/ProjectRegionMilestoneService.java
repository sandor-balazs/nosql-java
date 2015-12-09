package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ProjectRegionMilestone.
 */
public interface ProjectRegionMilestoneService {

    /**
     * Save a projectRegionMilestone.
     * @return the persisted entity
     */
    public ProjectRegionMilestoneDTO save(ProjectRegionMilestoneDTO projectRegionMilestoneDTO);

    /**
     *  get all the projectRegionMilestones.
     *  @return the list of entities
     */
    public List<ProjectRegionMilestoneDTO> findAll();

    /**
     *  get the "id" projectRegionMilestone.
     *  @return the entity
     */
    public ProjectRegionMilestoneDTO findOne(String id);

    /**
     *  delete the "id" projectRegionMilestone.
     */
    public void delete(String id);
}
