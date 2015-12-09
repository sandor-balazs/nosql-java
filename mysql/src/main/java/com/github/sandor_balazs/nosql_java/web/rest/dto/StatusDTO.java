package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Status entity.
 */
public class StatusDTO implements Serializable {

    private Long id;

    private String name;

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

        StatusDTO statusDTO = (StatusDTO) o;

        if ( ! Objects.equals(id, statusDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", appOrder='" + appOrder + "'" +
            '}';
    }
}
