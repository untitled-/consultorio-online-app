package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Trauma;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trauma entity.
 */
public interface TraumaRepository extends JpaRepository<Trauma,Long> {

}
