package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Allocation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Allocation entity.
 */
public interface AllocationRepository extends JpaRepository<Allocation,Long> {

}
