package com.github.sandor_balazs.nosql_java.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Funding.
 */

@Document(collection = "funding")
public class Funding implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Funding funding = (Funding) o;
        return Objects.equals(id, funding.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Funding{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
