package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.DepartmentService;
import com.github.sandor_balazs.nosql_java.domain.Department;
import com.github.sandor_balazs.nosql_java.repository.DepartmentRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.DepartmentDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.DepartmentMapper;
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
 * Service Implementation for managing Department.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    
    @Inject
    private DepartmentRepository departmentRepository;
    
    @Inject
    private DepartmentMapper departmentMapper;
    
    /**
     * Save a department.
     * @return the persisted entity
     */
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.departmentDTOToDepartment(departmentDTO);
        department = departmentRepository.save(department);
        DepartmentDTO result = departmentMapper.departmentToDepartmentDTO(department);
        return result;
    }

    /**
     *  get all the departments.
     *  @return the list of entities
     */
    public List<DepartmentDTO> findAll() {
        log.debug("Request to get all Departments");
        List<DepartmentDTO> result = departmentRepository.findAll().stream()
            .map(departmentMapper::departmentToDepartmentDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one department by id.
     *  @return the entity
     */
    public DepartmentDTO findOne(String id) {
        log.debug("Request to get Department : {}", id);
        Department department = departmentRepository.findOne(UUID.fromString(id));
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);
        return departmentDTO;
    }

    /**
     *  delete the  department by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.delete(UUID.fromString(id));
    }
}
