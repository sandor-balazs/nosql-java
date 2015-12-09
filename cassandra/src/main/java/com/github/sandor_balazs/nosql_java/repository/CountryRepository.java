package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Country;

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
 * Cassandra repository for the Country entity.
 */
@Repository
public class CountryRepository {

    @Inject
    private Session session;

    private Mapper<Country> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Country.class);
        findAllStmt = session.prepare("SELECT * FROM country");
        truncateStmt = session.prepare("TRUNCATE country");
    }

    public List<Country> findAll() {
        List<Country> countrys = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Country country = new Country();
                country.setId(row.getUUID("id"));
                country.setCode(row.getString("code"));
                country.setName(row.getString("name"));
                return country;
            }
        ).forEach(countrys::add);
        return countrys;
    }

    public Country findOne(UUID id) {
        return mapper.get(id);
    }

    public Country save(Country country) {
        if (country.getId() == null) {
            country.setId(UUID.randomUUID());
        }
        mapper.save(country);
        return country;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
