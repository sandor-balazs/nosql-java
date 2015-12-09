package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Milestone;
import com.github.sandor_balazs.nosql_java.service.MilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.MilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.MilestoneMapper;
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
 * REST controller for managing Milestone.
 */
@RestController
@RequestMapping("/api")
public class MilestoneResource {

    private final Logger log = LoggerFactory.getLogger(MilestoneResource.class);
        
    @Inject
    private MilestoneService milestoneService;
    
    @Inject
    private MilestoneMapper milestoneMapper;
    
    /**
     * POST  /milestones -> Create a new milestone.
     */
    @RequestMapping(value = "/milestones",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MilestoneDTO> createMilestone(@RequestBody MilestoneDTO milestoneDTO) throws URISyntaxException {
        log.debug("REST request to save Milestone : {}", milestoneDTO);
        if (milestoneDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("milestone", "idexists", "A new milestone cannot already have an ID")).body(null);
        }
        MilestoneDTO result = milestoneService.save(milestoneDTO);
        return ResponseEntity.created(new URI("/api/milestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("milestone", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /milestones -> Updates an existing milestone.
     */
    @RequestMapping(value = "/milestones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MilestoneDTO> updateMilestone(@RequestBody MilestoneDTO milestoneDTO) throws URISyntaxException {
        log.debug("REST request to update Milestone : {}", milestoneDTO);
        if (milestoneDTO.getId() == null) {
            return createMilestone(milestoneDTO);
        }
        MilestoneDTO result = milestoneService.save(milestoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("milestone", milestoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /milestones -> get all the milestones.
     */
    @RequestMapping(value = "/milestones",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<MilestoneDTO> getAllMilestones() {
        log.debug("REST request to get all Milestones");
        return milestoneService.findAll();
            }

    /**
     * GET  /milestones/:id -> get the "id" milestone.
     */
    @RequestMapping(value = "/milestones/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MilestoneDTO> getMilestone(@PathVariable Long id) {
        log.debug("REST request to get Milestone : {}", id);
        MilestoneDTO milestoneDTO = milestoneService.findOne(id);
        return Optional.ofNullable(milestoneDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /milestones/:id -> delete the "id" milestone.
     */
    @RequestMapping(value = "/milestones/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        log.debug("REST request to delete Milestone : {}", id);
        milestoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("milestone", id.toString())).build();
    }
}
