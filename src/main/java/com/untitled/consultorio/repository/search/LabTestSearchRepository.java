package com.untitled.consultorio.repository.search;

import com.untitled.consultorio.domain.LabTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LabTest entity.
 */
public interface LabTestSearchRepository extends ElasticsearchRepository<LabTest, Long> {
}
