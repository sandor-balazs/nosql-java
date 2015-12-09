package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;
import com.github.sandor_balazs.nosql_java.service.ProjectRegionMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMilestoneMapper;
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
 * REST controller for managing ProjectRegionMilestone.
 */
@RestController
@RequestMapping("/api")
public class ProjectRegionMilestoneResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRegionMilestoneResource.class);
        
    @Inject
    private ProjectRegionMilestoneService projectRegionMilestoneService;
    
    @Inject
    private ProjectRegionMilestoneMapper projectRegionMilestoneMapper;
    
    /**
     * POST  /projectRegionMilestones -> Create a new projectRegionMilestone.
     */
    @RequestMapping(value = "/projectRegionMilestones",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionMilestoneDTO> createProjectRegionMilestone(@RequestBody ProjectRegionMilestoneDTO projectRegionMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectRegionMilestone : {}", projectRegionMilestoneDTO);
        if (projectRegionMilestoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectRegionMilestone", "idexists", "A new projectRegionMilestone cannot already have an ID")).body(null);
        }
        ProjectRegionMilestoneDTO result = projectRegionMilestoneService.save(projectRegionMilestoneDTO);
        return ResponseEntity.created(new URI("/api/projectRegionMilestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectRegionMilestone", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectRegionMilestones -> Updates an existing projectRegionMilestone.
     */
    @RequestMapping(value = "/projectRegionMilestones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionMilestoneDTO> updateProjectRegionMilestone(@RequestBody ProjectRegionMilestoneDTO projectRegionMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectRegionMilestone : {}", projectRegionMilestoneDTO);
        if (projectRegionMilestoneDTO.getId() == null) {
            return createProjectRegionMilestone(projectRegionMilestoneDTO);
        }
        ProjectRegionMilestoneDTO result = projectRegionMilestoneService.save(projectRegionMilestoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectRegionMilestone", projectRegionMilestoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectRegionMilestones -> get all the projectRegionMilestones.
     */
    @RequestMapping(value = "/projectRegionMilestones",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProjectRegionMilestoneDTO> getAllProjectRegionMilestones() {
        log.debug("REST request to get all ProjectRegionMilestones");
        return projectRegionMilestoneService.findAll();
            }

    /**
     * GET  /projectRegionMilestones/:id -> get the "id" projectRegionMilestone.
     */
    @RequestMapping(value = "/projectRegionMilestones/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionMilestoneDTO> getProjectRegionMilestone(@PathVariable String id) {
        log.debug("REST request to get ProjectRegionMilestone : {}", id);
        ProjectRegionMilestoneDTO projectRegionMilestoneDTO = projectRegionMilestoneService.findOne(id);
        return Optional.ofNullable(projectRegionMilestoneDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectRegionMilestones/:id -> delete the "id" projectRegionMilestone.
     */
    @RequestMapping(value = "/projectRegionMilestones/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectRegionMilestone(@PathVariable String id) {
        log.debug("REST request to delete ProjectRegionMilestone : {}", id);
        projectRegionMilestoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectRegionMilestone", id.toString())).build();
    }
}
