package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Funding;
import com.github.sandor_balazs.nosql_java.service.FundingService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.FundingDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.FundingMapper;
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
 * REST controller for managing Funding.
 */
@RestController
@RequestMapping("/api")
public class FundingResource {

    private final Logger log = LoggerFactory.getLogger(FundingResource.class);
        
    @Inject
    private FundingService fundingService;
    
    @Inject
    private FundingMapper fundingMapper;
    
    /**
     * POST  /fundings -> Create a new funding.
     */
    @RequestMapping(value = "/fundings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FundingDTO> createFunding(@RequestBody FundingDTO fundingDTO) throws URISyntaxException {
        log.debug("REST request to save Funding : {}", fundingDTO);
        if (fundingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("funding", "idexists", "A new funding cannot already have an ID")).body(null);
        }
        FundingDTO result = fundingService.save(fundingDTO);
        return ResponseEntity.created(new URI("/api/fundings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("funding", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fundings -> Updates an existing funding.
     */
    @RequestMapping(value = "/fundings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FundingDTO> updateFunding(@RequestBody FundingDTO fundingDTO) throws URISyntaxException {
        log.debug("REST request to update Funding : {}", fundingDTO);
        if (fundingDTO.getId() == null) {
            return createFunding(fundingDTO);
        }
        FundingDTO result = fundingService.save(fundingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("funding", fundingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fundings -> get all the fundings.
     */
    @RequestMapping(value = "/fundings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<FundingDTO> getAllFundings() {
        log.debug("REST request to get all Fundings");
        return fundingService.findAll();
            }

    /**
     * GET  /fundings/:id -> get the "id" funding.
     */
    @RequestMapping(value = "/fundings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FundingDTO> getFunding(@PathVariable String id) {
        log.debug("REST request to get Funding : {}", id);
        FundingDTO fundingDTO = fundingService.findOne(id);
        return Optional.ofNullable(fundingDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fundings/:id -> delete the "id" funding.
     */
    @RequestMapping(value = "/fundings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFunding(@PathVariable String id) {
        log.debug("REST request to delete Funding : {}", id);
        fundingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("funding", id.toString())).build();
    }
}
