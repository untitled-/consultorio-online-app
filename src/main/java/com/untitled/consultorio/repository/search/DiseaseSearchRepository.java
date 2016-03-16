package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Disease;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Disease entity.
 */
public interface DiseaseSearchRepository extends ElasticsearchRepository<Disease, Long> {
}
