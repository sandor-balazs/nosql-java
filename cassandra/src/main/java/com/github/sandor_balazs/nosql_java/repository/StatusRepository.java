package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Status;

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
 * Cassandra repository for the Status entity.
 */
@Repository
public class StatusRepository {

    @Inject
    private Session session;

    private Mapper<Status> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Status.class);
        findAllStmt = session.prepare("SELECT * FROM status");
        truncateStmt = session.prepare("TRUNCATE status");
    }

    public List<Status> findAll() {
        List<Status> statuss = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Status status = new Status();
                status.setId(row.getUUID("id"));
                status.setName(row.getString("name"));
                status.setAppOrder(row.getInt("appOrder"));
                return status;
            }
        ).forEach(statuss::add);
        return statuss;
    }

    public Status findOne(UUID id) {
        return mapper.get(id);
    }

    public Status save(Status status) {
        if (status.getId() == null) {
            status.setId(UUID.randomUUID());
        }
        mapper.save(status);
        return status;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
