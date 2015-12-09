package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Milestone;

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
 * Cassandra repository for the Milestone entity.
 */
@Repository
public class MilestoneRepository {

    @Inject
    private Session session;

    private Mapper<Milestone> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Milestone.class);
        findAllStmt = session.prepare("SELECT * FROM milestone");
        truncateStmt = session.prepare("TRUNCATE milestone");
    }

    public List<Milestone> findAll() {
        List<Milestone> milestones = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Milestone milestone = new Milestone();
                milestone.setId(row.getUUID("id"));
                milestone.setName(row.getString("name"));
                milestone.setAppOrder(row.getInt("appOrder"));
                return milestone;
            }
        ).forEach(milestones::add);
        return milestones;
    }

    public Milestone findOne(UUID id) {
        return mapper.get(id);
    }

    public Milestone save(Milestone milestone) {
        if (milestone.getId() == null) {
            milestone.setId(UUID.randomUUID());
        }
        mapper.save(milestone);
        return milestone;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
