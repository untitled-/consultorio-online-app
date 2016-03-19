package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Alergy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Alergy entity.
 */
public interface AlergySearchRepository extends ElasticsearchRepository<Alergy, Long> {
}
