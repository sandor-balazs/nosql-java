package com.github.sandor_balazs.nosql_java.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.sandor_balazs.nosql_java.domain.Skill;
import com.github.sandor_balazs.nosql_java.service.SkillService;
import com.github.sandor_balazs.nosql_java.web.rest.util.HeaderUtil;
import com.github.sandor_balazs.nosql_java.web.rest.dto.SkillDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);
        
    @Inject
    private SkillService skillService;
    
    @Inject
    private SkillMapper skillMapper;
    
    /**
     * POST  /skills -> Create a new skill.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skillDTO);
        if (skillDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("skill", "idexists", "A new skill cannot already have an ID")).body(null);
        }
        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills -> Updates an existing skill.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SkillDTO> updateSkill(@RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skillDTO);
        if (skillDTO.getId() == null) {
            return createSkill(skillDTO);
        }
        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skill", skillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills -> get all the skills.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<SkillDTO> getAllSkills() {
        log.debug("REST request to get all Skills");
        return skillService.findAll();
            }

    /**
     * GET  /skills/:id -> get the "id" skill.
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SkillDTO> getSkill(@PathVariable String id) {
        log.debug("REST request to get Skill : {}", id);
        SkillDTO skillDTO = skillService.findOne(id);
        return Optional.ofNullable(skillDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /skills/:id -> delete the "id" skill.
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable String id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skill", id.toString())).build();
    }
}
