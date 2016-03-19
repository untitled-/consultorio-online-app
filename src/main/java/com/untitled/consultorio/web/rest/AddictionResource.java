package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Addiction;
import com.untitled.consultorio.repository.AddictionRepository;
import com.untitled.consultorio.repository.search.AddictionSearchRepository;
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
 * REST controller for managing Addiction.
 */
@RestController
@RequestMapping("/api")
public class AddictionResource {

    private final Logger log = LoggerFactory.getLogger(AddictionResource.class);
        
    @Inject
    private AddictionRepository addictionRepository;
    
    @Inject
    private AddictionSearchRepository addictionSearchRepository;
    
    /**
     * POST  /addictions -> Create a new addiction.
     */
    @RequestMapping(value = "/addictions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addiction> createAddiction(@RequestBody Addiction addiction) throws URISyntaxException {
        log.debug("REST request to save Addiction : {}", addiction);
        if (addiction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("addiction", "idexists", "A new addiction cannot already have an ID")).body(null);
        }
        Addiction result = addictionRepository.save(addiction);
        addictionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/addictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("addiction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addictions -> Updates an existing addiction.
     */
    @RequestMapping(value = "/addictions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addiction> updateAddiction(@RequestBody Addiction addiction) throws URISyntaxException {
        log.debug("REST request to update Addiction : {}", addiction);
        if (addiction.getId() == null) {
            return createAddiction(addiction);
        }
        Addiction result = addictionRepository.save(addiction);
        addictionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("addiction", addiction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addictions -> get all the addictions.
     */
    @RequestMapping(value = "/addictions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Addiction> getAllAddictions() {
        log.debug("REST request to get all Addictions");
        return addictionRepository.findAll();
            }

    /**
     * GET  /addictions/:id -> get the "id" addiction.
     */
    @RequestMapping(value = "/addictions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Addiction> getAddiction(@PathVariable Long id) {
        log.debug("REST request to get Addiction : {}", id);
        Addiction addiction = addictionRepository.findOne(id);
        return Optional.ofNullable(addiction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /addictions/:id -> delete the "id" addiction.
     */
    @RequestMapping(value = "/addictions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAddiction(@PathVariable Long id) {
        log.debug("REST request to delete Addiction : {}", id);
        addictionRepository.delete(id);
        addictionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("addiction", id.toString())).build();
    }

    /**
     * SEARCH  /_search/addictions/:query -> search for the addiction corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/addictions/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Addiction> searchAddictions(@PathVariable String query) {
        log.debug("REST request to search Addictions for query {}", query);
        return StreamSupport
            .stream(addictionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
