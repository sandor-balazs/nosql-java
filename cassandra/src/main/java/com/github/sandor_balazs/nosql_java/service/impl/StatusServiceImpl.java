package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.StatusService;
import com.github.sandor_balazs.nosql_java.domain.Status;
import com.github.sandor_balazs.nosql_java.repository.StatusRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.StatusDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Status.
 */
@Service
public class StatusServiceImpl implements StatusService{

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);
    
    @Inject
    private StatusRepository statusRepository;
    
    @Inject
    private StatusMapper statusMapper;
    
    /**
     * Save a status.
     * @return the persisted entity
     */
    public StatusDTO save(StatusDTO statusDTO) {
        log.debug("Request to save Status : {}", statusDTO);
        Status status = statusMapper.statusDTOToStatus(statusDTO);
        status = statusRepository.save(status);
        StatusDTO result = statusMapper.statusToStatusDTO(status);
        return result;
    }

    /**
     *  get all the statuss.
     *  @return the list of entities
     */
    public List<StatusDTO> findAll() {
        log.debug("Request to get all Statuss");
        List<StatusDTO> result = statusRepository.findAll().stream()
            .map(statusMapper::statusToStatusDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one status by id.
     *  @return the entity
     */
    public StatusDTO findOne(String id) {
        log.debug("Request to get Status : {}", id);
        Status status = statusRepository.findOne(UUID.fromString(id));
        StatusDTO statusDTO = statusMapper.statusToStatusDTO(status);
        return statusDTO;
    }

    /**
     *  delete the  status by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.delete(UUID.fromString(id));
    }
}
