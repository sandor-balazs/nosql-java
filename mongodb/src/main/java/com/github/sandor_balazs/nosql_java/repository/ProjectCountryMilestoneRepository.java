package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProjectCountryMilestone entity.
 */
public interface ProjectCountryMilestoneRepository extends MongoRepository<ProjectCountryMilestone,String> {

}
