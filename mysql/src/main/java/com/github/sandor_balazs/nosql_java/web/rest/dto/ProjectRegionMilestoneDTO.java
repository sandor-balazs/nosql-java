package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ProjectRegionMilestone entity.
 */
public class ProjectRegionMilestoneDTO implements Serializable {

    private Long id;

    private LocalDate planned;

    private LocalDate actual;

    private Long projectRegionId;
    private Long milestoneId;

    private String milestoneName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getProjectRegionId() {
        return projectRegionId;
    }

    public void setProjectRegionId(Long projectRegionId) {
        this.projectRegionId = projectRegionId;
    }
    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectRegionMilestoneDTO projectRegionMilestoneDTO = (ProjectRegionMilestoneDTO) o;

        if ( ! Objects.equals(id, projectRegionMilestoneDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectRegionMilestoneDTO{" +
            "id=" + id +
            ", planned='" + planned + "'" +
            ", actual='" + actual + "'" +
            '}';
    }
}
