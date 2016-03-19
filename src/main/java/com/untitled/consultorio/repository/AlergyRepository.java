package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Alergy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Alergy entity.
 */
public interface AlergyRepository extends JpaRepository<Alergy,Long> {

}
