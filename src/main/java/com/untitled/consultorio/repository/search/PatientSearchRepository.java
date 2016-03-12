package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Patient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Patient entity.
 */
public interface PatientSearchRepository extends ElasticsearchRepository<Patient, Long> {
}
