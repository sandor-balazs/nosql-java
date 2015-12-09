package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectMilestone entity.
 */
public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone,Long> {

}
