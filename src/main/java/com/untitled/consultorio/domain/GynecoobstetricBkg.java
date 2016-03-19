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
 * A GynecoobstetricBkg.
 */
@Entity
@Table(name = "gynecoobstetric_bkg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gynecoobstetricbkg")
public class GynecoobstetricBkg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "menarche")
    private LocalDate menarche;
    
    @Column(name = "beggining_sexual_life")
    private LocalDate begginingSexualLife;
    
    @Column(name = "pregnancies_number")
    private Integer pregnanciesNumber;
    
    @Column(name = "miscarriages_number")
    private Integer miscarriagesNumber;
    
    @Column(name = "c_sections_number")
    private Integer cSectionsNumber;
    
    @Column(name = "pregnancy_details")
    private String pregnancyDetails;
    
    @Column(name = "latest_pap_test")
    private LocalDate latestPapTest;
    
    @Column(name = "latest_pap_test_details")
    private String latestPapTestDetails;
    
    @Column(name = "latest_mammography")
    private LocalDate latestMammography;
    
    @Column(name = "latest_mammography_details")
    private String latestMammographyDetails;
    
    @Column(name = "uses_contraceptives")
    private Boolean usesContraceptives;
    
    @Column(name = "has_menopause")
    private Boolean hasMenopause;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "gynecoobstetric_bkg_contraceptivess",
               joinColumns = @JoinColumn(name="gynecoobstetric_bkgs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="contraceptivesss_id", referencedColumnName="ID"))
    private Set<Contraceptives> contraceptivesss = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "patients_id")
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMenarche() {
        return menarche;
    }
    
    public void setMenarche(LocalDate menarche) {
        this.menarche = menarche;
    }

    public LocalDate getBegginingSexualLife() {
        return begginingSexualLife;
    }
    
    public void setBegginingSexualLife(LocalDate begginingSexualLife) {
        this.begginingSexualLife = begginingSexualLife;
    }

    public Integer getPregnanciesNumber() {
        return pregnanciesNumber;
    }
    
    public void setPregnanciesNumber(Integer pregnanciesNumber) {
        this.pregnanciesNumber = pregnanciesNumber;
    }

    public Integer getMiscarriagesNumber() {
        return miscarriagesNumber;
    }
    
    public void setMiscarriagesNumber(Integer miscarriagesNumber) {
        this.miscarriagesNumber = miscarriagesNumber;
    }

    public Integer getcSectionsNumber() {
        return cSectionsNumber;
    }
    
    public void setcSectionsNumber(Integer cSectionsNumber) {
        this.cSectionsNumber = cSectionsNumber;
    }

    public String getPregnancyDetails() {
        return pregnancyDetails;
    }
    
    public void setPregnancyDetails(String pregnancyDetails) {
        this.pregnancyDetails = pregnancyDetails;
    }

    public LocalDate getLatestPapTest() {
        return latestPapTest;
    }
    
    public void setLatestPapTest(LocalDate latestPapTest) {
        this.latestPapTest = latestPapTest;
    }

    public String getLatestPapTestDetails() {
        return latestPapTestDetails;
    }
    
    public void setLatestPapTestDetails(String latestPapTestDetails) {
        this.latestPapTestDetails = latestPapTestDetails;
    }

    public LocalDate getLatestMammography() {
        return latestMammography;
    }
    
    public void setLatestMammography(LocalDate latestMammography) {
        this.latestMammography = latestMammography;
    }

    public String getLatestMammographyDetails() {
        return latestMammographyDetails;
    }
    
    public void setLatestMammographyDetails(String latestMammographyDetails) {
        this.latestMammographyDetails = latestMammographyDetails;
    }

    public Boolean getUsesContraceptives() {
        return usesContraceptives;
    }
    
    public void setUsesContraceptives(Boolean usesContraceptives) {
        this.usesContraceptives = usesContraceptives;
    }

    public Boolean getHasMenopause() {
        return hasMenopause;
    }
    
    public void setHasMenopause(Boolean hasMenopause) {
        this.hasMenopause = hasMenopause;
    }

    public Set<Contraceptives> getContraceptivesss() {
        return contraceptivesss;
    }

    public void setContraceptivesss(Set<Contraceptives> contraceptivess) {
        this.contraceptivesss = contraceptivess;
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
        GynecoobstetricBkg gynecoobstetricBkg = (GynecoobstetricBkg) o;
        if(gynecoobstetricBkg.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gynecoobstetricBkg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GynecoobstetricBkg{" +
            "id=" + id +
            ", menarche='" + menarche + "'" +
            ", begginingSexualLife='" + begginingSexualLife + "'" +
            ", pregnanciesNumber='" + pregnanciesNumber + "'" +
            ", miscarriagesNumber='" + miscarriagesNumber + "'" +
            ", cSectionsNumber='" + cSectionsNumber + "'" +
            ", pregnancyDetails='" + pregnancyDetails + "'" +
            ", latestPapTest='" + latestPapTest + "'" +
            ", latestPapTestDetails='" + latestPapTestDetails + "'" +
            ", latestMammography='" + latestMammography + "'" +
            ", latestMammographyDetails='" + latestMammographyDetails + "'" +
            ", usesContraceptives='" + usesContraceptives + "'" +
            ", hasMenopause='" + hasMenopause + "'" +
            '}';
    }
}
