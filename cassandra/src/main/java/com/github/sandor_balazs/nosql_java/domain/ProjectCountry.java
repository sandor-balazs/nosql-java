package com.github.sandor_balazs.nosql_java.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ProjectCountry.
 */

@Table(name = "projectCountry")
public class ProjectCountry implements Serializable {

    @PartitionKey
    private UUID id;

    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectCountry projectCountry = (ProjectCountry) o;
        return Objects.equals(id, projectCountry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectCountry{" +
            "id=" + id +
            '}';
    }
}
