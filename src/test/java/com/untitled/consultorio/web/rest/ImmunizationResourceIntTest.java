package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Immunization;
import com.untitled.consultorio.repository.ImmunizationRepository;
import com.untitled.consultorio.repository.search.ImmunizationSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ImmunizationResource REST controller.
 *
 * @see ImmunizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImmunizationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ImmunizationRepository immunizationRepository;

    @Inject
    private ImmunizationSearchRepository immunizationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restImmunizationMockMvc;

    private Immunization immunization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImmunizationResource immunizationResource = new ImmunizationResource();
        ReflectionTestUtils.setField(immunizationResource, "immunizationSearchRepository", immunizationSearchRepository);
        ReflectionTestUtils.setField(immunizationResource, "immunizationRepository", immunizationRepository);
        this.restImmunizationMockMvc = MockMvcBuilders.standaloneSetup(immunizationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        immunization = new Immunization();
        immunization.setCode(DEFAULT_CODE);
        immunization.setName(DEFAULT_NAME);
        immunization.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createImmunization() throws Exception {
        int databaseSizeBeforeCreate = immunizationRepository.findAll().size();

        // Create the Immunization

        restImmunizationMockMvc.perform(post("/api/immunizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(immunization)))
                .andExpect(status().isCreated());

        // Validate the Immunization in the database
        List<Immunization> immunizations = immunizationRepository.findAll();
        assertThat(immunizations).hasSize(databaseSizeBeforeCreate + 1);
        Immunization testImmunization = immunizations.get(immunizations.size() - 1);
        assertThat(testImmunization.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testImmunization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImmunization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImmunizations() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizations
        restImmunizationMockMvc.perform(get("/api/immunizations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(immunization.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get the immunization
        restImmunizationMockMvc.perform(get("/api/immunizations/{id}", immunization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(immunization.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImmunization() throws Exception {
        // Get the immunization
        restImmunizationMockMvc.perform(get("/api/immunizations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

		int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();

        // Update the immunization
        immunization.setCode(UPDATED_CODE);
        immunization.setName(UPDATED_NAME);
        immunization.setDescription(UPDATED_DESCRIPTION);

        restImmunizationMockMvc.perform(put("/api/immunizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(immunization)))
                .andExpect(status().isOk());

        // Validate the Immunization in the database
        List<Immunization> immunizations = immunizationRepository.findAll();
        assertThat(immunizations).hasSize(databaseSizeBeforeUpdate);
        Immunization testImmunization = immunizations.get(immunizations.size() - 1);
        assertThat(testImmunization.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testImmunization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImmunization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

		int databaseSizeBeforeDelete = immunizationRepository.findAll().size();

        // Get the immunization
        restImmunizationMockMvc.perform(delete("/api/immunizations/{id}", immunization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Immunization> immunizations = immunizationRepository.findAll();
        assertThat(immunizations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
