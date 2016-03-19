package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Treatment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Treatment entity.
 */
public interface TreatmentSearchRepository extends ElasticsearchRepository<Treatment, Long> {
}
