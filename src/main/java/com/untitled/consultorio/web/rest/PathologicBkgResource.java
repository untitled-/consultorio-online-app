package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.PathologicBkg;
import com.untitled.consultorio.repository.PathologicBkgRepository;
import com.untitled.consultorio.repository.search.PathologicBkgSearchRepository;
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
 * REST controller for managing PathologicBkg.
 */
@RestController
@RequestMapping("/api")
public class PathologicBkgResource {

    private final Logger log = LoggerFactory.getLogger(PathologicBkgResource.class);
        
    @Inject
    private PathologicBkgRepository pathologicBkgRepository;
    
    @Inject
    private PathologicBkgSearchRepository pathologicBkgSearchRepository;
    
    /**
     * POST  /pathologicBkgs -> Create a new pathologicBkg.
     */
    @RequestMapping(value = "/pathologicBkgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathologicBkg> createPathologicBkg(@RequestBody PathologicBkg pathologicBkg) throws URISyntaxException {
        log.debug("REST request to save PathologicBkg : {}", pathologicBkg);
        if (pathologicBkg.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pathologicBkg", "idexists", "A new pathologicBkg cannot already have an ID")).body(null);
        }
        PathologicBkg result = pathologicBkgRepository.save(pathologicBkg);
        pathologicBkgSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pathologicBkgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pathologicBkg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pathologicBkgs -> Updates an existing pathologicBkg.
     */
    @RequestMapping(value = "/pathologicBkgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathologicBkg> updatePathologicBkg(@RequestBody PathologicBkg pathologicBkg) throws URISyntaxException {
        log.debug("REST request to update PathologicBkg : {}", pathologicBkg);
        if (pathologicBkg.getId() == null) {
            return createPathologicBkg(pathologicBkg);
        }
        PathologicBkg result = pathologicBkgRepository.save(pathologicBkg);
        pathologicBkgSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pathologicBkg", pathologicBkg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pathologicBkgs -> get all the pathologicBkgs.
     */
    @RequestMapping(value = "/pathologicBkgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PathologicBkg> getAllPathologicBkgs(@RequestParam(required = false) String filter) {
        if ("patients-is-null".equals(filter)) {
            log.debug("REST request to get all PathologicBkgs where patients is null");
            return StreamSupport
                .stream(pathologicBkgRepository.findAll().spliterator(), false)
                .filter(pathologicBkg -> pathologicBkg.getPatients() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PathologicBkgs");
        return pathologicBkgRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /pathologicBkgs/:id -> get the "id" pathologicBkg.
     */
    @RequestMapping(value = "/pathologicBkgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PathologicBkg> getPathologicBkg(@PathVariable Long id) {
        log.debug("REST request to get PathologicBkg : {}", id);
        PathologicBkg pathologicBkg = pathologicBkgRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(pathologicBkg)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pathologicBkgs/:id -> delete the "id" pathologicBkg.
     */
    @RequestMapping(value = "/pathologicBkgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePathologicBkg(@PathVariable Long id) {
        log.debug("REST request to delete PathologicBkg : {}", id);
        pathologicBkgRepository.delete(id);
        pathologicBkgSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pathologicBkg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pathologicBkgs/:query -> search for the pathologicBkg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pathologicBkgs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PathologicBkg> searchPathologicBkgs(@PathVariable String query) {
        log.debug("REST request to search PathologicBkgs for query {}", query);
        return StreamSupport
            .stream(pathologicBkgSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
