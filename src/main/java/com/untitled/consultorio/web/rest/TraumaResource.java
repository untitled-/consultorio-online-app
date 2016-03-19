package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Trauma;
import com.untitled.consultorio.repository.TraumaRepository;
import com.untitled.consultorio.repository.search.TraumaSearchRepository;
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
 * REST controller for managing Trauma.
 */
@RestController
@RequestMapping("/api")
public class TraumaResource {

    private final Logger log = LoggerFactory.getLogger(TraumaResource.class);
        
    @Inject
    private TraumaRepository traumaRepository;
    
    @Inject
    private TraumaSearchRepository traumaSearchRepository;
    
    /**
     * POST  /traumas -> Create a new trauma.
     */
    @RequestMapping(value = "/traumas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trauma> createTrauma(@RequestBody Trauma trauma) throws URISyntaxException {
        log.debug("REST request to save Trauma : {}", trauma);
        if (trauma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("trauma", "idexists", "A new trauma cannot already have an ID")).body(null);
        }
        Trauma result = traumaRepository.save(trauma);
        traumaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/traumas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trauma", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /traumas -> Updates an existing trauma.
     */
    @RequestMapping(value = "/traumas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trauma> updateTrauma(@RequestBody Trauma trauma) throws URISyntaxException {
        log.debug("REST request to update Trauma : {}", trauma);
        if (trauma.getId() == null) {
            return createTrauma(trauma);
        }
        Trauma result = traumaRepository.save(trauma);
        traumaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trauma", trauma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /traumas -> get all the traumas.
     */
    @RequestMapping(value = "/traumas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Trauma> getAllTraumas() {
        log.debug("REST request to get all Traumas");
        return traumaRepository.findAll();
            }

    /**
     * GET  /traumas/:id -> get the "id" trauma.
     */
    @RequestMapping(value = "/traumas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Trauma> getTrauma(@PathVariable Long id) {
        log.debug("REST request to get Trauma : {}", id);
        Trauma trauma = traumaRepository.findOne(id);
        return Optional.ofNullable(trauma)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /traumas/:id -> delete the "id" trauma.
     */
    @RequestMapping(value = "/traumas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrauma(@PathVariable Long id) {
        log.debug("REST request to delete Trauma : {}", id);
        traumaRepository.delete(id);
        traumaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trauma", id.toString())).build();
    }

    /**
     * SEARCH  /_search/traumas/:query -> search for the trauma corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/traumas/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Trauma> searchTraumas(@PathVariable String query) {
        log.debug("REST request to search Traumas for query {}", query);
        return StreamSupport
            .stream(traumaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
