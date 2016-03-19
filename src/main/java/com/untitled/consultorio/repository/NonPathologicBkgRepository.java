package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.NonPathologicBkg;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NonPathologicBkg entity.
 */
public interface NonPathologicBkgRepository extends JpaRepository<NonPathologicBkg,Long> {

}
