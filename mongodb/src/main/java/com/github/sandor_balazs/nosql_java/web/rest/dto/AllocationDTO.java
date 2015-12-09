package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Allocation entity.
 */
public class AllocationDTO implements Serializable {

    private String id;

    private Float fte;

    private Integer year;

    private Integer month;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AllocationDTO allocationDTO = (AllocationDTO) o;

        if ( ! Objects.equals(id, allocationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AllocationDTO{" +
            "id=" + id +
            ", fte='" + fte + "'" +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            '}';
    }
}
