package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.EmploymentService;
import com.github.sandor_balazs.nosql_java.domain.Employment;
import com.github.sandor_balazs.nosql_java.repository.EmploymentRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.EmploymentMapper;
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
 * Service Implementation for managing Employment.
 */
@Service
public class EmploymentServiceImpl implements EmploymentService{

    private final Logger log = LoggerFactory.getLogger(EmploymentServiceImpl.class);
    
    @Inject
    private EmploymentRepository employmentRepository;
    
    @Inject
    private EmploymentMapper employmentMapper;
    
    /**
     * Save a employment.
     * @return the persisted entity
     */
    public EmploymentDTO save(EmploymentDTO employmentDTO) {
        log.debug("Request to save Employment : {}", employmentDTO);
        Employment employment = employmentMapper.employmentDTOToEmployment(employmentDTO);
        employment = employmentRepository.save(employment);
        EmploymentDTO result = employmentMapper.employmentToEmploymentDTO(employment);
        return result;
    }

    /**
     *  get all the employments.
     *  @return the list of entities
     */
    public List<EmploymentDTO> findAll() {
        log.debug("Request to get all Employments");
        List<EmploymentDTO> result = employmentRepository.findAll().stream()
            .map(employmentMapper::employmentToEmploymentDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one employment by id.
     *  @return the entity
     */
    public EmploymentDTO findOne(String id) {
        log.debug("Request to get Employment : {}", id);
        Employment employment = employmentRepository.findOne(UUID.fromString(id));
        EmploymentDTO employmentDTO = employmentMapper.employmentToEmploymentDTO(employment);
        return employmentDTO;
    }

    /**
     *  delete the  employment by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Employment : {}", id);
        employmentRepository.delete(UUID.fromString(id));
    }
}
