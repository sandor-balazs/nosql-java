package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the ProjectCountryMilestone entity.
 */
public class ProjectCountryMilestoneDTO implements Serializable {

    private String id;

    private LocalDate planned;

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
