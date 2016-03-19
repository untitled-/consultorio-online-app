package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Immunization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Immunization entity.
 */
public interface ImmunizationSearchRepository extends ElasticsearchRepository<Immunization, Long> {
}
