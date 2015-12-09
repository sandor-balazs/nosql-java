package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Country;
import com.github.sandor_balazs.nosql_java.web.rest.dto.CountryDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Country.
 */
public interface CountryService {

    /**
     * Save a country.
     * @return the persisted entity
     */
    public CountryDTO save(CountryDTO countryDTO);

    /**
     *  get all the countrys.
     *  @return the list of entities
     */
    public List<CountryDTO> findAll();

    /**
     *  get the "id" country.
     *  @return the entity
     */
    public CountryDTO findOne(String id);

    /**
     *  delete the "id" country.
     */
    public void delete(String id);
}
