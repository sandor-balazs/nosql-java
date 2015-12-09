package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ProjectMilestone.
 */
public interface ProjectMilestoneService {

    /**
     * Save a projectMilestone.
     * @return the persisted entity
     */
    public ProjectMilestoneDTO save(ProjectMilestoneDTO projectMilestoneDTO);

    /**
     *  get all the projectMilestones.
     *  @return the list of entities
     */
    public List<ProjectMilestoneDTO> findAll();

    /**
     *  get the "id" projectMilestone.
     *  @return the entity
     */
    public ProjectMilestoneDTO findOne(Long id);

    /**
     *  delete the "id" projectMilestone.
     */
    public void delete(Long id);
}
