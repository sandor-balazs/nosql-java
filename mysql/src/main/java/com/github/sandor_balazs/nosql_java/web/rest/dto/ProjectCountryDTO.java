package com.github.sandor_balazs.nosql_java.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ProjectCountry entity.
 */
public class ProjectCountryDTO implements Serializable {

    private Long id;

    private Long projectRegionId;
    private Long countryId;

    private String countryName;

    private Long statusId;

    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectRegionId() {
        return projectRegionId;
    }

    public void setProjectRegionId(Long projectRegionId) {
        this.projectRegionId = projectRegionId;
    }
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectCountryDTO projectCountryDTO = (ProjectCountryDTO) o;

        if ( ! Objects.equals(id, projectCountryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectCountryDTO{" +
            "id=" + id +
            '}';
    }
}
