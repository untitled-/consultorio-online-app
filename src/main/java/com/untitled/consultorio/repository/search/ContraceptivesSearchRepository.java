package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.Contraceptives;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Contraceptives entity.
 */
public interface ContraceptivesSearchRepository extends ElasticsearchRepository<Contraceptives, Long> {
}
