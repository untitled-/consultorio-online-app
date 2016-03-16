package com.untitled.consultorio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.untitled.consultorio.domain.Contact;
import com.untitled.consultorio.repository.ContactRepository;
import com.untitled.consultorio.repository.search.ContactSearchRepository;
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
 * REST controller for managing Contact.
 */
@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(ContactResource.class);
        
    @Inject
    private ContactRepository contactRepository;
    
    @Inject
    private ContactSearchRepository contactSearchRepository;
    
    /**
     * POST  /contacts -> Create a new contact.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to save Contact : {}", contact);
        if (contact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contact", "idexists", "A new contact cannot already have an ID")).body(null);
        }
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contact", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacts -> Updates an existing contact.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) throws URISyntaxException {
        log.debug("REST request to update Contact : {}", contact);
        if (contact.getId() == null) {
            return createContact(contact);
        }
        Contact result = contactRepository.save(contact);
        contactSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contact", contact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacts -> get all the contacts.
     */
    @RequestMapping(value = "/contacts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contact> getAllContacts() {
        log.debug("REST request to get all Contacts");
        return contactRepository.findAll();
            }

    /**
     * GET  /contacts/:id -> get the "id" contact.
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {
        log.debug("REST request to get Contact : {}", id);
        Contact contact = contactRepository.findOne(id);
        return Optional.ofNullable(contact)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contacts/:id -> delete the "id" contact.
     */
    @RequestMapping(value = "/contacts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        log.debug("REST request to delete Contact : {}", id);
        contactRepository.delete(id);
        contactSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contact", id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacts/:query -> search for the contact corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contacts/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contact> searchContacts(@PathVariable String query) {
        log.debug("REST request to search Contacts for query {}", query);
        return StreamSupport
            .stream(contactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
