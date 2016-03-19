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
 * A PathologicBkg.
 */
@Entity
@Table(name = "pathologic_bkg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pathologicbkg")
public class PathologicBkg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "observations")
    private String observations;
    
    @OneToMany(mappedBy = "pathologicBkgs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Addiction> addictionss = new HashSet<>();

    @OneToMany(mappedBy = "pathologicBkgs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Alergy> alergyss = new HashSet<>();

    @OneToMany(mappedBy = "pathologicBkgs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuirurgicalProcedure> quirurgicalProceduress = new HashSet<>();

    @OneToMany(mappedBy = "pathologicBkgs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trauma> traumass = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pathologic_bkg_diseases",
               joinColumns = @JoinColumn(name="pathologic_bkgs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="diseasess_id", referencedColumnName="ID"))
    private Set<Disease> diseasess = new HashSet<>();

    @OneToOne(mappedBy = "pathologicBkgs")
    @JsonIgnore
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }
    
    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Set<Addiction> getAddictionss() {
        return addictionss;
    }

    public void setAddictionss(Set<Addiction> addictions) {
        this.addictionss = addictions;
    }

    public Set<Alergy> getAlergyss() {
        return alergyss;
    }

    public void setAlergyss(Set<Alergy> alergys) {
        this.alergyss = alergys;
    }

    public Set<QuirurgicalProcedure> getQuirurgicalProceduress() {
        return quirurgicalProceduress;
    }

    public void setQuirurgicalProceduress(Set<QuirurgicalProcedure> quirurgicalProcedures) {
        this.quirurgicalProceduress = quirurgicalProcedures;
    }

    public Set<Trauma> getTraumass() {
        return traumass;
    }

    public void setTraumass(Set<Trauma> traumas) {
        this.traumass = traumas;
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
        PathologicBkg pathologicBkg = (PathologicBkg) o;
        if(pathologicBkg.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pathologicBkg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PathologicBkg{" +
            "id=" + id +
            ", observations='" + observations + "'" +
            '}';
    }
}
