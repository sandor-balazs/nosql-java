package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Allocation;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Allocation entity.
 */
public interface AllocationRepository extends MongoRepository<Allocation,String> {

}
