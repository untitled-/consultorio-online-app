package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.QuirurgicalProcedure;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuirurgicalProcedure entity.
 */
public interface QuirurgicalProcedureRepository extends JpaRepository<QuirurgicalProcedure,Long> {

}
