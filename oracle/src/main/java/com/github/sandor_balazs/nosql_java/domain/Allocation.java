package com.github.sandor_balazs.nosql_java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Allocation.
 */
@Entity
@Table(name = "allocation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Allocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fte")
    private Float fte;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @OneToOne    private ProjectCountry projectCountry;

    @OneToOne    private Employment employment;

    @OneToOne    private Skill skill;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getFte() {
        return fte;
    }

    public void setFte(Float fte) {
        this.fte = fte;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public ProjectCountry getProjectCountry() {
        return projectCountry;
    }

    public void setProjectCountry(ProjectCountry projectCountry) {
        this.projectCountry = projectCountry;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Allocation allocation = (Allocation) o;
        return Objects.equals(id, allocation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Allocation{" +
            "id=" + id +
            ", fte='" + fte + "'" +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            '}';
    }
}
