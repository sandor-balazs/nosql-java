package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Employment;

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
 * Cassandra repository for the Employment entity.
 */
@Repository
public class EmploymentRepository {

    @Inject
    private Session session;

    private Mapper<Employment> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Employment.class);
        findAllStmt = session.prepare("SELECT * FROM employment");
        truncateStmt = session.prepare("TRUNCATE employment");
    }

    public List<Employment> findAll() {
        List<Employment> employments = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Employment employment = new Employment();
                employment.setId(row.getUUID("id"));
                employment.setFte(row.getFloat("fte"));
                employment.setStartDate(row.getDate("startDate"));
                employment.setEndDate(row.getDate("endDate"));
                return employment;
            }
        ).forEach(employments::add);
        return employments;
    }

    public Employment findOne(UUID id) {
        return mapper.get(id);
    }

    public Employment save(Employment employment) {
        if (employment.getId() == null) {
            employment.setId(UUID.randomUUID());
        }
        mapper.save(employment);
        return employment;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
