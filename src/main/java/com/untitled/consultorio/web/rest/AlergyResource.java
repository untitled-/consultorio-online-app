package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Alergy;
import com.untitled.consultorio.repository.AlergyRepository;
import com.untitled.consultorio.repository.search.AlergySearchRepository;
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
 * REST controller for managing Alergy.
 */
@RestController
@RequestMapping("/api")
public class AlergyResource {

    private final Logger log = LoggerFactory.getLogger(AlergyResource.class);
        
    @Inject
    private AlergyRepository alergyRepository;
    
    @Inject
    private AlergySearchRepository alergySearchRepository;
    
    /**
     * POST  /alergys -> Create a new alergy.
     */
    @RequestMapping(value = "/alergys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alergy> createAlergy(@RequestBody Alergy alergy) throws URISyntaxException {
        log.debug("REST request to save Alergy : {}", alergy);
        if (alergy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alergy", "idexists", "A new alergy cannot already have an ID")).body(null);
        }
        Alergy result = alergyRepository.save(alergy);
        alergySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/alergys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alergy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alergys -> Updates an existing alergy.
     */
    @RequestMapping(value = "/alergys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alergy> updateAlergy(@RequestBody Alergy alergy) throws URISyntaxException {
        log.debug("REST request to update Alergy : {}", alergy);
        if (alergy.getId() == null) {
            return createAlergy(alergy);
        }
        Alergy result = alergyRepository.save(alergy);
        alergySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alergy", alergy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alergys -> get all the alergys.
     */
    @RequestMapping(value = "/alergys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Alergy> getAllAlergys() {
        log.debug("REST request to get all Alergys");
        return alergyRepository.findAll();
            }

    /**
     * GET  /alergys/:id -> get the "id" alergy.
     */
    @RequestMapping(value = "/alergys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alergy> getAlergy(@PathVariable Long id) {
        log.debug("REST request to get Alergy : {}", id);
        Alergy alergy = alergyRepository.findOne(id);
        return Optional.ofNullable(alergy)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alergys/:id -> delete the "id" alergy.
     */
    @RequestMapping(value = "/alergys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlergy(@PathVariable Long id) {
        log.debug("REST request to delete Alergy : {}", id);
        alergyRepository.delete(id);
        alergySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alergy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/alergys/:query -> search for the alergy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/alergys/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Alergy> searchAlergys(@PathVariable String query) {
        log.debug("REST request to search Alergys for query {}", query);
        return StreamSupport
            .stream(alergySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
