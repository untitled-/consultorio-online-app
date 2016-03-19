package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.GynecoobstetricBkg;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the GynecoobstetricBkg entity.
 */
public interface GynecoobstetricBkgRepository extends JpaRepository<GynecoobstetricBkg,Long> {

    @Query("select distinct gynecoobstetricBkg from GynecoobstetricBkg gynecoobstetricBkg left join fetch gynecoobstetricBkg.contraceptivesss")
    List<GynecoobstetricBkg> findAllWithEagerRelationships();

    @Query("select gynecoobstetricBkg from GynecoobstetricBkg gynecoobstetricBkg left join fetch gynecoobstetricBkg.contraceptivesss where gynecoobstetricBkg.id =:id")
    GynecoobstetricBkg findOneWithEagerRelationships(@Param("id") Long id);

}
