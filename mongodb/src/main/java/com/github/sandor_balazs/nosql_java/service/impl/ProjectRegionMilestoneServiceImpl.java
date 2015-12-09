package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectRegionMilestoneService;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectRegionMilestoneRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMilestoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProjectRegionMilestone.
 */
@Service
public class ProjectRegionMilestoneServiceImpl implements ProjectRegionMilestoneService{

    private final Logger log = LoggerFactory.getLogger(ProjectRegionMilestoneServiceImpl.class);
    
    @Inject
    private ProjectRegionMilestoneRepository projectRegionMilestoneRepository;
    
    @Inject
    private ProjectRegionMilestoneMapper projectRegionMilestoneMapper;
    
    /**
     * Save a projectRegionMilestone.
     * @return the persisted entity
     */
    public ProjectRegionMilestoneDTO save(ProjectRegionMilestoneDTO projectRegionMilestoneDTO) {
        log.debug("Request to save ProjectRegionMilestone : {}", projectRegionMilestoneDTO);
        ProjectRegionMilestone projectRegionMilestone = projectRegionMilestoneMapper.projectRegionMilestoneDTOToProjectRegionMilestone(projectRegionMilestoneDTO);
        projectRegionMilestone = projectRegionMilestoneRepository.save(projectRegionMilestone);
        ProjectRegionMilestoneDTO result = projectRegionMilestoneMapper.projectRegionMilestoneToProjectRegionMilestoneDTO(projectRegionMilestone);
        return result;
    }

    /**
     *  get all the projectRegionMilestones.
     *  @return the list of entities
     */
    public List<ProjectRegionMilestoneDTO> findAll() {
        log.debug("Request to get all ProjectRegionMilestones");
        List<ProjectRegionMilestoneDTO> result = projectRegionMilestoneRepository.findAll().stream()
            .map(projectRegionMilestoneMapper::projectRegionMilestoneToProjectRegionMilestoneDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one projectRegionMilestone by id.
     *  @return the entity
     */
    public ProjectRegionMilestoneDTO findOne(String id) {
        log.debug("Request to get ProjectRegionMilestone : {}", id);
        ProjectRegionMilestone projectRegionMilestone = projectRegionMilestoneRepository.findOne(id);
        ProjectRegionMilestoneDTO projectRegionMilestoneDTO = projectRegionMilestoneMapper.projectRegionMilestoneToProjectRegionMilestoneDTO(projectRegionMilestone);
        return projectRegionMilestoneDTO;
    }

    /**
     *  delete the  projectRegionMilestone by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectRegionMilestone : {}", id);
        projectRegionMilestoneRepository.delete(id);
    }
}
