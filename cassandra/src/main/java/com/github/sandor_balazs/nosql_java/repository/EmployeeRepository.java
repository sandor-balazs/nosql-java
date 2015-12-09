package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Employee;

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
 * Cassandra repository for the Employee entity.
 */
@Repository
public class EmployeeRepository {

    @Inject
    private Session session;

    private Mapper<Employee> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Employee.class);
        findAllStmt = session.prepare("SELECT * FROM employee");
        truncateStmt = session.prepare("TRUNCATE employee");
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        BoundStatement stmt =  findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Employee employee = new Employee();
                employee.setId(row.getUUID("id"));
                employee.setFirstName(row.getString("firstName"));
                employee.setLastName(row.getString("lastName"));
                employee.setUserId(row.getString("userId"));
                employee.setPhone(row.getString("phone"));
                employee.setEmail(row.getString("email"));
                return employee;
            }
        ).forEach(employees::add);
        return employees;
    }

    public Employee findOne(UUID id) {
        return mapper.get(id);
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(UUID.randomUUID());
        }
        mapper.save(employee);
        return employee;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt =  truncateStmt.bind();
        session.execute(stmt);
    }
}
