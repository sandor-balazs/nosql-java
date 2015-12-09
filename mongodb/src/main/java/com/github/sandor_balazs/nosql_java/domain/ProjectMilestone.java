package com.github.sandor_balazs.nosql_java.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectMilestone.
 */

@Document(collection = "project_milestone")
public class ProjectMilestone implements Serializable {

    @Id
    private String id;

    @Field("planned")
    private LocalDate planned;

    @Field("actual")
    private LocalDate actual;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getPlanned() {
        return planned;
    }

    public void setPlanned(LocalDate planned) {
        this.planned = planned;
    }

    public LocalDate getActual() {
        return actual;
    }

    public void setActual(LocalDate actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectMilestone projectMilestone = (ProjectMilestone) o;
        return Objects.equals(id, projectMilestone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectMilestone{" +
            "id=" + id +
            ", planned='" + planned + "'" +
            ", actual='" + actual + "'" +
            '}';
    }
}
