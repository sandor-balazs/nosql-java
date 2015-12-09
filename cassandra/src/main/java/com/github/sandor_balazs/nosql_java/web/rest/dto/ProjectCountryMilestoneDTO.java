package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * A DTO for the ProjectCountryMilestone entity.
 */
public class ProjectCountryMilestoneDTO implements Serializable {

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

        ProjectCountryMilestoneDTO projectCountryMilestoneDTO = (ProjectCountryMilestoneDTO) o;

        if ( ! Objects.equals(id, projectCountryMilestoneDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectCountryMilestoneDTO{" +
            "id=" + id +
            ", planned='" + planned + "'" +
            ", actual='" + actual + "'" +
            '}';
    }
}
