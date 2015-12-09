package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectRegion entity.
 */
public interface ProjectRegionRepository extends MongoRepository<ProjectRegion,String> {

}
