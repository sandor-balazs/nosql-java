package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the ProjectRegion entity.
 */
public class ProjectRegionDTO implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectRegionDTO projectRegionDTO = (ProjectRegionDTO) o;

        if ( ! Objects.equals(id, projectRegionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectRegionDTO{" +
            "id=" + id +
            '}';
    }
}
