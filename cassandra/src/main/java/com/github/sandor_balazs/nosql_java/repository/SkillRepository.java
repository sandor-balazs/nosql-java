package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Skill;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Skill entity.
 */
@Repository
public class SkillRepository {

    @Inject
    private Session session;

    private Mapper<Skill> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Skill.class);
        findAllStmt = session.prepare("SELECT * FROM skill");
        truncateStmt = session.prepare("TRUNCATE skill");
    }

    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Skill skill = new Skill();
                skill.setId(row.getUUID("id"));
                skill.setName(row.getString("name"));
                return skill;
            }
        ).forEach(skills::add);
        return skills;
    }

    public Skill findOne(UUID id) {
        return mapper.get(id);
    }

    public Skill save(Skill skill) {
        if (skill.getId() == null) {
            skill.setId(UUID.randomUUID());
        }
        mapper.save(skill);
        return skill;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
