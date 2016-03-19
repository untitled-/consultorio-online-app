package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.PathologicBkg;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PathologicBkg entity.
 */
public interface PathologicBkgRepository extends JpaRepository<PathologicBkg,Long> {

    @Query("select distinct pathologicBkg from PathologicBkg pathologicBkg left join fetch pathologicBkg.diseasess")
    List<PathologicBkg> findAllWithEagerRelationships();

    @Query("select pathologicBkg from PathologicBkg pathologicBkg left join fetch pathologicBkg.diseasess where pathologicBkg.id =:id")
    PathologicBkg findOneWithEagerRelationships(@Param("id") Long id);

}
