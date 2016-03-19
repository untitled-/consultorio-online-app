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
 * A NonPathologicBkg.
 */
@Entity
@Table(name = "non_pathologic_bkg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "nonpathologicbkg")
public class NonPathologicBkg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "housing")
    private String housing;
    
    @Column(name = "has_zoonosis")
    private Boolean hasZoonosis;
    
    @Column(name = "zoonosis_desc")
    private String zoonosisDesc;
    
    @Column(name = "is_overcrowded")
    private Boolean isOvercrowded;
    
    @Column(name = "overcrowding_desc")
    private String overcrowdingDesc;
    
    @Column(name = "is_feeding_balanced")
    private Boolean isFeedingBalanced;
    
    @Column(name = "feeding_desc")
    private String feedingDesc;
    
    @Column(name = "hygiene_desc")
    private String hygieneDesc;
    
    @OneToMany(mappedBy = "nonPathologicBkgs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immunization> immunizationss = new HashSet<>();

    @OneToOne(mappedBy = "nonPathologicBkgs")
    @JsonIgnore
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHousing() {
        return housing;
    }
    
    public void setHousing(String housing) {
        this.housing = housing;
    }

    public Boolean getHasZoonosis() {
        return hasZoonosis;
    }
    
    public void setHasZoonosis(Boolean hasZoonosis) {
        this.hasZoonosis = hasZoonosis;
    }

    public String getZoonosisDesc() {
        return zoonosisDesc;
    }
    
    public void setZoonosisDesc(String zoonosisDesc) {
        this.zoonosisDesc = zoonosisDesc;
    }

    public Boolean getIsOvercrowded() {
        return isOvercrowded;
    }
    
    public void setIsOvercrowded(Boolean isOvercrowded) {
        this.isOvercrowded = isOvercrowded;
    }

    public String getOvercrowdingDesc() {
        return overcrowdingDesc;
    }
    
    public void setOvercrowdingDesc(String overcrowdingDesc) {
        this.overcrowdingDesc = overcrowdingDesc;
    }

    public Boolean getIsFeedingBalanced() {
        return isFeedingBalanced;
    }
    
    public void setIsFeedingBalanced(Boolean isFeedingBalanced) {
        this.isFeedingBalanced = isFeedingBalanced;
    }

    public String getFeedingDesc() {
        return feedingDesc;
    }
    
    public void setFeedingDesc(String feedingDesc) {
        this.feedingDesc = feedingDesc;
    }

    public String getHygieneDesc() {
        return hygieneDesc;
    }
    
    public void setHygieneDesc(String hygieneDesc) {
        this.hygieneDesc = hygieneDesc;
    }

    public Set<Immunization> getImmunizationss() {
        return immunizationss;
    }

    public void setImmunizationss(Set<Immunization> immunizations) {
        this.immunizationss = immunizations;
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
        NonPathologicBkg nonPathologicBkg = (NonPathologicBkg) o;
        if(nonPathologicBkg.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nonPathologicBkg.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NonPathologicBkg{" +
            "id=" + id +
            ", housing='" + housing + "'" +
            ", hasZoonosis='" + hasZoonosis + "'" +
            ", zoonosisDesc='" + zoonosisDesc + "'" +
            ", isOvercrowded='" + isOvercrowded + "'" +
            ", overcrowdingDesc='" + overcrowdingDesc + "'" +
            ", isFeedingBalanced='" + isFeedingBalanced + "'" +
            ", feedingDesc='" + feedingDesc + "'" +
            ", hygieneDesc='" + hygieneDesc + "'" +
            '}';
    }
}
