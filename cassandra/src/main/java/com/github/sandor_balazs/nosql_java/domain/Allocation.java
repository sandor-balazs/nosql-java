package com.github.sandor_balazs.nosql_java.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Allocation.
 */

@Table(name = "allocation")
public class Allocation implements Serializable {

    @PartitionKey
    private UUID id;

    private Float fte;

    private Integer year;

    private Integer month;

    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
