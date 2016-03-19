package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.LabTest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LabTest entity.
 */
public interface LabTestRepository extends JpaRepository<LabTest,Long> {

}
