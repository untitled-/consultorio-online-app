package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.HeredoFamilyBkg;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HeredoFamilyBkg entity.
 */
public interface HeredoFamilyBkgRepository extends JpaRepository<HeredoFamilyBkg,Long> {

    @Query("select distinct heredoFamilyBkg from HeredoFamilyBkg heredoFamilyBkg left join fetch heredoFamilyBkg.diseasess")
    List<HeredoFamilyBkg> findAllWithEagerRelationships();

    @Query("select heredoFamilyBkg from HeredoFamilyBkg heredoFamilyBkg left join fetch heredoFamilyBkg.diseasess where heredoFamilyBkg.id =:id")
    HeredoFamilyBkg findOneWithEagerRelationships(@Param("id") Long id);

}
