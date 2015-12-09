package com.github.sandor_balazs.nosql_java.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectCountry.
 */

@Document(collection = "project_country")
public class ProjectCountry implements Serializable {

    @Id
    private String id;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
