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
 * A Disease.
 */
@Entity
@Table(name = "disease")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "disease")
public class Disease implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "is_congenital")
    private Boolean isCongenital;
    
    @Column(name = "is_infectious")
    private Boolean isInfectious;
    
    @ManyToMany(mappedBy = "diseasess")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HeredoFamilyBkg> heredoFamilyBkgss = new HashSet<>();

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

    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsCongenital() {
        return isCongenital;
    }
    
    public void setIsCongenital(Boolean isCongenital) {
        this.isCongenital = isCongenital;
    }

    public Boolean getIsInfectious() {
        return isInfectious;
    }
    
    public void setIsInfectious(Boolean isInfectious) {
        this.isInfectious = isInfectious;
    }

    public Set<HeredoFamilyBkg> getHeredoFamilyBkgss() {
        return heredoFamilyBkgss;
    }

    public void setHeredoFamilyBkgss(Set<HeredoFamilyBkg> heredoFamilyBkgs) {
        this.heredoFamilyBkgss = heredoFamilyBkgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disease disease = (Disease) o;
        if(disease.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, disease.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Disease{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", type='" + type + "'" +
            ", isCongenital='" + isCongenital + "'" +
            ", isInfectious='" + isInfectious + "'" +
            '}';
    }
}
