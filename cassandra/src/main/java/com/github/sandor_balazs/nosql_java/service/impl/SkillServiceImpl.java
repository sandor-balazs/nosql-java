package com.github.sandor_balazs.nosql_java.service.impl;

import com.github.sandor_balazs.nosql_java.service.SkillService;
import com.github.sandor_balazs.nosql_java.domain.Skill;
import com.github.sandor_balazs.nosql_java.repository.SkillRepository;
import com.github.sandor_balazs.nosql_java.web.rest.dto.SkillDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.SkillMapper;
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
 * Service Implementation for managing Skill.
 */
@Service
public class SkillServiceImpl implements SkillService{

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);
    
    @Inject
    private SkillRepository skillRepository;
    
    @Inject
    private SkillMapper skillMapper;
    
    /**
     * Save a skill.
     * @return the persisted entity
     */
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.skillDTOToSkill(skillDTO);
        skill = skillRepository.save(skill);
        SkillDTO result = skillMapper.skillToSkillDTO(skill);
        return result;
    }

    /**
     *  get all the skills.
     *  @return the list of entities
     */
    public List<SkillDTO> findAll() {
        log.debug("Request to get all Skills");
        List<SkillDTO> result = skillRepository.findAll().stream()
            .map(skillMapper::skillToSkillDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one skill by id.
     *  @return the entity
     */
    public SkillDTO findOne(String id) {
        log.debug("Request to get Skill : {}", id);
        Skill skill = skillRepository.findOne(UUID.fromString(id));
        SkillDTO skillDTO = skillMapper.skillToSkillDTO(skill);
        return skillDTO;
    }

    /**
     *  delete the  skill by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.delete(UUID.fromString(id));
    }
}
