package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Contraceptives;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contraceptives entity.
 */
public interface ContraceptivesRepository extends JpaRepository<Contraceptives,Long> {

}
