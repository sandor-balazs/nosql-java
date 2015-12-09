package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Project;

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
 * Cassandra repository for the Project entity.
 */
@Repository
public class ProjectRepository {

    @Inject
    private Session session;

    private Mapper<Project> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Project.class);
        findAllStmt = session.prepare("SELECT * FROM project");
        truncateStmt = session.prepare("TRUNCATE project");
    }

    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Project project = new Project();
                project.setId(row.getUUID("id"));
                project.setCode(row.getString("code"));
                project.setTitle(row.getString("title"));
                project.setDescription(row.getString("description"));
                return project;
            }
        ).forEach(projects::add);
        return projects;
    }

    public Project findOne(UUID id) {
        return mapper.get(id);
    }

    public Project save(Project project) {
        if (project.getId() == null) {
            project.setId(UUID.randomUUID());
        }
        mapper.save(project);
        return project;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
