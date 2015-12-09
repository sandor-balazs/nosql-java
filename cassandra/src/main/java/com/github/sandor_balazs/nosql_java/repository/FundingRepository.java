package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Funding;

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
 * Cassandra repository for the Funding entity.
 */
@Repository
public class FundingRepository {

    @Inject
    private Session session;

    private Mapper<Funding> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Funding.class);
        findAllStmt = session.prepare("SELECT * FROM funding");
        truncateStmt = session.prepare("TRUNCATE funding");
    }

    public List<Funding> findAll() {
        List<Funding> fundings = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Funding funding = new Funding();
                funding.setId(row.getUUID("id"));
                funding.setName(row.getString("name"));
                return funding;
            }
        ).forEach(fundings::add);
        return fundings;
    }

    public Funding findOne(UUID id) {
        return mapper.get(id);
    }

    public Funding save(Funding funding) {
        if (funding.getId() == null) {
            funding.setId(UUID.randomUUID());
        }
        mapper.save(funding);
        return funding;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
