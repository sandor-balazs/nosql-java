package com.github.sandor_balazs.nosql_java.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Milestone.
 */

@Table(name = "milestone")
public class Milestone implements Serializable {

    @PartitionKey
    private UUID id;

    private String name;

    private Integer appOrder;

    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAppOrder() {
        return appOrder;
    }

    public void setAppOrder(Integer appOrder) {
        this.appOrder = appOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Milestone milestone = (Milestone) o;
        return Objects.equals(id, milestone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Milestone{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", appOrder='" + appOrder + "'" +
            '}';
    }
}
