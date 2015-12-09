package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Milestone;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Milestone entity.
 */
public interface MilestoneRepository extends MongoRepository<Milestone,String> {

}
