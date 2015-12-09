package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectCountryMilestone entity.
 */
public interface ProjectCountryMilestoneRepository extends JpaRepository<ProjectCountryMilestone,Long> {

}
