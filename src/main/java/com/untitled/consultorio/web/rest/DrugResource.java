package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Drug;
import com.untitled.consultorio.repository.DrugRepository;
import com.untitled.consultorio.repository.search.DrugSearchRepository;
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
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);
        
    @Inject
    private DrugRepository drugRepository;
    
    @Inject
    private DrugSearchRepository drugSearchRepository;
    
    /**
     * POST  /drugs -> Create a new drug.
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drug", "idexists", "A new drug cannot already have an ID")).body(null);
        }
        Drug result = drugRepository.save(drug);
        drugSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drug", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs -> Updates an existing drug.
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            return createDrug(drug);
        }
        Drug result = drugRepository.save(drug);
        drugSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drug", drug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs -> get all the drugs.
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drug> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugRepository.findAll();
            }

    /**
     * GET  /drugs/:id -> get the "id" drug.
     */
    @RequestMapping(value = "/drugs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Drug drug = drugRepository.findOne(id);
        return Optional.ofNullable(drug)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drugs/:id -> delete the "id" drug.
     */
    @RequestMapping(value = "/drugs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugRepository.delete(id);
        drugSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drug", id.toString())).build();
    }

    /**
     * SEARCH  /_search/drugs/:query -> search for the drug corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/drugs/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drug> searchDrugs(@PathVariable String query) {
        log.debug("REST request to search Drugs for query {}", query);
        return StreamSupport
            .stream(drugSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
