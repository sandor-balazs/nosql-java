package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectRegionService;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;
import com.github.sandor_balazs.nosql_java.repository.ProjectRegionRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMapper;
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
 * Service Implementation for managing ProjectRegion.
 */
@Service
public class ProjectRegionServiceImpl implements ProjectRegionService{

    private final Logger log = LoggerFactory.getLogger(ProjectRegionServiceImpl.class);
    
    @Inject
    private ProjectRegionRepository projectRegionRepository;
    
    @Inject
    private ProjectRegionMapper projectRegionMapper;
    
    /**
     * Save a projectRegion.
     * @return the persisted entity
     */
    public ProjectRegionDTO save(ProjectRegionDTO projectRegionDTO) {
        log.debug("Request to save ProjectRegion : {}", projectRegionDTO);
        ProjectRegion projectRegion = projectRegionMapper.projectRegionDTOToProjectRegion(projectRegionDTO);
        projectRegion = projectRegionRepository.save(projectRegion);
        ProjectRegionDTO result = projectRegionMapper.projectRegionToProjectRegionDTO(projectRegion);
        return result;
    }

    /**
     *  get all the projectRegions.
     *  @return the list of entities
     */
    public List<ProjectRegionDTO> findAll() {
        log.debug("Request to get all ProjectRegions");
        List<ProjectRegionDTO> result = projectRegionRepository.findAll().stream()
            .map(projectRegionMapper::projectRegionToProjectRegionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one projectRegion by id.
     *  @return the entity
     */
    public ProjectRegionDTO findOne(String id) {
        log.debug("Request to get ProjectRegion : {}", id);
        ProjectRegion projectRegion = projectRegionRepository.findOne(UUID.fromString(id));
        ProjectRegionDTO projectRegionDTO = projectRegionMapper.projectRegionToProjectRegionDTO(projectRegion);
        return projectRegionDTO;
    }

    /**
     *  delete the  projectRegion by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectRegion : {}", id);
        projectRegionRepository.delete(UUID.fromString(id));
    }
}
