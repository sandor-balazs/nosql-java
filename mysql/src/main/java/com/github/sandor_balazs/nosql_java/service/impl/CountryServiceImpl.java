package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.CountryService;
import com.github.sandor_balazs.nosql_java.domain.Country;
import com.github.sandor_balazs.nosql_java.repository.CountryRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.CountryDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.CountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    private final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);
    
    @Inject
    private CountryRepository countryRepository;
    
    @Inject
    private CountryMapper countryMapper;
    
    /**
     * Save a country.
     * @return the persisted entity
     */
    public CountryDTO save(CountryDTO countryDTO) {
        log.debug("Request to save Country : {}", countryDTO);
        Country country = countryMapper.countryDTOToCountry(countryDTO);
        country = countryRepository.save(country);
        CountryDTO result = countryMapper.countryToCountryDTO(country);
        return result;
    }

    /**
     *  get all the countrys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CountryDTO> findAll() {
        log.debug("Request to get all Countrys");
        List<CountryDTO> result = countryRepository.findAll().stream()
            .map(countryMapper::countryToCountryDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one country by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CountryDTO findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        CountryDTO countryDTO = countryMapper.countryToCountryDTO(country);
        return countryDTO;
    }

    /**
     *  delete the  country by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
