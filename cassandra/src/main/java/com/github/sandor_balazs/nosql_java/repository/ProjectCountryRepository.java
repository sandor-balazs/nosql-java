package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;

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
 * Cassandra repository for the ProjectCountry entity.
 */
@Repository
public class ProjectCountryRepository {

    @Inject
    private Session session;

    private Mapper<ProjectCountry> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(ProjectCountry.class);
        findAllStmt = session.prepare("SELECT * FROM projectCountry");
        truncateStmt = session.prepare("TRUNCATE projectCountry");
    }

    public List<ProjectCountry> findAll() {
        List<ProjectCountry> projectCountrys = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                ProjectCountry projectCountry = new ProjectCountry();
                projectCountry.setId(row.getUUID("id"));
                return projectCountry;
            }
        ).forEach(projectCountrys::add);
        return projectCountrys;
    }

    public ProjectCountry findOne(UUID id) {
        return mapper.get(id);
    }

    public ProjectCountry save(ProjectCountry projectCountry) {
        if (projectCountry.getId() == null) {
            projectCountry.setId(UUID.randomUUID());
        }
        mapper.save(projectCountry);
        return projectCountry;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
