package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Contraceptives;
import com.untitled.consultorio.repository.ContraceptivesRepository;
import com.untitled.consultorio.repository.search.ContraceptivesSearchRepository;
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
 * REST controller for managing Contraceptives.
 */
@RestController
@RequestMapping("/api")
public class ContraceptivesResource {

    private final Logger log = LoggerFactory.getLogger(ContraceptivesResource.class);
        
    @Inject
    private ContraceptivesRepository contraceptivesRepository;
    
    @Inject
    private ContraceptivesSearchRepository contraceptivesSearchRepository;
    
    /**
     * POST  /contraceptivess -> Create a new contraceptives.
     */
    @RequestMapping(value = "/contraceptivess",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contraceptives> createContraceptives(@RequestBody Contraceptives contraceptives) throws URISyntaxException {
        log.debug("REST request to save Contraceptives : {}", contraceptives);
        if (contraceptives.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contraceptives", "idexists", "A new contraceptives cannot already have an ID")).body(null);
        }
        Contraceptives result = contraceptivesRepository.save(contraceptives);
        contraceptivesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/contraceptivess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contraceptives", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contraceptivess -> Updates an existing contraceptives.
     */
    @RequestMapping(value = "/contraceptivess",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contraceptives> updateContraceptives(@RequestBody Contraceptives contraceptives) throws URISyntaxException {
        log.debug("REST request to update Contraceptives : {}", contraceptives);
        if (contraceptives.getId() == null) {
            return createContraceptives(contraceptives);
        }
        Contraceptives result = contraceptivesRepository.save(contraceptives);
        contraceptivesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contraceptives", contraceptives.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contraceptivess -> get all the contraceptivess.
     */
    @RequestMapping(value = "/contraceptivess",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contraceptives> getAllContraceptivess() {
        log.debug("REST request to get all Contraceptivess");
        return contraceptivesRepository.findAll();
            }

    /**
     * GET  /contraceptivess/:id -> get the "id" contraceptives.
     */
    @RequestMapping(value = "/contraceptivess/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contraceptives> getContraceptives(@PathVariable Long id) {
        log.debug("REST request to get Contraceptives : {}", id);
        Contraceptives contraceptives = contraceptivesRepository.findOne(id);
        return Optional.ofNullable(contraceptives)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contraceptivess/:id -> delete the "id" contraceptives.
     */
    @RequestMapping(value = "/contraceptivess/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContraceptives(@PathVariable Long id) {
        log.debug("REST request to delete Contraceptives : {}", id);
        contraceptivesRepository.delete(id);
        contraceptivesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contraceptives", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contraceptivess/:query -> search for the contraceptives corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contraceptivess/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contraceptives> searchContraceptivess(@PathVariable String query) {
        log.debug("REST request to search Contraceptivess for query {}", query);
        return StreamSupport
            .stream(contraceptivesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
