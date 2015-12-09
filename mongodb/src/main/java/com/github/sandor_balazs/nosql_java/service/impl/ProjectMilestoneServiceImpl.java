package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectMilestoneService;
import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectMilestoneRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectMilestoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProjectMilestone.
 */
@Service
public class ProjectMilestoneServiceImpl implements ProjectMilestoneService{

    private final Logger log = LoggerFactory.getLogger(ProjectMilestoneServiceImpl.class);
    
    @Inject
    private ProjectMilestoneRepository projectMilestoneRepository;
    
    @Inject
    private ProjectMilestoneMapper projectMilestoneMapper;
    
    /**
     * Save a projectMilestone.
     * @return the persisted entity
     */
    public ProjectMilestoneDTO save(ProjectMilestoneDTO projectMilestoneDTO) {
        log.debug("Request to save ProjectMilestone : {}", projectMilestoneDTO);
        ProjectMilestone projectMilestone = projectMilestoneMapper.projectMilestoneDTOToProjectMilestone(projectMilestoneDTO);
        projectMilestone = projectMilestoneRepository.save(projectMilestone);
        ProjectMilestoneDTO result = projectMilestoneMapper.projectMilestoneToProjectMilestoneDTO(projectMilestone);
        return result;
    }

    /**
     *  get all the projectMilestones.
     *  @return the list of entities
     */
    public List<ProjectMilestoneDTO> findAll() {
        log.debug("Request to get all ProjectMilestones");
        List<ProjectMilestoneDTO> result = projectMilestoneRepository.findAll().stream()
            .map(projectMilestoneMapper::projectMilestoneToProjectMilestoneDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one projectMilestone by id.
     *  @return the entity
     */
    public ProjectMilestoneDTO findOne(String id) {
        log.debug("Request to get ProjectMilestone : {}", id);
        ProjectMilestone projectMilestone = projectMilestoneRepository.findOne(id);
        ProjectMilestoneDTO projectMilestoneDTO = projectMilestoneMapper.projectMilestoneToProjectMilestoneDTO(projectMilestone);
        return projectMilestoneDTO;
    }

    /**
     *  delete the  projectMilestone by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectMilestone : {}", id);
        projectMilestoneRepository.delete(id);
    }
}
