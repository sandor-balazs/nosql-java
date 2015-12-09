package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectCountryMilestoneService;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectCountryMilestoneRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMilestoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProjectCountryMilestone.
 */
@Service
public class ProjectCountryMilestoneServiceImpl implements ProjectCountryMilestoneService{

    private final Logger log = LoggerFactory.getLogger(ProjectCountryMilestoneServiceImpl.class);
    
    @Inject
    private ProjectCountryMilestoneRepository projectCountryMilestoneRepository;
    
    @Inject
    private ProjectCountryMilestoneMapper projectCountryMilestoneMapper;
    
    /**
     * Save a projectCountryMilestone.
     * @return the persisted entity
     */
    public ProjectCountryMilestoneDTO save(ProjectCountryMilestoneDTO projectCountryMilestoneDTO) {
        log.debug("Request to save ProjectCountryMilestone : {}", projectCountryMilestoneDTO);
        ProjectCountryMilestone projectCountryMilestone = projectCountryMilestoneMapper.projectCountryMilestoneDTOToProjectCountryMilestone(projectCountryMilestoneDTO);
        projectCountryMilestone = projectCountryMilestoneRepository.save(projectCountryMilestone);
        ProjectCountryMilestoneDTO result = projectCountryMilestoneMapper.projectCountryMilestoneToProjectCountryMilestoneDTO(projectCountryMilestone);
        return result;
    }

    /**
     *  get all the projectCountryMilestones.
     *  @return the list of entities
     */
    public List<ProjectCountryMilestoneDTO> findAll() {
        log.debug("Request to get all ProjectCountryMilestones");
        List<ProjectCountryMilestoneDTO> result = projectCountryMilestoneRepository.findAll().stream()
            .map(projectCountryMilestoneMapper::projectCountryMilestoneToProjectCountryMilestoneDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one projectCountryMilestone by id.
     *  @return the entity
     */
    public ProjectCountryMilestoneDTO findOne(String id) {
        log.debug("Request to get ProjectCountryMilestone : {}", id);
        ProjectCountryMilestone projectCountryMilestone = projectCountryMilestoneRepository.findOne(UUID.fromString(id));
        ProjectCountryMilestoneDTO projectCountryMilestoneDTO = projectCountryMilestoneMapper.projectCountryMilestoneToProjectCountryMilestoneDTO(projectCountryMilestone);
        return projectCountryMilestoneDTO;
    }

    /**
     *  delete the  projectCountryMilestone by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectCountryMilestone : {}", id);
        projectCountryMilestoneRepository.delete(UUID.fromString(id));
    }
}
