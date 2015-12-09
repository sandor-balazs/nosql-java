package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectCountry entity.
 */
public interface ProjectCountryRepository extends MongoRepository<ProjectCountry,String> {

}
