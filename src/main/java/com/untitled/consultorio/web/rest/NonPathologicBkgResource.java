package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.NonPathologicBkg;
import com.untitled.consultorio.repository.NonPathologicBkgRepository;
import com.untitled.consultorio.repository.search.NonPathologicBkgSearchRepository;
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
 * REST controller for managing NonPathologicBkg.
 */
@RestController
@RequestMapping("/api")
public class NonPathologicBkgResource {

    private final Logger log = LoggerFactory.getLogger(NonPathologicBkgResource.class);
        
    @Inject
    private NonPathologicBkgRepository nonPathologicBkgRepository;
    
    @Inject
    private NonPathologicBkgSearchRepository nonPathologicBkgSearchRepository;
    
    /**
     * POST  /nonPathologicBkgs -> Create a new nonPathologicBkg.
     */
    @RequestMapping(value = "/nonPathologicBkgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NonPathologicBkg> createNonPathologicBkg(@RequestBody NonPathologicBkg nonPathologicBkg) throws URISyntaxException {
        log.debug("REST request to save NonPathologicBkg : {}", nonPathologicBkg);
        if (nonPathologicBkg.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("nonPathologicBkg", "idexists", "A new nonPathologicBkg cannot already have an ID")).body(null);
        }
        NonPathologicBkg result = nonPathologicBkgRepository.save(nonPathologicBkg);
        nonPathologicBkgSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/nonPathologicBkgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nonPathologicBkg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nonPathologicBkgs -> Updates an existing nonPathologicBkg.
     */
    @RequestMapping(value = "/nonPathologicBkgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NonPathologicBkg> updateNonPathologicBkg(@RequestBody NonPathologicBkg nonPathologicBkg) throws URISyntaxException {
        log.debug("REST request to update NonPathologicBkg : {}", nonPathologicBkg);
        if (nonPathologicBkg.getId() == null) {
            return createNonPathologicBkg(nonPathologicBkg);
        }
        NonPathologicBkg result = nonPathologicBkgRepository.save(nonPathologicBkg);
        nonPathologicBkgSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nonPathologicBkg", nonPathologicBkg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nonPathologicBkgs -> get all the nonPathologicBkgs.
     */
    @RequestMapping(value = "/nonPathologicBkgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NonPathologicBkg> getAllNonPathologicBkgs(@RequestParam(required = false) String filter) {
        if ("patients-is-null".equals(filter)) {
            log.debug("REST request to get all NonPathologicBkgs where patients is null");
            return StreamSupport
                .stream(nonPathologicBkgRepository.findAll().spliterator(), false)
                .filter(nonPathologicBkg -> nonPathologicBkg.getPatients() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all NonPathologicBkgs");
        return nonPathologicBkgRepository.findAll();
            }

    /**
     * GET  /nonPathologicBkgs/:id -> get the "id" nonPathologicBkg.
     */
    @RequestMapping(value = "/nonPathologicBkgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NonPathologicBkg> getNonPathologicBkg(@PathVariable Long id) {
        log.debug("REST request to get NonPathologicBkg : {}", id);
        NonPathologicBkg nonPathologicBkg = nonPathologicBkgRepository.findOne(id);
        return Optional.ofNullable(nonPathologicBkg)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nonPathologicBkgs/:id -> delete the "id" nonPathologicBkg.
     */
    @RequestMapping(value = "/nonPathologicBkgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNonPathologicBkg(@PathVariable Long id) {
        log.debug("REST request to delete NonPathologicBkg : {}", id);
        nonPathologicBkgRepository.delete(id);
        nonPathologicBkgSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nonPathologicBkg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/nonPathologicBkgs/:query -> search for the nonPathologicBkg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/nonPathologicBkgs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NonPathologicBkg> searchNonPathologicBkgs(@PathVariable String query) {
        log.debug("REST request to search NonPathologicBkgs for query {}", query);
        return StreamSupport
            .stream(nonPathologicBkgSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
