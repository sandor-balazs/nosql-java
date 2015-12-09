package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Employment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employment entity.
 */
public interface EmploymentRepository extends JpaRepository<Employment,Long> {

}
