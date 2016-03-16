package com.untitled.consultorio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.untitled.consultorio.domain.enumeration.BloodType;

import com.untitled.consultorio.domain.enumeration.MaritalStatus;

import com.untitled.consultorio.domain.enumeration.Gender;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "job")
    private String job;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @OneToOne
    private Address addresss;

    @OneToMany(mappedBy = "patients")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> contactss = new HashSet<>();

    @OneToOne
    private HeredoFamilyBkg heredoFamilyBkgs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getJob() {
        return job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }

    public BloodType getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
    
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddresss() {
        return addresss;
    }

    public void setAddresss(Address address) {
        this.addresss = address;
    }

    public Set<Contact> getContactss() {
        return contactss;
    }

    public void setContactss(Set<Contact> contacts) {
        this.contactss = contacts;
    }

    public HeredoFamilyBkg getHeredoFamilyBkgs() {
        return heredoFamilyBkgs;
    }

    public void setHeredoFamilyBkgs(HeredoFamilyBkg heredoFamilyBkg) {
        this.heredoFamilyBkgs = heredoFamilyBkg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if(patient.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", middleName='" + middleName + "'" +
            ", lastName='" + lastName + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", job='" + job + "'" +
            ", bloodType='" + bloodType + "'" +
            ", maritalStatus='" + maritalStatus + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
