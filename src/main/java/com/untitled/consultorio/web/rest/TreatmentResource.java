package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Treatment;
import com.untitled.consultorio.repository.TreatmentRepository;
import com.untitled.consultorio.repository.search.TreatmentSearchRepository;
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
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);
        
    @Inject
    private TreatmentRepository treatmentRepository;
    
    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;
    
    /**
     * POST  /treatments -> Create a new treatment.
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatment);
        if (treatment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("treatment", "idexists", "A new treatment cannot already have an ID")).body(null);
        }
        Treatment result = treatmentRepository.save(treatment);
        treatmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("treatment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments -> Updates an existing treatment.
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> updateTreatment(@RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatment);
        if (treatment.getId() == null) {
            return createTreatment(treatment);
        }
        Treatment result = treatmentRepository.save(treatment);
        treatmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("treatment", treatment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments -> get all the treatments.
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Treatment> getAllTreatments(@RequestParam(required = false) String filter) {
        if ("consultations-is-null".equals(filter)) {
            log.debug("REST request to get all Treatments where consultations is null");
            return StreamSupport
                .stream(treatmentRepository.findAll().spliterator(), false)
                .filter(treatment -> treatment.getConsultations() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Treatments");
        return treatmentRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /treatments/:id -> get the "id" treatment.
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Treatment treatment = treatmentRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(treatment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /treatments/:id -> delete the "id" treatment.
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentRepository.delete(id);
        treatmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("treatment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/treatments/:query -> search for the treatment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/treatments/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Treatment> searchTreatments(@PathVariable String query) {
        log.debug("REST request to search Treatments for query {}", query);
        return StreamSupport
            .stream(treatmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
