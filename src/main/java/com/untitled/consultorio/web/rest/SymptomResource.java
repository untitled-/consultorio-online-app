package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Symptom;
import com.untitled.consultorio.repository.SymptomRepository;
import com.untitled.consultorio.repository.search.SymptomSearchRepository;
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
 * REST controller for managing Symptom.
 */
@RestController
@RequestMapping("/api")
public class SymptomResource {

    private final Logger log = LoggerFactory.getLogger(SymptomResource.class);
        
    @Inject
    private SymptomRepository symptomRepository;
    
    @Inject
    private SymptomSearchRepository symptomSearchRepository;
    
    /**
     * POST  /symptoms -> Create a new symptom.
     */
    @RequestMapping(value = "/symptoms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) throws URISyntaxException {
        log.debug("REST request to save Symptom : {}", symptom);
        if (symptom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("symptom", "idexists", "A new symptom cannot already have an ID")).body(null);
        }
        Symptom result = symptomRepository.save(symptom);
        symptomSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/symptoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("symptom", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /symptoms -> Updates an existing symptom.
     */
    @RequestMapping(value = "/symptoms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Symptom> updateSymptom(@RequestBody Symptom symptom) throws URISyntaxException {
        log.debug("REST request to update Symptom : {}", symptom);
        if (symptom.getId() == null) {
            return createSymptom(symptom);
        }
        Symptom result = symptomRepository.save(symptom);
        symptomSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("symptom", symptom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /symptoms -> get all the symptoms.
     */
    @RequestMapping(value = "/symptoms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Symptom> getAllSymptoms() {
        log.debug("REST request to get all Symptoms");
        return symptomRepository.findAll();
            }

    /**
     * GET  /symptoms/:id -> get the "id" symptom.
     */
    @RequestMapping(value = "/symptoms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Symptom> getSymptom(@PathVariable Long id) {
        log.debug("REST request to get Symptom : {}", id);
        Symptom symptom = symptomRepository.findOne(id);
        return Optional.ofNullable(symptom)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /symptoms/:id -> delete the "id" symptom.
     */
    @RequestMapping(value = "/symptoms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        log.debug("REST request to delete Symptom : {}", id);
        symptomRepository.delete(id);
        symptomSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("symptom", id.toString())).build();
    }

    /**
     * SEARCH  /_search/symptoms/:query -> search for the symptom corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/symptoms/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Symptom> searchSymptoms(@PathVariable String query) {
        log.debug("REST request to search Symptoms for query {}", query);
        return StreamSupport
            .stream(symptomSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
