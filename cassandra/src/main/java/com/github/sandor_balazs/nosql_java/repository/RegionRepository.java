package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Region;

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
 * Cassandra repository for the Region entity.
 */
@Repository
public class RegionRepository {

    @Inject
    private Session session;

    private Mapper<Region> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Region.class);
        findAllStmt = session.prepare("SELECT * FROM region");
        truncateStmt = session.prepare("TRUNCATE region");
    }

    public List<Region> findAll() {
        List<Region> regions = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Region region = new Region();
                region.setId(row.getUUID("id"));
                region.setCode(row.getString("code"));
                region.setName(row.getString("name"));
                return region;
            }
        ).forEach(regions::add);
        return regions;
    }

    public Region findOne(UUID id) {
        return mapper.get(id);
    }

    public Region save(Region region) {
        if (region.getId() == null) {
            region.setId(UUID.randomUUID());
        }
        mapper.save(region);
        return region;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
