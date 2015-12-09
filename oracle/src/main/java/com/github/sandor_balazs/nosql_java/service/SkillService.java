package com.github.sandor_balazs.nosql_java.service;

import com.github.sandor_balazs.nosql_java.domain.Skill;
import com.github.sandor_balazs.nosql_java.web.rest.dto.SkillDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Skill.
 */
public interface SkillService {

    /**
     * Save a skill.
     * @return the persisted entity
     */
    public SkillDTO save(SkillDTO skillDTO);

    /**
     *  get all the skills.
     *  @return the list of entities
     */
    public List<SkillDTO> findAll();

    /**
     *  get the "id" skill.
     *  @return the entity
     */
    public SkillDTO findOne(Long id);

    /**
     *  delete the "id" skill.
     */
    public void delete(Long id);
}
