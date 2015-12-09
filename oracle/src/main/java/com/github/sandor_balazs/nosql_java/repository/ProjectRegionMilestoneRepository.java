package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectRegionMilestone entity.
 */
public interface ProjectRegionMilestoneRepository extends JpaRepository<ProjectRegionMilestone,Long> {

}
