package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.QuirurgicalProcedure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the QuirurgicalProcedure entity.
 */
public interface QuirurgicalProcedureSearchRepository extends ElasticsearchRepository<QuirurgicalProcedure, Long> {
}
