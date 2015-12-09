package com.github.sandor_balazs.nosql_java.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Milestone.
 */
@Entity
@Table(name = "milestone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Milestone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "app_order")
    private Integer appOrder;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAppOrder() {
        return appOrder;
    }

    public void setAppOrder(Integer appOrder) {
        this.appOrder = appOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Milestone milestone = (Milestone) o;
        return Objects.equals(id, milestone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Milestone{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", appOrder='" + appOrder + "'" +
            '}';
    }
}
