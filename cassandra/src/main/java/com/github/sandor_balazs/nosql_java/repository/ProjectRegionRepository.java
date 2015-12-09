package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;

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
 * Cassandra repository for the ProjectRegion entity.
 */
@Repository
public class ProjectRegionRepository {

    @Inject
    private Session session;

    private Mapper<ProjectRegion> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(ProjectRegion.class);
        findAllStmt = session.prepare("SELECT * FROM projectRegion");
        truncateStmt = session.prepare("TRUNCATE projectRegion");
    }

    public List<ProjectRegion> findAll() {
        List<ProjectRegion> projectRegions = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                ProjectRegion projectRegion = new ProjectRegion();
                projectRegion.setId(row.getUUID("id"));
                return projectRegion;
            }
        ).forEach(projectRegions::add);
        return projectRegions;
    }

    public ProjectRegion findOne(UUID id) {
        return mapper.get(id);
    }

    public ProjectRegion save(ProjectRegion projectRegion) {
        if (projectRegion.getId() == null) {
            projectRegion.setId(UUID.randomUUID());
        }
        mapper.save(projectRegion);
        return projectRegion;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
