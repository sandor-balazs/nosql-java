package com.github.sandor_balazs.nosql_java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProjectCountryMilestone.
 */
@Entity
@Table(name = "project_country_milestone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProjectCountryMilestone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "planned")
    private LocalDate planned;

    @Column(name = "actual")
    private LocalDate actual;

    @OneToOne    private ProjectCountry projectCountry;

    @OneToOne    private Milestone milestone;

    
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

    public ProjectCountry getProjectCountry() {
        return projectCountry;
    }

    public void setProjectCountry(ProjectCountry projectCountry) {
        this.projectCountry = projectCountry;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectCountryMilestone projectCountryMilestone = (ProjectCountryMilestone) o;
        return Objects.equals(id, projectCountryMilestone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectCountryMilestone{" +
            "id=" + id +
            ", planned='" + planned + "'" +
            ", actual='" + actual + "'" +
            '}';
    }
}
