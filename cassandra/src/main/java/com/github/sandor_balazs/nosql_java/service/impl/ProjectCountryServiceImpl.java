package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.ProjectCountryService;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;
import com.github.sandor_balazs.nosql_java.repository.ProjectCountryRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMapper;
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
 * Service Implementation for managing ProjectCountry.
 */
@Service
public class ProjectCountryServiceImpl implements ProjectCountryService{

    private final Logger log = LoggerFactory.getLogger(ProjectCountryServiceImpl.class);
    
    @Inject
    private ProjectCountryRepository projectCountryRepository;
    
    @Inject
    private ProjectCountryMapper projectCountryMapper;
    
    /**
     * Save a projectCountry.
     * @return the persisted entity
     */
    public ProjectCountryDTO save(ProjectCountryDTO projectCountryDTO) {
        log.debug("Request to save ProjectCountry : {}", projectCountryDTO);
        ProjectCountry projectCountry = projectCountryMapper.projectCountryDTOToProjectCountry(projectCountryDTO);
        projectCountry = projectCountryRepository.save(projectCountry);
        ProjectCountryDTO result = projectCountryMapper.projectCountryToProjectCountryDTO(projectCountry);
        return result;
    }

    /**
     *  get all the projectCountrys.
     *  @return the list of entities
     */
    public List<ProjectCountryDTO> findAll() {
        log.debug("Request to get all ProjectCountrys");
        List<ProjectCountryDTO> result = projectCountryRepository.findAll().stream()
            .map(projectCountryMapper::projectCountryToProjectCountryDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one projectCountry by id.
     *  @return the entity
     */
    public ProjectCountryDTO findOne(String id) {
        log.debug("Request to get ProjectCountry : {}", id);
        ProjectCountry projectCountry = projectCountryRepository.findOne(UUID.fromString(id));
        ProjectCountryDTO projectCountryDTO = projectCountryMapper.projectCountryToProjectCountryDTO(projectCountry);
        return projectCountryDTO;
    }

    /**
     *  delete the  projectCountry by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProjectCountry : {}", id);
        projectCountryRepository.delete(UUID.fromString(id));
    }
}
