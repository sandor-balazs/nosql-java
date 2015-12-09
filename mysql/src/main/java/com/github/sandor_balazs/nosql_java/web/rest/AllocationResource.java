package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Allocation;
import com.github.sandor_balazs.nosql_java.service.AllocationService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.AllocationMapper;
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
 * REST controller for managing Allocation.
 */
@RestController
@RequestMapping("/api")
public class AllocationResource {

    private final Logger log = LoggerFactory.getLogger(AllocationResource.class);
        
    @Inject
    private AllocationService allocationService;
    
    @Inject
    private AllocationMapper allocationMapper;
    
    /**
     * POST  /allocations -> Create a new allocation.
     */
    @RequestMapping(value = "/allocations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllocationDTO> createAllocation(@RequestBody AllocationDTO allocationDTO) throws URISyntaxException {
        log.debug("REST request to save Allocation : {}", allocationDTO);
        if (allocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("allocation", "idexists", "A new allocation cannot already have an ID")).body(null);
        }
        AllocationDTO result = allocationService.save(allocationDTO);
        return ResponseEntity.created(new URI("/api/allocations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("allocation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allocations -> Updates an existing allocation.
     */
    @RequestMapping(value = "/allocations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllocationDTO> updateAllocation(@RequestBody AllocationDTO allocationDTO) throws URISyntaxException {
        log.debug("REST request to update Allocation : {}", allocationDTO);
        if (allocationDTO.getId() == null) {
            return createAllocation(allocationDTO);
        }
        AllocationDTO result = allocationService.save(allocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("allocation", allocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allocations -> get all the allocations.
     */
    @RequestMapping(value = "/allocations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<AllocationDTO> getAllAllocations() {
        log.debug("REST request to get all Allocations");
        return allocationService.findAll();
            }

    /**
     * GET  /allocations/:id -> get the "id" allocation.
     */
    @RequestMapping(value = "/allocations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllocationDTO> getAllocation(@PathVariable Long id) {
        log.debug("REST request to get Allocation : {}", id);
        AllocationDTO allocationDTO = allocationService.findOne(id);
        return Optional.ofNullable(allocationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /allocations/:id -> delete the "id" allocation.
     */
    @RequestMapping(value = "/allocations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        log.debug("REST request to delete Allocation : {}", id);
        allocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("allocation", id.toString())).build();
    }
}
