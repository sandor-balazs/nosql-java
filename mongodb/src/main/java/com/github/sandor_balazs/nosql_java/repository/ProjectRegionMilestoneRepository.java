package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectRegionMilestone entity.
 */
public interface ProjectRegionMilestoneRepository extends MongoRepository<ProjectRegionMilestone,String> {

}
