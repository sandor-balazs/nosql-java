package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Employment entity.
 */
public class EmploymentDTO implements Serializable {

    private String id;

    private Float fte;

    private LocalDate startDate;

    private LocalDate endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getFte() {
        return fte;
    }

    public void setFte(Float fte) {
        this.fte = fte;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmploymentDTO employmentDTO = (EmploymentDTO) o;

        if ( ! Objects.equals(id, employmentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmploymentDTO{" +
            "id=" + id +
            ", fte='" + fte + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
