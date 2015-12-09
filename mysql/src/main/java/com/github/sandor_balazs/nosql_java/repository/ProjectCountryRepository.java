package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProjectCountry entity.
 */
public interface ProjectCountryRepository extends JpaRepository<ProjectCountry,Long> {

}
