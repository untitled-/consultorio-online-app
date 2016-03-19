package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Consultation;
import com.untitled.consultorio.repository.ConsultationRepository;
import com.untitled.consultorio.repository.search.ConsultationSearchRepository;
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
 * REST controller for managing Consultation.
 */
@RestController
@RequestMapping("/api")
public class ConsultationResource {

    private final Logger log = LoggerFactory.getLogger(ConsultationResource.class);
        
    @Inject
    private ConsultationRepository consultationRepository;
    
    @Inject
    private ConsultationSearchRepository consultationSearchRepository;
    
    /**
     * POST  /consultations -> Create a new consultation.
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) throws URISyntaxException {
        log.debug("REST request to save Consultation : {}", consultation);
        if (consultation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("consultation", "idexists", "A new consultation cannot already have an ID")).body(null);
        }
        Consultation result = consultationRepository.save(consultation);
        consultationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/consultations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("consultation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultations -> Updates an existing consultation.
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consultation> updateConsultation(@RequestBody Consultation consultation) throws URISyntaxException {
        log.debug("REST request to update Consultation : {}", consultation);
        if (consultation.getId() == null) {
            return createConsultation(consultation);
        }
        Consultation result = consultationRepository.save(consultation);
        consultationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("consultation", consultation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultations -> get all the consultations.
     */
    @RequestMapping(value = "/consultations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Consultation> getAllConsultations() {
        log.debug("REST request to get all Consultations");
        return consultationRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /consultations/:id -> get the "id" consultation.
     */
    @RequestMapping(value = "/consultations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Consultation> getConsultation(@PathVariable Long id) {
        log.debug("REST request to get Consultation : {}", id);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(consultation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /consultations/:id -> delete the "id" consultation.
     */
    @RequestMapping(value = "/consultations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        log.debug("REST request to delete Consultation : {}", id);
        consultationRepository.delete(id);
        consultationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("consultation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/consultations/:query -> search for the consultation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/consultations/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Consultation> searchConsultations(@PathVariable String query) {
        log.debug("REST request to search Consultations for query {}", query);
        return StreamSupport
            .stream(consultationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
