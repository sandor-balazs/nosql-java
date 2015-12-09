package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectMilestone entity.
 */
public interface ProjectMilestoneRepository extends MongoRepository<ProjectMilestone,String> {

}
