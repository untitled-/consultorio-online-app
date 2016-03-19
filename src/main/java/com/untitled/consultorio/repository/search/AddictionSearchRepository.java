package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Addiction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Addiction entity.
 */
public interface AddictionSearchRepository extends ElasticsearchRepository<Addiction, Long> {
}
