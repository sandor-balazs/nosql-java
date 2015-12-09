package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Country;
import com.github.sandor_balazs.nosql_java.service.CountryService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.CountryDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.CountryMapper;
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
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);
        
    @Inject
    private CountryService countryService;
    
    @Inject
    private CountryMapper countryMapper;
    
    /**
     * POST  /countrys -> Create a new country.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to save Country : {}", countryDTO);
        if (countryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("country", "idexists", "A new country cannot already have an ID")).body(null);
        }
        CountryDTO result = countryService.save(countryDTO);
        return ResponseEntity.created(new URI("/api/countrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("country", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countrys -> Updates an existing country.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryDTO> updateCountry(@RequestBody CountryDTO countryDTO) throws URISyntaxException {
        log.debug("REST request to update Country : {}", countryDTO);
        if (countryDTO.getId() == null) {
            return createCountry(countryDTO);
        }
        CountryDTO result = countryService.save(countryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("country", countryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countrys -> get all the countrys.
     */
    @RequestMapping(value = "/countrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CountryDTO> getAllCountrys() {
        log.debug("REST request to get all Countrys");
        return countryService.findAll();
            }

    /**
     * GET  /countrys/:id -> get the "id" country.
     */
    @RequestMapping(value = "/countrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryDTO> getCountry(@PathVariable String id) {
        log.debug("REST request to get Country : {}", id);
        CountryDTO countryDTO = countryService.findOne(id);
        return Optional.ofNullable(countryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /countrys/:id -> delete the "id" country.
     */
    @RequestMapping(value = "/countrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        log.debug("REST request to delete Country : {}", id);
        countryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("country", id.toString())).build();
    }
}
