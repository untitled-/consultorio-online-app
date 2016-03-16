package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.HeredoFamilyBkg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HeredoFamilyBkg entity.
 */
public interface HeredoFamilyBkgSearchRepository extends ElasticsearchRepository<HeredoFamilyBkg, Long> {
}
