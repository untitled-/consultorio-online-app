package com.untitled.consultorio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Alergy.
 */
@Entity
@Table(name = "alergy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "alergy")
public class Alergy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "pathologic_bkgs_id")
    private PathologicBkg pathologicBkgs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public PathologicBkg getPathologicBkgs() {
        return pathologicBkgs;
    }

    public void setPathologicBkgs(PathologicBkg pathologicBkg) {
        this.pathologicBkgs = pathologicBkg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alergy alergy = (Alergy) o;
        if(alergy.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alergy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alergy{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
