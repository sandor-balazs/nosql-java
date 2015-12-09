package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;
import com.github.sandor_balazs.nosql_java.service.ProjectMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectMilestoneMapper;
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
 * REST controller for managing ProjectMilestone.
 */
@RestController
@RequestMapping("/api")
public class ProjectMilestoneResource {

    private final Logger log = LoggerFactory.getLogger(ProjectMilestoneResource.class);
        
    @Inject
    private ProjectMilestoneService projectMilestoneService;
    
    @Inject
    private ProjectMilestoneMapper projectMilestoneMapper;
    
    /**
     * POST  /projectMilestones -> Create a new projectMilestone.
     */
    @RequestMapping(value = "/projectMilestones",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectMilestoneDTO> createProjectMilestone(@RequestBody ProjectMilestoneDTO projectMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectMilestone : {}", projectMilestoneDTO);
        if (projectMilestoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectMilestone", "idexists", "A new projectMilestone cannot already have an ID")).body(null);
        }
        ProjectMilestoneDTO result = projectMilestoneService.save(projectMilestoneDTO);
        return ResponseEntity.created(new URI("/api/projectMilestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectMilestone", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectMilestones -> Updates an existing projectMilestone.
     */
    @RequestMapping(value = "/projectMilestones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectMilestoneDTO> updateProjectMilestone(@RequestBody ProjectMilestoneDTO projectMilestoneDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectMilestone : {}", projectMilestoneDTO);
        if (projectMilestoneDTO.getId() == null) {
            return createProjectMilestone(projectMilestoneDTO);
        }
        ProjectMilestoneDTO result = projectMilestoneService.save(projectMilestoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectMilestone", projectMilestoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectMilestones -> get all the projectMilestones.
     */
    @RequestMapping(value = "/projectMilestones",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProjectMilestoneDTO> getAllProjectMilestones() {
        log.debug("REST request to get all ProjectMilestones");
        return projectMilestoneService.findAll();
            }

    /**
     * GET  /projectMilestones/:id -> get the "id" projectMilestone.
     */
    @RequestMapping(value = "/projectMilestones/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectMilestoneDTO> getProjectMilestone(@PathVariable String id) {
        log.debug("REST request to get ProjectMilestone : {}", id);
        ProjectMilestoneDTO projectMilestoneDTO = projectMilestoneService.findOne(id);
        return Optional.ofNullable(projectMilestoneDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectMilestones/:id -> delete the "id" projectMilestone.
     */
    @RequestMapping(value = "/projectMilestones/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectMilestone(@PathVariable String id) {
        log.debug("REST request to delete ProjectMilestone : {}", id);
        projectMilestoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectMilestone", id.toString())).build();
    }
}
