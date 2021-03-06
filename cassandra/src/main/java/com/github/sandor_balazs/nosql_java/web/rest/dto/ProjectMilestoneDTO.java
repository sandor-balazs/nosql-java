package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * A DTO for the ProjectMilestone entity.
 */
public class ProjectMilestoneDTO implements Serializable {

    private UUID id;

    private Date planned;

    private Date actual;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getPlanned() {
        return planned;
    }

    public void setPlanned(Date planned) {
        this.planned = planned;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
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

        ProjectMilestoneDTO projectMilestoneDTO = (ProjectMilestoneDTO) o;

        if ( ! Objects.equals(id, projectMilestoneDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectMilestoneDTO{" +
            "id=" + id +
            ", planned='" + planned + "'" +
            ", actual='" + actual + "'" +
            '}';
    }
}
