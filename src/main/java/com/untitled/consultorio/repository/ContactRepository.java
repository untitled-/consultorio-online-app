package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Contact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contact entity.
 */
public interface ContactRepository extends JpaRepository<Contact,Long> {

}
