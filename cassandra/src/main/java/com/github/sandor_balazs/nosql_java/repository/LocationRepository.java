package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Location;

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
 * Cassandra repository for the Location entity.
 */
@Repository
public class LocationRepository {

    @Inject
    private Session session;

    private Mapper<Location> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Location.class);
        findAllStmt = session.prepare("SELECT * FROM location");
        truncateStmt = session.prepare("TRUNCATE location");
    }

    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Location location = new Location();
                location.setId(row.getUUID("id"));
                location.setName(row.getString("name"));
                location.setAddress(row.getString("address"));
                return location;
            }
        ).forEach(locations::add);
        return locations;
    }

    public Location findOne(UUID id) {
        return mapper.get(id);
    }

    public Location save(Location location) {
        if (location.getId() == null) {
            location.setId(UUID.randomUUID());
        }
        mapper.save(location);
        return location;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
