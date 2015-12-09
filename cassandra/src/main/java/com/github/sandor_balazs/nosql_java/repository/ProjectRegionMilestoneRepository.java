package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;

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
 * Cassandra repository for the ProjectRegionMilestone entity.
 */
@Repository
public class ProjectRegionMilestoneRepository {

    @Inject
    private Session session;

    private Mapper<ProjectRegionMilestone> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(ProjectRegionMilestone.class);
        findAllStmt = session.prepare("SELECT * FROM projectRegionMilestone");
        truncateStmt = session.prepare("TRUNCATE projectRegionMilestone");
    }

    public List<ProjectRegionMilestone> findAll() {
        List<ProjectRegionMilestone> projectRegionMilestones = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                ProjectRegionMilestone projectRegionMilestone = new ProjectRegionMilestone();
                projectRegionMilestone.setId(row.getUUID("id"));
                projectRegionMilestone.setPlanned(row.getDate("planned"));
                projectRegionMilestone.setActual(row.getDate("actual"));
                return projectRegionMilestone;
            }
        ).forEach(projectRegionMilestones::add);
        return projectRegionMilestones;
    }

    public ProjectRegionMilestone findOne(UUID id) {
        return mapper.get(id);
    }

    public ProjectRegionMilestone save(ProjectRegionMilestone projectRegionMilestone) {
        if (projectRegionMilestone.getId() == null) {
            projectRegionMilestone.setId(UUID.randomUUID());
        }
        mapper.save(projectRegionMilestone);
        return projectRegionMilestone;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
