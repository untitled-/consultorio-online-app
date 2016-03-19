package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Consultation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Consultation entity.
 */
public interface ConsultationSearchRepository extends ElasticsearchRepository<Consultation, Long> {
}
