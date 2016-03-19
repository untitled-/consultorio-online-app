package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Symptom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Symptom entity.
 */
public interface SymptomRepository extends JpaRepository<Symptom,Long> {

}
