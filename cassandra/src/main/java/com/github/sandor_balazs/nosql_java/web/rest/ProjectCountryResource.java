package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;
import com.github.sandor_balazs.nosql_java.service.ProjectCountryService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing ProjectCountry.
 */
@RestController
@RequestMapping("/api")
public class ProjectCountryResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCountryResource.class);
        
    @Inject
    private ProjectCountryService projectCountryService;
    
    @Inject
    private ProjectCountryMapper projectCountryMapper;
    
    /**
     * POST  /projectCountrys -> Create a new projectCountry.
     */
    @RequestMapping(value = "/projectCountrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryDTO> createProjectCountry(@RequestBody ProjectCountryDTO projectCountryDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectCountry : {}", projectCountryDTO);
        if (projectCountryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectCountry", "idexists", "A new projectCountry cannot already have an ID")).body(null);
        }
        ProjectCountryDTO result = projectCountryService.save(projectCountryDTO);
        return ResponseEntity.created(new URI("/api/projectCountrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectCountry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectCountrys -> Updates an existing projectCountry.
     */
    @RequestMapping(value = "/projectCountrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryDTO> updateProjectCountry(@RequestBody ProjectCountryDTO projectCountryDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectCountry : {}", projectCountryDTO);
        if (projectCountryDTO.getId() == null) {
            return createProjectCountry(projectCountryDTO);
        }
        ProjectCountryDTO result = projectCountryService.save(projectCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectCountry", projectCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectCountrys -> get all the projectCountrys.
     */
    @RequestMapping(value = "/projectCountrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProjectCountryDTO> getAllProjectCountrys() {
        log.debug("REST request to get all ProjectCountrys");
        return projectCountryService.findAll();
            }

    /**
     * GET  /projectCountrys/:id -> get the "id" projectCountry.
     */
    @RequestMapping(value = "/projectCountrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryDTO> getProjectCountry(@PathVariable String id) {
        log.debug("REST request to get ProjectCountry : {}", id);
        ProjectCountryDTO projectCountryDTO = projectCountryService.findOne(id);
        return Optional.ofNullable(projectCountryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectCountrys/:id -> delete the "id" projectCountry.
     */
    @RequestMapping(value = "/projectCountrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectCountry(@PathVariable String id) {
        log.debug("REST request to delete ProjectCountry : {}", id);
        projectCountryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectCountry", id.toString())).build();
    }
}
