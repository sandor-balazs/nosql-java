package com.github.sandor_balazs.nosql_java.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Milestone.
 */

@Document(collection = "milestone")
public class Milestone implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("app_order")
    private Integer appOrder;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
