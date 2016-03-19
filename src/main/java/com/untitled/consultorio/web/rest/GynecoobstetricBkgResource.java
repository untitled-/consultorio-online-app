package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.GynecoobstetricBkg;
import com.untitled.consultorio.repository.GynecoobstetricBkgRepository;
import com.untitled.consultorio.repository.search.GynecoobstetricBkgSearchRepository;
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
 * REST controller for managing GynecoobstetricBkg.
 */
@RestController
@RequestMapping("/api")
public class GynecoobstetricBkgResource {

    private final Logger log = LoggerFactory.getLogger(GynecoobstetricBkgResource.class);
        
    @Inject
    private GynecoobstetricBkgRepository gynecoobstetricBkgRepository;
    
    @Inject
    private GynecoobstetricBkgSearchRepository gynecoobstetricBkgSearchRepository;
    
    /**
     * POST  /gynecoobstetricBkgs -> Create a new gynecoobstetricBkg.
     */
    @RequestMapping(value = "/gynecoobstetricBkgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GynecoobstetricBkg> createGynecoobstetricBkg(@RequestBody GynecoobstetricBkg gynecoobstetricBkg) throws URISyntaxException {
        log.debug("REST request to save GynecoobstetricBkg : {}", gynecoobstetricBkg);
        if (gynecoobstetricBkg.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gynecoobstetricBkg", "idexists", "A new gynecoobstetricBkg cannot already have an ID")).body(null);
        }
        GynecoobstetricBkg result = gynecoobstetricBkgRepository.save(gynecoobstetricBkg);
        gynecoobstetricBkgSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gynecoobstetricBkgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gynecoobstetricBkg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gynecoobstetricBkgs -> Updates an existing gynecoobstetricBkg.
     */
    @RequestMapping(value = "/gynecoobstetricBkgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GynecoobstetricBkg> updateGynecoobstetricBkg(@RequestBody GynecoobstetricBkg gynecoobstetricBkg) throws URISyntaxException {
        log.debug("REST request to update GynecoobstetricBkg : {}", gynecoobstetricBkg);
        if (gynecoobstetricBkg.getId() == null) {
            return createGynecoobstetricBkg(gynecoobstetricBkg);
        }
        GynecoobstetricBkg result = gynecoobstetricBkgRepository.save(gynecoobstetricBkg);
        gynecoobstetricBkgSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gynecoobstetricBkg", gynecoobstetricBkg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gynecoobstetricBkgs -> get all the gynecoobstetricBkgs.
     */
    @RequestMapping(value = "/gynecoobstetricBkgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GynecoobstetricBkg> getAllGynecoobstetricBkgs() {
        log.debug("REST request to get all GynecoobstetricBkgs");
        return gynecoobstetricBkgRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /gynecoobstetricBkgs/:id -> get the "id" gynecoobstetricBkg.
     */
    @RequestMapping(value = "/gynecoobstetricBkgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GynecoobstetricBkg> getGynecoobstetricBkg(@PathVariable Long id) {
        log.debug("REST request to get GynecoobstetricBkg : {}", id);
        GynecoobstetricBkg gynecoobstetricBkg = gynecoobstetricBkgRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(gynecoobstetricBkg)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gynecoobstetricBkgs/:id -> delete the "id" gynecoobstetricBkg.
     */
    @RequestMapping(value = "/gynecoobstetricBkgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGynecoobstetricBkg(@PathVariable Long id) {
        log.debug("REST request to delete GynecoobstetricBkg : {}", id);
        gynecoobstetricBkgRepository.delete(id);
        gynecoobstetricBkgSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gynecoobstetricBkg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/gynecoobstetricBkgs/:query -> search for the gynecoobstetricBkg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/gynecoobstetricBkgs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GynecoobstetricBkg> searchGynecoobstetricBkgs(@PathVariable String query) {
        log.debug("REST request to search GynecoobstetricBkgs for query {}", query);
        return StreamSupport
            .stream(gynecoobstetricBkgSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
