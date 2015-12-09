package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectRegionService;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;
import com.github.sandor_balazs.nosql_java.repository.ProjectRegionRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProjectRegion.
 */
@Service
@Transactional
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
    @Transactional(readOnly = true) 
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
    @Transactional(readOnly = true) 
    public ProjectRegionDTO findOne(Long id) {
        log.debug("Request to get ProjectRegion : {}", id);
        ProjectRegion projectRegion = projectRegionRepository.findOne(id);
        ProjectRegionDTO projectRegionDTO = projectRegionMapper.projectRegionToProjectRegionDTO(projectRegion);
        return projectRegionDTO;
    }

    /**
     *  delete the  projectRegion by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectRegion : {}", id);
        projectRegionRepository.delete(id);
    }
}
