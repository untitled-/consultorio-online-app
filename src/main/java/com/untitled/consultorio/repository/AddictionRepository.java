package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Addiction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Addiction entity.
 */
public interface AddictionRepository extends JpaRepository<Addiction,Long> {

}
