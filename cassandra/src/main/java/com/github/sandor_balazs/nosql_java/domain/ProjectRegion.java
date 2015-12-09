package com.github.sandor_balazs.nosql_java.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ProjectRegion.
 */

@Table(name = "projectRegion")
public class ProjectRegion implements Serializable {

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
        ProjectRegion projectRegion = (ProjectRegion) o;
        return Objects.equals(id, projectRegion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectRegion{" +
            "id=" + id +
            '}';
    }
}
