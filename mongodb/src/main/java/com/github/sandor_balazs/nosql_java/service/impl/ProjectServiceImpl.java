package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectService;
import com.github.sandor_balazs.nosql_java.domain.Project;
import com.github.sandor_balazs.nosql_java.repository.ProjectRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Project.
 */
@Service
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
    
    @Inject
    private ProjectRepository projectRepository;
    
    @Inject
    private ProjectMapper projectMapper;
    
    /**
     * Save a project.
     * @return the persisted entity
     */
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.projectDTOToProject(projectDTO);
        project = projectRepository.save(project);
        ProjectDTO result = projectMapper.projectToProjectDTO(project);
        return result;
    }

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    public List<ProjectDTO> findAll() {
        log.debug("Request to get all Projects");
        List<ProjectDTO> result = projectRepository.findAll().stream()
            .map(projectMapper::projectToProjectDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one project by id.
     *  @return the entity
     */
    public ProjectDTO findOne(String id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOne(id);
        ProjectDTO projectDTO = projectMapper.projectToProjectDTO(project);
        return projectDTO;
    }

    /**
     *  delete the  project by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
}
