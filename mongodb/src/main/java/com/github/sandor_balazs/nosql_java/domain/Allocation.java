package com.github.sandor_balazs.nosql_java.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Allocation.
 */

@Document(collection = "allocation")
public class Allocation implements Serializable {

    @Id
    private String id;

    @Field("fte")
    private Float fte;

    @Field("year")
    private Integer year;

    @Field("month")
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
