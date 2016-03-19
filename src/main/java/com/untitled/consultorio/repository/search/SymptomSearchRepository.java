package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Symptom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Symptom entity.
 */
public interface SymptomSearchRepository extends ElasticsearchRepository<Symptom, Long> {
}
