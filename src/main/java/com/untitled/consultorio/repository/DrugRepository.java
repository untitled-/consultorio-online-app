package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Drug;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Drug entity.
 */
public interface DrugRepository extends JpaRepository<Drug,Long> {

}
