package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Employment;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Employment entity.
 */
public interface EmploymentRepository extends MongoRepository<Employment,String> {

}
