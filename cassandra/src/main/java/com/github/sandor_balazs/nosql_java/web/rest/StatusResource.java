package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Status;
import com.github.sandor_balazs.nosql_java.service.StatusService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.StatusDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.StatusMapper;
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
 * REST controller for managing Status.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);
        
    @Inject
    private StatusService statusService;
    
    @Inject
    private StatusMapper statusMapper;
    
    /**
     * POST  /statuss -> Create a new status.
     */
    @RequestMapping(value = "/statuss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to save Status : {}", statusDTO);
        if (statusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("status", "idexists", "A new status cannot already have an ID")).body(null);
        }
        StatusDTO result = statusService.save(statusDTO);
        return ResponseEntity.created(new URI("/api/statuss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("status", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statuss -> Updates an existing status.
     */
    @RequestMapping(value = "/statuss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatusDTO> updateStatus(@RequestBody StatusDTO statusDTO) throws URISyntaxException {
        log.debug("REST request to update Status : {}", statusDTO);
        if (statusDTO.getId() == null) {
            return createStatus(statusDTO);
        }
        StatusDTO result = statusService.save(statusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("status", statusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statuss -> get all the statuss.
     */
    @RequestMapping(value = "/statuss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<StatusDTO> getAllStatuss() {
        log.debug("REST request to get all Statuss");
        return statusService.findAll();
            }

    /**
     * GET  /statuss/:id -> get the "id" status.
     */
    @RequestMapping(value = "/statuss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatusDTO> getStatus(@PathVariable String id) {
        log.debug("REST request to get Status : {}", id);
        StatusDTO statusDTO = statusService.findOne(id);
        return Optional.ofNullable(statusDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /statuss/:id -> delete the "id" status.
     */
    @RequestMapping(value = "/statuss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStatus(@PathVariable String id) {
        log.debug("REST request to delete Status : {}", id);
        statusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("status", id.toString())).build();
    }
}
