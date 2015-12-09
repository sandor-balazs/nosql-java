package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Department;

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
 * Cassandra repository for the Department entity.
 */
@Repository
public class DepartmentRepository {

    @Inject
    private Session session;

    private Mapper<Department> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Department.class);
        findAllStmt = session.prepare("SELECT * FROM department");
        truncateStmt = session.prepare("TRUNCATE department");
    }

    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Department department = new Department();
                department.setId(row.getUUID("id"));
                department.setName(row.getString("name"));
                return department;
            }
        ).forEach(departments::add);
        return departments;
    }

    public Department findOne(UUID id) {
        return mapper.get(id);
    }

    public Department save(Department department) {
        if (department.getId() == null) {
            department.setId(UUID.randomUUID());
        }
        mapper.save(department);
        return department;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
