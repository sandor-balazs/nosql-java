package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Milestone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Milestone entity.
 */
public interface MilestoneRepository extends JpaRepository<Milestone,Long> {

}
