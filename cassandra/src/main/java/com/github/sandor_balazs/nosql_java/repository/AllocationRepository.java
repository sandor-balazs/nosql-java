package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Allocation;

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
 * Cassandra repository for the Allocation entity.
 */
@Repository
public class AllocationRepository {

    @Inject
    private Session session;

    private Mapper<Allocation> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Allocation.class);
        findAllStmt = session.prepare("SELECT * FROM allocation");
        truncateStmt = session.prepare("TRUNCATE allocation");
    }

    public List<Allocation> findAll() {
        List<Allocation> allocations = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Allocation allocation = new Allocation();
                allocation.setId(row.getUUID("id"));
                allocation.setFte(row.getFloat("fte"));
                allocation.setYear(row.getInt("year"));
                allocation.setMonth(row.getInt("month"));
                return allocation;
            }
        ).forEach(allocations::add);
        return allocations;
    }

    public Allocation findOne(UUID id) {
        return mapper.get(id);
    }

    public Allocation save(Allocation allocation) {
        if (allocation.getId() == null) {
            allocation.setId(UUID.randomUUID());
        }
        mapper.save(allocation);
        return allocation;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
