package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.LabTest;
import com.untitled.consultorio.repository.LabTestRepository;
import com.untitled.consultorio.repository.search.LabTestSearchRepository;
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
 * REST controller for managing LabTest.
 */
@RestController
@RequestMapping("/api")
public class LabTestResource {

    private final Logger log = LoggerFactory.getLogger(LabTestResource.class);
        
    @Inject
    private LabTestRepository labTestRepository;
    
    @Inject
    private LabTestSearchRepository labTestSearchRepository;
    
    /**
     * POST  /labTests -> Create a new labTest.
     */
    @RequestMapping(value = "/labTests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LabTest> createLabTest(@RequestBody LabTest labTest) throws URISyntaxException {
        log.debug("REST request to save LabTest : {}", labTest);
        if (labTest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("labTest", "idexists", "A new labTest cannot already have an ID")).body(null);
        }
        LabTest result = labTestRepository.save(labTest);
        labTestSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/labTests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("labTest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /labTests -> Updates an existing labTest.
     */
    @RequestMapping(value = "/labTests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LabTest> updateLabTest(@RequestBody LabTest labTest) throws URISyntaxException {
        log.debug("REST request to update LabTest : {}", labTest);
        if (labTest.getId() == null) {
            return createLabTest(labTest);
        }
        LabTest result = labTestRepository.save(labTest);
        labTestSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("labTest", labTest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /labTests -> get all the labTests.
     */
    @RequestMapping(value = "/labTests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LabTest> getAllLabTests() {
        log.debug("REST request to get all LabTests");
        return labTestRepository.findAll();
            }

    /**
     * GET  /labTests/:id -> get the "id" labTest.
     */
    @RequestMapping(value = "/labTests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LabTest> getLabTest(@PathVariable Long id) {
        log.debug("REST request to get LabTest : {}", id);
        LabTest labTest = labTestRepository.findOne(id);
        return Optional.ofNullable(labTest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /labTests/:id -> delete the "id" labTest.
     */
    @RequestMapping(value = "/labTests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLabTest(@PathVariable Long id) {
        log.debug("REST request to delete LabTest : {}", id);
        labTestRepository.delete(id);
        labTestSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("labTest", id.toString())).build();
    }

    /**
     * SEARCH  /_search/labTests/:query -> search for the labTest corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/labTests/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LabTest> searchLabTests(@PathVariable String query) {
        log.debug("REST request to search LabTests for query {}", query);
        return StreamSupport
            .stream(labTestSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
