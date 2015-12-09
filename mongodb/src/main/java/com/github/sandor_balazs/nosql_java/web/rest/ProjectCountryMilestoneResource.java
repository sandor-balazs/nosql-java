package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;
import com.github.sandor_balazs.nosql_java.service.ProjectCountryMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMilestoneMapper;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing ProjectCountryMilestone.
 */
@RestController
@RequestMapping("/api")
public class ProjectCountryMilestoneResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCountryMilestoneResource.class);
        
    @Inject
    private ProjectCountryMilestoneService projectCountryMilestoneService;
    
    @Inject
    private ProjectCountryMilestoneMapper projectCountryMilestoneMapper;
    
    /**
     * POST  /projectCountryMilestones -> Create a new projectCountryMilestone.
     */
    @RequestMapping(value = "/projectCountryMilestones",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryMilestoneDTO> createProjectCountryMilestone(@RequestBody ProjectCountryMilestoneDTO projectCountryMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectCountryMilestone : {}", projectCountryMilestoneDTO);
        if (projectCountryMilestoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectCountryMilestone", "idexists", "A new projectCountryMilestone cannot already have an ID")).body(null);
        }
        ProjectCountryMilestoneDTO result = projectCountryMilestoneService.save(projectCountryMilestoneDTO);
        return ResponseEntity.created(new URI("/api/projectCountryMilestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectCountryMilestone", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectCountryMilestones -> Updates an existing projectCountryMilestone.
     */
    @RequestMapping(value = "/projectCountryMilestones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryMilestoneDTO> updateProjectCountryMilestone(@RequestBody ProjectCountryMilestoneDTO projectCountryMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectCountryMilestone : {}", projectCountryMilestoneDTO);
        if (projectCountryMilestoneDTO.getId() == null) {
            return createProjectCountryMilestone(projectCountryMilestoneDTO);
        }
        ProjectCountryMilestoneDTO result = projectCountryMilestoneService.save(projectCountryMilestoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectCountryMilestone", projectCountryMilestoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectCountryMilestones -> get all the projectCountryMilestones.
     */
    @RequestMapping(value = "/projectCountryMilestones",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProjectCountryMilestoneDTO> getAllProjectCountryMilestones() {
        log.debug("REST request to get all ProjectCountryMilestones");
        return projectCountryMilestoneService.findAll();
            }

    /**
     * GET  /projectCountryMilestones/:id -> get the "id" projectCountryMilestone.
     */
    @RequestMapping(value = "/projectCountryMilestones/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectCountryMilestoneDTO> getProjectCountryMilestone(@PathVariable String id) {
        log.debug("REST request to get ProjectCountryMilestone : {}", id);
        ProjectCountryMilestoneDTO projectCountryMilestoneDTO = projectCountryMilestoneService.findOne(id);
        return Optional.ofNullable(projectCountryMilestoneDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectCountryMilestones/:id -> delete the "id" projectCountryMilestone.
     */
    @RequestMapping(value = "/projectCountryMilestones/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectCountryMilestone(@PathVariable String id) {
        log.debug("REST request to delete ProjectCountryMilestone : {}", id);
        projectCountryMilestoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectCountryMilestone", id.toString())).build();
    }
}
