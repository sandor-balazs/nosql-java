package com.github.sandor_balazs.nosql_java.repository;

import com.github.sandor_balazs.nosql_java.domain.Location;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
public interface LocationRepository extends JpaRepository<Location,Long> {

}
