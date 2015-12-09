package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectRegion entity.
 */
public interface ProjectRegionRepository extends JpaRepository<ProjectRegion,Long> {

}
