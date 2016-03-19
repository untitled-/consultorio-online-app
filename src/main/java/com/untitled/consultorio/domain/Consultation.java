package com.untitled.consultorio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "consultation")
public class Consultation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "consultation_date")
    private LocalDate consultationDate;
    
    @Column(name = "idx")
    private String idx;
    
    @Column(name = "diferential_diagnostic")
    private String diferentialDiagnostic;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "consultation_tests",
               joinColumns = @JoinColumn(name="consultations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="testss_id", referencedColumnName="ID"))
    private Set<LabTest> testss = new HashSet<>();

    @OneToOne
    private Treatment treatments;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "consultation_symptoms",
               joinColumns = @JoinColumn(name="consultations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="symptomss_id", referencedColumnName="ID"))
    private Set<Symptom> symptomss = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "patients_id")
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getConsultationDate() {
        return consultationDate;
    }
    
    public void setConsultationDate(LocalDate consultationDate) {
        this.consultationDate = consultationDate;
    }

    public String getIdx() {
        return idx;
    }
    
    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getDiferentialDiagnostic() {
        return diferentialDiagnostic;
    }
    
    public void setDiferentialDiagnostic(String diferentialDiagnostic) {
        this.diferentialDiagnostic = diferentialDiagnostic;
    }

    public Set<LabTest> getTestss() {
        return testss;
    }

    public void setTestss(Set<LabTest> labTests) {
        this.testss = labTests;
    }

    public Treatment getTreatments() {
        return treatments;
    }

    public void setTreatments(Treatment treatment) {
        this.treatments = treatment;
    }

    public Set<Symptom> getSymptomss() {
        return symptomss;
    }

    public void setSymptomss(Set<Symptom> symptoms) {
        this.symptomss = symptoms;
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
        Consultation consultation = (Consultation) o;
        if(consultation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, consultation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Consultation{" +
            "id=" + id +
            ", consultationDate='" + consultationDate + "'" +
            ", idx='" + idx + "'" +
            ", diferentialDiagnostic='" + diferentialDiagnostic + "'" +
            '}';
    }
}
