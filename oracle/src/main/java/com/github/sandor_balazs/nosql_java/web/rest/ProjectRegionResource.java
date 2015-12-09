package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;
import com.github.sandor_balazs.nosql_java.service.ProjectRegionService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMapper;
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
 * REST controller for managing ProjectRegion.
 */
@RestController
@RequestMapping("/api")
public class ProjectRegionResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRegionResource.class);
        
    @Inject
    private ProjectRegionService projectRegionService;
    
    @Inject
    private ProjectRegionMapper projectRegionMapper;
    
    /**
     * POST  /projectRegions -> Create a new projectRegion.
     */
    @RequestMapping(value = "/projectRegions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionDTO> createProjectRegion(@RequestBody ProjectRegionDTO projectRegionDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectRegion : {}", projectRegionDTO);
        if (projectRegionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projectRegion", "idexists", "A new projectRegion cannot already have an ID")).body(null);
        }
        ProjectRegionDTO result = projectRegionService.save(projectRegionDTO);
        return ResponseEntity.created(new URI("/api/projectRegions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projectRegion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectRegions -> Updates an existing projectRegion.
     */
    @RequestMapping(value = "/projectRegions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionDTO> updateProjectRegion(@RequestBody ProjectRegionDTO projectRegionDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectRegion : {}", projectRegionDTO);
        if (projectRegionDTO.getId() == null) {
            return createProjectRegion(projectRegionDTO);
        }
        ProjectRegionDTO result = projectRegionService.save(projectRegionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projectRegion", projectRegionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectRegions -> get all the projectRegions.
     */
    @RequestMapping(value = "/projectRegions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<ProjectRegionDTO> getAllProjectRegions() {
        log.debug("REST request to get all ProjectRegions");
        return projectRegionService.findAll();
            }

    /**
     * GET  /projectRegions/:id -> get the "id" projectRegion.
     */
    @RequestMapping(value = "/projectRegions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProjectRegionDTO> getProjectRegion(@PathVariable Long id) {
        log.debug("REST request to get ProjectRegion : {}", id);
        ProjectRegionDTO projectRegionDTO = projectRegionService.findOne(id);
        return Optional.ofNullable(projectRegionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projectRegions/:id -> delete the "id" projectRegion.
     */
    @RequestMapping(value = "/projectRegions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjectRegion(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRegion : {}", id);
        projectRegionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projectRegion", id.toString())).build();
    }
}
