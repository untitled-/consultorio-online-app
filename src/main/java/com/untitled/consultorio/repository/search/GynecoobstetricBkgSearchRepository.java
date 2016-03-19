package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.GynecoobstetricBkg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GynecoobstetricBkg entity.
 */
public interface GynecoobstetricBkgSearchRepository extends ElasticsearchRepository<GynecoobstetricBkg, Long> {
}
