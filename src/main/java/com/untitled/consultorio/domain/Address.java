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
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "street")
    private String street;
    
    @Column(name = "apt")
    private String apt;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "zip")
    private String zip;
    
    @Column(name = "country")
    private String country;
    
    @OneToOne(mappedBy = "addresss")
    @JsonIgnore
    private Patient patients;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    public String getApt() {
        return apt;
    }
    
    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
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
        Address address = (Address) o;
        if(address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", apt='" + apt + "'" +
            ", city='" + city + "'" +
            ", zip='" + zip + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
