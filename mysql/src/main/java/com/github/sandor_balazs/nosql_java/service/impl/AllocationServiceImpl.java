package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.AllocationService;
import com.github.sandor_balazs.nosql_java.domain.Allocation;
import com.github.sandor_balazs.nosql_java.repository.AllocationRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.AllocationMapper;
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
 * Service Implementation for managing Allocation.
 */
@Service
@Transactional
public class AllocationServiceImpl implements AllocationService{

    private final Logger log = LoggerFactory.getLogger(AllocationServiceImpl.class);
    
    @Inject
    private AllocationRepository allocationRepository;
    
    @Inject
    private AllocationMapper allocationMapper;
    
    /**
     * Save a allocation.
     * @return the persisted entity
     */
    public AllocationDTO save(AllocationDTO allocationDTO) {
        log.debug("Request to save Allocation : {}", allocationDTO);
        Allocation allocation = allocationMapper.allocationDTOToAllocation(allocationDTO);
        allocation = allocationRepository.save(allocation);
        AllocationDTO result = allocationMapper.allocationToAllocationDTO(allocation);
        return result;
    }

    /**
     *  get all the allocations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AllocationDTO> findAll() {
        log.debug("Request to get all Allocations");
        List<AllocationDTO> result = allocationRepository.findAll().stream()
            .map(allocationMapper::allocationToAllocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one allocation by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AllocationDTO findOne(Long id) {
        log.debug("Request to get Allocation : {}", id);
        Allocation allocation = allocationRepository.findOne(id);
        AllocationDTO allocationDTO = allocationMapper.allocationToAllocationDTO(allocation);
        return allocationDTO;
    }

    /**
     *  delete the  allocation by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Allocation : {}", id);
        allocationRepository.delete(id);
    }
}
