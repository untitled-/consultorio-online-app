package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Immunization;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Immunization entity.
 */
public interface ImmunizationRepository extends JpaRepository<Immunization,Long> {

}
