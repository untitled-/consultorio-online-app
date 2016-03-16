package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Disease;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Disease entity.
 */
public interface DiseaseRepository extends JpaRepository<Disease,Long> {

}
