package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;

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
 * Cassandra repository for the ProjectMilestone entity.
 */
@Repository
public class ProjectMilestoneRepository {

    @Inject
    private Session session;

    private Mapper<ProjectMilestone> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(ProjectMilestone.class);
        findAllStmt = session.prepare("SELECT * FROM projectMilestone");
        truncateStmt = session.prepare("TRUNCATE projectMilestone");
    }

    public List<ProjectMilestone> findAll() {
        List<ProjectMilestone> projectMilestones = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                ProjectMilestone projectMilestone = new ProjectMilestone();
                projectMilestone.setId(row.getUUID("id"));
                projectMilestone.setPlanned(row.getDate("planned"));
                projectMilestone.setActual(row.getDate("actual"));
                return projectMilestone;
            }
        ).forEach(projectMilestones::add);
        return projectMilestones;
    }

    public ProjectMilestone findOne(UUID id) {
        return mapper.get(id);
    }

    public ProjectMilestone save(ProjectMilestone projectMilestone) {
        if (projectMilestone.getId() == null) {
            projectMilestone.setId(UUID.randomUUID());
        }
        mapper.save(projectMilestone);
        return projectMilestone;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
