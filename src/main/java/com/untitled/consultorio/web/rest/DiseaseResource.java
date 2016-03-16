package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Disease;
import com.untitled.consultorio.repository.DiseaseRepository;
import com.untitled.consultorio.repository.search.DiseaseSearchRepository;
import com.untitled.consultorio.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Disease.
 */
@RestController
@RequestMapping("/api")
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);
        
    @Inject
    private DiseaseRepository diseaseRepository;
    
    @Inject
    private DiseaseSearchRepository diseaseSearchRepository;
    
    /**
     * POST  /diseases -> Create a new disease.
     */
    @RequestMapping(value = "/diseases",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", disease);
        if (disease.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("disease", "idexists", "A new disease cannot already have an ID")).body(null);
        }
        Disease result = diseaseRepository.save(disease);
        diseaseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("disease", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diseases -> Updates an existing disease.
     */
    @RequestMapping(value = "/diseases",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disease> updateDisease(@RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to update Disease : {}", disease);
        if (disease.getId() == null) {
            return createDisease(disease);
        }
        Disease result = diseaseRepository.save(disease);
        diseaseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("disease", disease.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diseases -> get all the diseases.
     */
    @RequestMapping(value = "/diseases",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Disease> getAllDiseases() {
        log.debug("REST request to get all Diseases");
        return diseaseRepository.findAll();
            }

    /**
     * GET  /diseases/:id -> get the "id" disease.
     */
    @RequestMapping(value = "/diseases/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Disease> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Disease disease = diseaseRepository.findOne(id);
        return Optional.ofNullable(disease)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /diseases/:id -> delete the "id" disease.
     */
    @RequestMapping(value = "/diseases/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseRepository.delete(id);
        diseaseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("disease", id.toString())).build();
    }

    /**
     * SEARCH  /_search/diseases/:query -> search for the disease corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/diseases/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Disease> searchDiseases(@PathVariable String query) {
        log.debug("REST request to search Diseases for query {}", query);
        return StreamSupport
            .stream(diseaseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
