package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ProjectCountryMilestone.
 */
public interface ProjectCountryMilestoneService {

    /**
     * Save a projectCountryMilestone.
     * @return the persisted entity
     */
    public ProjectCountryMilestoneDTO save(ProjectCountryMilestoneDTO projectCountryMilestoneDTO);

    /**
     *  get all the projectCountryMilestones.
     *  @return the list of entities
     */
    public List<ProjectCountryMilestoneDTO> findAll();

    /**
     *  get the "id" projectCountryMilestone.
     *  @return the entity
     */
    public ProjectCountryMilestoneDTO findOne(Long id);

    /**
     *  delete the "id" projectCountryMilestone.
     */
    public void delete(Long id);
}
