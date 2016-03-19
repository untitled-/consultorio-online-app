package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.PathologicBkg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PathologicBkg entity.
 */
public interface PathologicBkgSearchRepository extends ElasticsearchRepository<PathologicBkg, Long> {
}
