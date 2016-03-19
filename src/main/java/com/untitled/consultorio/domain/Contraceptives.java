package com.untitled.consultorio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Contraceptives.
 */
@Entity
@Table(name = "contraceptives")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contraceptives")
public class Contraceptives implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @ManyToMany(mappedBy = "contraceptivesss")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GynecoobstetricBkg> gynecoobstetricBkgss = new HashSet<>();

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

    public Set<GynecoobstetricBkg> getGynecoobstetricBkgss() {
        return gynecoobstetricBkgss;
    }

    public void setGynecoobstetricBkgss(Set<GynecoobstetricBkg> gynecoobstetricBkgs) {
        this.gynecoobstetricBkgss = gynecoobstetricBkgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contraceptives contraceptives = (Contraceptives) o;
        if(contraceptives.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contraceptives.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contraceptives{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
