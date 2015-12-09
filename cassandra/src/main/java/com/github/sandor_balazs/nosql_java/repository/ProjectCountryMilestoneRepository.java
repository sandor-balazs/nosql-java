package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;

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
 * Cassandra repository for the ProjectCountryMilestone entity.
 */
@Repository
public class ProjectCountryMilestoneRepository {

    @Inject
    private Session session;

    private Mapper<ProjectCountryMilestone> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(ProjectCountryMilestone.class);
        findAllStmt = session.prepare("SELECT * FROM projectCountryMilestone");
        truncateStmt = session.prepare("TRUNCATE projectCountryMilestone");
    }

    public List<ProjectCountryMilestone> findAll() {
        List<ProjectCountryMilestone> projectCountryMilestones = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                ProjectCountryMilestone projectCountryMilestone = new ProjectCountryMilestone();
                projectCountryMilestone.setId(row.getUUID("id"));
                projectCountryMilestone.setPlanned(row.getDate("planned"));
                projectCountryMilestone.setActual(row.getDate("actual"));
                return projectCountryMilestone;
            }
        ).forEach(projectCountryMilestones::add);
        return projectCountryMilestones;
    }

    public ProjectCountryMilestone findOne(UUID id) {
        return mapper.get(id);
    }

    public ProjectCountryMilestone save(ProjectCountryMilestone projectCountryMilestone) {
        if (projectCountryMilestone.getId() == null) {
            projectCountryMilestone.setId(UUID.randomUUID());
        }
        mapper.save(projectCountryMilestone);
        return projectCountryMilestone;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
