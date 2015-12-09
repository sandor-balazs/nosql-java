package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.LocationService;
import com.github.sandor_balazs.nosql_java.domain.Location;
import com.github.sandor_balazs.nosql_java.repository.LocationRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.LocationDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Location.
 */
@Service
public class LocationServiceImpl implements LocationService{

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);
    
    @Inject
    private LocationRepository locationRepository;
    
    @Inject
    private LocationMapper locationMapper;
    
    /**
     * Save a location.
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return result;
    }

    /**
     *  get all the locations.
     *  @return the list of entities
     */
    public List<LocationDTO> findAll() {
        log.debug("Request to get all Locations");
        List<LocationDTO> result = locationRepository.findAll().stream()
            .map(locationMapper::locationToLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one location by id.
     *  @return the entity
     */
    public LocationDTO findOne(String id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return locationDTO;
    }

    /**
     *  delete the  location by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
