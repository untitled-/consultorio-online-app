package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Drug;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Drug entity.
 */
public interface DrugSearchRepository extends ElasticsearchRepository<Drug, Long> {
}
