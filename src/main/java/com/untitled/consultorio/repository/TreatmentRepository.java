package com.untitled.consultorio.repository;

import com.untitled.consultorio.domain.Treatment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Treatment entity.
 */
public interface TreatmentRepository extends JpaRepository<Treatment,Long> {

    @Query("select distinct treatment from Treatment treatment left join fetch treatment.drugss")
    List<Treatment> findAllWithEagerRelationships();

    @Query("select treatment from Treatment treatment left join fetch treatment.drugss where treatment.id =:id")
    Treatment findOneWithEagerRelationships(@Param("id") Long id);

}
