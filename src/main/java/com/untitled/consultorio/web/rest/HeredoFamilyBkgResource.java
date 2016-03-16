package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.HeredoFamilyBkg;
import com.untitled.consultorio.repository.HeredoFamilyBkgRepository;
import com.untitled.consultorio.repository.search.HeredoFamilyBkgSearchRepository;
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
 * REST controller for managing HeredoFamilyBkg.
 */
@RestController
@RequestMapping("/api")
public class HeredoFamilyBkgResource {

    private final Logger log = LoggerFactory.getLogger(HeredoFamilyBkgResource.class);
        
    @Inject
    private HeredoFamilyBkgRepository heredoFamilyBkgRepository;
    
    @Inject
    private HeredoFamilyBkgSearchRepository heredoFamilyBkgSearchRepository;
    
    /**
     * POST  /heredoFamilyBkgs -> Create a new heredoFamilyBkg.
     */
    @RequestMapping(value = "/heredoFamilyBkgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeredoFamilyBkg> createHeredoFamilyBkg(@RequestBody HeredoFamilyBkg heredoFamilyBkg) throws URISyntaxException {
        log.debug("REST request to save HeredoFamilyBkg : {}", heredoFamilyBkg);
        if (heredoFamilyBkg.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("heredoFamilyBkg", "idexists", "A new heredoFamilyBkg cannot already have an ID")).body(null);
        }
        HeredoFamilyBkg result = heredoFamilyBkgRepository.save(heredoFamilyBkg);
        heredoFamilyBkgSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/heredoFamilyBkgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("heredoFamilyBkg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /heredoFamilyBkgs -> Updates an existing heredoFamilyBkg.
     */
    @RequestMapping(value = "/heredoFamilyBkgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeredoFamilyBkg> updateHeredoFamilyBkg(@RequestBody HeredoFamilyBkg heredoFamilyBkg) throws URISyntaxException {
        log.debug("REST request to update HeredoFamilyBkg : {}", heredoFamilyBkg);
        if (heredoFamilyBkg.getId() == null) {
            return createHeredoFamilyBkg(heredoFamilyBkg);
        }
        HeredoFamilyBkg result = heredoFamilyBkgRepository.save(heredoFamilyBkg);
        heredoFamilyBkgSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("heredoFamilyBkg", heredoFamilyBkg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /heredoFamilyBkgs -> get all the heredoFamilyBkgs.
     */
    @RequestMapping(value = "/heredoFamilyBkgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HeredoFamilyBkg> getAllHeredoFamilyBkgs(@RequestParam(required = false) String filter) {
        if ("patients-is-null".equals(filter)) {
            log.debug("REST request to get all HeredoFamilyBkgs where patients is null");
            return StreamSupport
                .stream(heredoFamilyBkgRepository.findAll().spliterator(), false)
                .filter(heredoFamilyBkg -> heredoFamilyBkg.getPatients() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all HeredoFamilyBkgs");
        return heredoFamilyBkgRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /heredoFamilyBkgs/:id -> get the "id" heredoFamilyBkg.
     */
    @RequestMapping(value = "/heredoFamilyBkgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HeredoFamilyBkg> getHeredoFamilyBkg(@PathVariable Long id) {
        log.debug("REST request to get HeredoFamilyBkg : {}", id);
        HeredoFamilyBkg heredoFamilyBkg = heredoFamilyBkgRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(heredoFamilyBkg)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /heredoFamilyBkgs/:id -> delete the "id" heredoFamilyBkg.
     */
    @RequestMapping(value = "/heredoFamilyBkgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHeredoFamilyBkg(@PathVariable Long id) {
        log.debug("REST request to delete HeredoFamilyBkg : {}", id);
        heredoFamilyBkgRepository.delete(id);
        heredoFamilyBkgSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("heredoFamilyBkg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/heredoFamilyBkgs/:query -> search for the heredoFamilyBkg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/heredoFamilyBkgs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HeredoFamilyBkg> searchHeredoFamilyBkgs(@PathVariable String query) {
        log.debug("REST request to search HeredoFamilyBkgs for query {}", query);
        return StreamSupport
            .stream(heredoFamilyBkgSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
