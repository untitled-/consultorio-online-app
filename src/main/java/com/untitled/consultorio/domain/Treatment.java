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
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "treatment")
public class Treatment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "prescription_number")
    private String prescriptionNumber;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "treatment_drugs",
               joinColumns = @JoinColumn(name="treatments_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="drugss_id", referencedColumnName="ID"))
    private Set<Drug> drugss = new HashSet<>();

    @OneToOne(mappedBy = "treatments")
    @JsonIgnore
    private Consultation consultations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }
    
    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public Set<Drug> getDrugss() {
        return drugss;
    }

    public void setDrugss(Set<Drug> drugs) {
        this.drugss = drugs;
    }

    public Consultation getConsultations() {
        return consultations;
    }

    public void setConsultations(Consultation consultation) {
        this.consultations = consultation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Treatment treatment = (Treatment) o;
        if(treatment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + id +
            ", prescriptionNumber='" + prescriptionNumber + "'" +
            '}';
    }
}
