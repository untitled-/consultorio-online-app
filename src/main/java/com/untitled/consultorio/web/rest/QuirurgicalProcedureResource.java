package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.QuirurgicalProcedure;
import com.untitled.consultorio.repository.QuirurgicalProcedureRepository;
import com.untitled.consultorio.repository.search.QuirurgicalProcedureSearchRepository;
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
 * REST controller for managing QuirurgicalProcedure.
 */
@RestController
@RequestMapping("/api")
public class QuirurgicalProcedureResource {

    private final Logger log = LoggerFactory.getLogger(QuirurgicalProcedureResource.class);
        
    @Inject
    private QuirurgicalProcedureRepository quirurgicalProcedureRepository;
    
    @Inject
    private QuirurgicalProcedureSearchRepository quirurgicalProcedureSearchRepository;
    
    /**
     * POST  /quirurgicalProcedures -> Create a new quirurgicalProcedure.
     */
    @RequestMapping(value = "/quirurgicalProcedures",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuirurgicalProcedure> createQuirurgicalProcedure(@RequestBody QuirurgicalProcedure quirurgicalProcedure) throws URISyntaxException {
        log.debug("REST request to save QuirurgicalProcedure : {}", quirurgicalProcedure);
        if (quirurgicalProcedure.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("quirurgicalProcedure", "idexists", "A new quirurgicalProcedure cannot already have an ID")).body(null);
        }
        QuirurgicalProcedure result = quirurgicalProcedureRepository.save(quirurgicalProcedure);
        quirurgicalProcedureSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quirurgicalProcedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quirurgicalProcedure", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quirurgicalProcedures -> Updates an existing quirurgicalProcedure.
     */
    @RequestMapping(value = "/quirurgicalProcedures",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuirurgicalProcedure> updateQuirurgicalProcedure(@RequestBody QuirurgicalProcedure quirurgicalProcedure) throws URISyntaxException {
        log.debug("REST request to update QuirurgicalProcedure : {}", quirurgicalProcedure);
        if (quirurgicalProcedure.getId() == null) {
            return createQuirurgicalProcedure(quirurgicalProcedure);
        }
        QuirurgicalProcedure result = quirurgicalProcedureRepository.save(quirurgicalProcedure);
        quirurgicalProcedureSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("quirurgicalProcedure", quirurgicalProcedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quirurgicalProcedures -> get all the quirurgicalProcedures.
     */
    @RequestMapping(value = "/quirurgicalProcedures",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<QuirurgicalProcedure> getAllQuirurgicalProcedures() {
        log.debug("REST request to get all QuirurgicalProcedures");
        return quirurgicalProcedureRepository.findAll();
            }

    /**
     * GET  /quirurgicalProcedures/:id -> get the "id" quirurgicalProcedure.
     */
    @RequestMapping(value = "/quirurgicalProcedures/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QuirurgicalProcedure> getQuirurgicalProcedure(@PathVariable Long id) {
        log.debug("REST request to get QuirurgicalProcedure : {}", id);
        QuirurgicalProcedure quirurgicalProcedure = quirurgicalProcedureRepository.findOne(id);
        return Optional.ofNullable(quirurgicalProcedure)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quirurgicalProcedures/:id -> delete the "id" quirurgicalProcedure.
     */
    @RequestMapping(value = "/quirurgicalProcedures/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuirurgicalProcedure(@PathVariable Long id) {
        log.debug("REST request to delete QuirurgicalProcedure : {}", id);
        quirurgicalProcedureRepository.delete(id);
        quirurgicalProcedureSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quirurgicalProcedure", id.toString())).build();
    }

    /**
     * SEARCH  /_search/quirurgicalProcedures/:query -> search for the quirurgicalProcedure corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/quirurgicalProcedures/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<QuirurgicalProcedure> searchQuirurgicalProcedures(@PathVariable String query) {
        log.debug("REST request to search QuirurgicalProcedures for query {}", query);
        return StreamSupport
            .stream(quirurgicalProcedureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
