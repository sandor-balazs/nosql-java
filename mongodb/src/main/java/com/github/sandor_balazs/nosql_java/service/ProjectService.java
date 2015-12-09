package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Project;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     * @return the persisted entity
     */
    public ProjectDTO save(ProjectDTO projectDTO);

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    public List<ProjectDTO> findAll();

    /**
     *  get the "id" project.
     *  @return the entity
     */
    public ProjectDTO findOne(String id);

    /**
     *  delete the "id" project.
     */
    public void delete(String id);
}
