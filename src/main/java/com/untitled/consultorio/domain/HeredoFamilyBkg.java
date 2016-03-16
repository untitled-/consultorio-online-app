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
 * A HeredoFamilyBkg.
 */
@Entity
@Table(name = "heredo_family_bkg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "heredofamilybkg")
public class HeredoFamilyBkg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "observation")
    private String observation;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "heredo_family_bkg_diseases",
               joinColumns = @JoinColumn(name="heredo_family_bkgs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="diseasess_id", referencedColumnName="ID"))
    private Set<Disease> diseasess = new HashSet<>();

    @OneToOne(mappedBy = "heredoFamilyBkgs")
    @JsonIgnore
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }
    
    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Set<Disease> getDiseasess() {
        return diseasess;
    }

    public void setDiseasess(Set<Disease> diseases) {
        this.diseasess = diseases;
    }

    public Patient getPatients() {
        return patients;
    }

    public void setPatients(Patient patient) {
        this.patients = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeredoFamilyBkg heredoFamilyBkg = (HeredoFamilyBkg) o;
        if(heredoFamilyBkg.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heredoFamilyBkg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeredoFamilyBkg{" +
            "id=" + id +
            ", observation='" + observation + "'" +
            '}';
    }
}
