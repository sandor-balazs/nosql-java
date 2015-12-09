package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Employment;
import com.github.sandor_balazs.nosql_java.service.EmploymentService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.EmploymentMapper;
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
 * REST controller for managing Employment.
 */
@RestController
@RequestMapping("/api")
public class EmploymentResource {

    private final Logger log = LoggerFactory.getLogger(EmploymentResource.class);
        
    @Inject
    private EmploymentService employmentService;
    
    @Inject
    private EmploymentMapper employmentMapper;
    
    /**
     * POST  /employments -> Create a new employment.
     */
    @RequestMapping(value = "/employments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmploymentDTO> createEmployment(@RequestBody EmploymentDTO employmentDTO) throws URISyntaxException {
        log.debug("REST request to save Employment : {}", employmentDTO);
        if (employmentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("employment", "idexists", "A new employment cannot already have an ID")).body(null);
        }
        EmploymentDTO result = employmentService.save(employmentDTO);
        return ResponseEntity.created(new URI("/api/employments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employments -> Updates an existing employment.
     */
    @RequestMapping(value = "/employments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmploymentDTO> updateEmployment(@RequestBody EmploymentDTO employmentDTO) throws URISyntaxException {
        log.debug("REST request to update Employment : {}", employmentDTO);
        if (employmentDTO.getId() == null) {
            return createEmployment(employmentDTO);
        }
        EmploymentDTO result = employmentService.save(employmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employment", employmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employments -> get all the employments.
     */
    @RequestMapping(value = "/employments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<EmploymentDTO> getAllEmployments() {
        log.debug("REST request to get all Employments");
        return employmentService.findAll();
            }

    /**
     * GET  /employments/:id -> get the "id" employment.
     */
    @RequestMapping(value = "/employments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmploymentDTO> getEmployment(@PathVariable String id) {
        log.debug("REST request to get Employment : {}", id);
        EmploymentDTO employmentDTO = employmentService.findOne(id);
        return Optional.ofNullable(employmentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employments/:id -> delete the "id" employment.
     */
    @RequestMapping(value = "/employments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployment(@PathVariable String id) {
        log.debug("REST request to delete Employment : {}", id);
        employmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employment", id.toString())).build();
    }
}
