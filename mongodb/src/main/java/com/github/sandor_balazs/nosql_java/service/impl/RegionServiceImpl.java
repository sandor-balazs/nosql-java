package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.RegionService;
import com.github.sandor_balazs.nosql_java.domain.Region;
import com.github.sandor_balazs.nosql_java.repository.RegionRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.RegionDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.RegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Region.
 */
@Service
public class RegionServiceImpl implements RegionService{

    private final Logger log = LoggerFactory.getLogger(RegionServiceImpl.class);
    
    @Inject
    private RegionRepository regionRepository;
    
    @Inject
    private RegionMapper regionMapper;
    
    /**
     * Save a region.
     * @return the persisted entity
     */
    public RegionDTO save(RegionDTO regionDTO) {
        log.debug("Request to save Region : {}", regionDTO);
        Region region = regionMapper.regionDTOToRegion(regionDTO);
        region = regionRepository.save(region);
        RegionDTO result = regionMapper.regionToRegionDTO(region);
        return result;
    }

    /**
     *  get all the regions.
     *  @return the list of entities
     */
    public List<RegionDTO> findAll() {
        log.debug("Request to get all Regions");
        List<RegionDTO> result = regionRepository.findAll().stream()
            .map(regionMapper::regionToRegionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one region by id.
     *  @return the entity
     */
    public RegionDTO findOne(String id) {
        log.debug("Request to get Region : {}", id);
        Region region = regionRepository.findOne(id);
        RegionDTO regionDTO = regionMapper.regionToRegionDTO(region);
        return regionDTO;
    }

    /**
     *  delete the  region by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Region : {}", id);
        regionRepository.delete(id);
    }
}
