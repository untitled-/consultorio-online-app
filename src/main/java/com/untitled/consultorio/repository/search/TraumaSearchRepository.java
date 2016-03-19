package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Trauma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Trauma entity.
 */
public interface TraumaSearchRepository extends ElasticsearchRepository<Trauma, Long> {
}
