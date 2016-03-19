package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.NonPathologicBkg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NonPathologicBkg entity.
 */
public interface NonPathologicBkgSearchRepository extends ElasticsearchRepository<NonPathologicBkg, Long> {
}
