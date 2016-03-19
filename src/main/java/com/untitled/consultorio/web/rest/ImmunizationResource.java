package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Immunization;
import com.untitled.consultorio.repository.ImmunizationRepository;
import com.untitled.consultorio.repository.search.ImmunizationSearchRepository;
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
 * REST controller for managing Immunization.
 */
@RestController
@RequestMapping("/api")
public class ImmunizationResource {

    private final Logger log = LoggerFactory.getLogger(ImmunizationResource.class);
        
    @Inject
    private ImmunizationRepository immunizationRepository;
    
    @Inject
    private ImmunizationSearchRepository immunizationSearchRepository;
    
    /**
     * POST  /immunizations -> Create a new immunization.
     */
    @RequestMapping(value = "/immunizations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Immunization> createImmunization(@RequestBody Immunization immunization) throws URISyntaxException {
        log.debug("REST request to save Immunization : {}", immunization);
        if (immunization.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("immunization", "idexists", "A new immunization cannot already have an ID")).body(null);
        }
        Immunization result = immunizationRepository.save(immunization);
        immunizationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/immunizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("immunization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /immunizations -> Updates an existing immunization.
     */
    @RequestMapping(value = "/immunizations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Immunization> updateImmunization(@RequestBody Immunization immunization) throws URISyntaxException {
        log.debug("REST request to update Immunization : {}", immunization);
        if (immunization.getId() == null) {
            return createImmunization(immunization);
        }
        Immunization result = immunizationRepository.save(immunization);
        immunizationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("immunization", immunization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /immunizations -> get all the immunizations.
     */
    @RequestMapping(value = "/immunizations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Immunization> getAllImmunizations() {
        log.debug("REST request to get all Immunizations");
        return immunizationRepository.findAll();
            }

    /**
     * GET  /immunizations/:id -> get the "id" immunization.
     */
    @RequestMapping(value = "/immunizations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Immunization> getImmunization(@PathVariable Long id) {
        log.debug("REST request to get Immunization : {}", id);
        Immunization immunization = immunizationRepository.findOne(id);
        return Optional.ofNullable(immunization)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /immunizations/:id -> delete the "id" immunization.
     */
    @RequestMapping(value = "/immunizations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteImmunization(@PathVariable Long id) {
        log.debug("REST request to delete Immunization : {}", id);
        immunizationRepository.delete(id);
        immunizationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("immunization", id.toString())).build();
    }

    /**
     * SEARCH  /_search/immunizations/:query -> search for the immunization corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/immunizations/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Immunization> searchImmunizations(@PathVariable String query) {
        log.debug("REST request to search Immunizations for query {}", query);
        return StreamSupport
            .stream(immunizationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
