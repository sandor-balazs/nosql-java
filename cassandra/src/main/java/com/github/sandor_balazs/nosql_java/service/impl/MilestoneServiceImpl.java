package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.MilestoneService;
import com.github.sandor_balazs.nosql_java.domain.Milestone;
import com.github.sandor_balazs.nosql_java.repository.MilestoneRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.MilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.MilestoneMapper;
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
 * Service Implementation for managing Milestone.
 */
@Service
public class MilestoneServiceImpl implements MilestoneService{

    private final Logger log = LoggerFactory.getLogger(MilestoneServiceImpl.class);
    
    @Inject
    private MilestoneRepository milestoneRepository;
    
    @Inject
    private MilestoneMapper milestoneMapper;
    
    /**
     * Save a milestone.
     * @return the persisted entity
     */
    public MilestoneDTO save(MilestoneDTO milestoneDTO) {
        log.debug("Request to save Milestone : {}", milestoneDTO);
        Milestone milestone = milestoneMapper.milestoneDTOToMilestone(milestoneDTO);
        milestone = milestoneRepository.save(milestone);
        MilestoneDTO result = milestoneMapper.milestoneToMilestoneDTO(milestone);
        return result;
    }

    /**
     *  get all the milestones.
     *  @return the list of entities
     */
    public List<MilestoneDTO> findAll() {
        log.debug("Request to get all Milestones");
        List<MilestoneDTO> result = milestoneRepository.findAll().stream()
            .map(milestoneMapper::milestoneToMilestoneDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one milestone by id.
     *  @return the entity
     */
    public MilestoneDTO findOne(String id) {
        log.debug("Request to get Milestone : {}", id);
        Milestone milestone = milestoneRepository.findOne(UUID.fromString(id));
        MilestoneDTO milestoneDTO = milestoneMapper.milestoneToMilestoneDTO(milestone);
        return milestoneDTO;
    }

    /**
     *  delete the  milestone by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Milestone : {}", id);
        milestoneRepository.delete(UUID.fromString(id));
    }
}
