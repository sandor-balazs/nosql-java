package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Country;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Country entity.
 */
public interface CountryRepository extends MongoRepository<Country,String> {

}
