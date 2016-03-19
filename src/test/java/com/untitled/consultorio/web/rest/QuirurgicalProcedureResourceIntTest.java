package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.QuirurgicalProcedure;
import com.untitled.consultorio.repository.QuirurgicalProcedureRepository;
import com.untitled.consultorio.repository.search.QuirurgicalProcedureSearchRepository;

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
 * Test class for the QuirurgicalProcedureResource REST controller.
 *
 * @see QuirurgicalProcedureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuirurgicalProcedureResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private QuirurgicalProcedureRepository quirurgicalProcedureRepository;

    @Inject
    private QuirurgicalProcedureSearchRepository quirurgicalProcedureSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restQuirurgicalProcedureMockMvc;

    private QuirurgicalProcedure quirurgicalProcedure;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuirurgicalProcedureResource quirurgicalProcedureResource = new QuirurgicalProcedureResource();
        ReflectionTestUtils.setField(quirurgicalProcedureResource, "quirurgicalProcedureSearchRepository", quirurgicalProcedureSearchRepository);
        ReflectionTestUtils.setField(quirurgicalProcedureResource, "quirurgicalProcedureRepository", quirurgicalProcedureRepository);
        this.restQuirurgicalProcedureMockMvc = MockMvcBuilders.standaloneSetup(quirurgicalProcedureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quirurgicalProcedure = new QuirurgicalProcedure();
        quirurgicalProcedure.setCode(DEFAULT_CODE);
        quirurgicalProcedure.setName(DEFAULT_NAME);
        quirurgicalProcedure.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createQuirurgicalProcedure() throws Exception {
        int databaseSizeBeforeCreate = quirurgicalProcedureRepository.findAll().size();

        // Create the QuirurgicalProcedure

        restQuirurgicalProcedureMockMvc.perform(post("/api/quirurgicalProcedures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quirurgicalProcedure)))
                .andExpect(status().isCreated());

        // Validate the QuirurgicalProcedure in the database
        List<QuirurgicalProcedure> quirurgicalProcedures = quirurgicalProcedureRepository.findAll();
        assertThat(quirurgicalProcedures).hasSize(databaseSizeBeforeCreate + 1);
        QuirurgicalProcedure testQuirurgicalProcedure = quirurgicalProcedures.get(quirurgicalProcedures.size() - 1);
        assertThat(testQuirurgicalProcedure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testQuirurgicalProcedure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuirurgicalProcedure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllQuirurgicalProcedures() throws Exception {
        // Initialize the database
        quirurgicalProcedureRepository.saveAndFlush(quirurgicalProcedure);

        // Get all the quirurgicalProcedures
        restQuirurgicalProcedureMockMvc.perform(get("/api/quirurgicalProcedures?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quirurgicalProcedure.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getQuirurgicalProcedure() throws Exception {
        // Initialize the database
        quirurgicalProcedureRepository.saveAndFlush(quirurgicalProcedure);

        // Get the quirurgicalProcedure
        restQuirurgicalProcedureMockMvc.perform(get("/api/quirurgicalProcedures/{id}", quirurgicalProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quirurgicalProcedure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuirurgicalProcedure() throws Exception {
        // Get the quirurgicalProcedure
        restQuirurgicalProcedureMockMvc.perform(get("/api/quirurgicalProcedures/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuirurgicalProcedure() throws Exception {
        // Initialize the database
        quirurgicalProcedureRepository.saveAndFlush(quirurgicalProcedure);

		int databaseSizeBeforeUpdate = quirurgicalProcedureRepository.findAll().size();

        // Update the quirurgicalProcedure
        quirurgicalProcedure.setCode(UPDATED_CODE);
        quirurgicalProcedure.setName(UPDATED_NAME);
        quirurgicalProcedure.setDescription(UPDATED_DESCRIPTION);

        restQuirurgicalProcedureMockMvc.perform(put("/api/quirurgicalProcedures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quirurgicalProcedure)))
                .andExpect(status().isOk());

        // Validate the QuirurgicalProcedure in the database
        List<QuirurgicalProcedure> quirurgicalProcedures = quirurgicalProcedureRepository.findAll();
        assertThat(quirurgicalProcedures).hasSize(databaseSizeBeforeUpdate);
        QuirurgicalProcedure testQuirurgicalProcedure = quirurgicalProcedures.get(quirurgicalProcedures.size() - 1);
        assertThat(testQuirurgicalProcedure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuirurgicalProcedure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuirurgicalProcedure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteQuirurgicalProcedure() throws Exception {
        // Initialize the database
        quirurgicalProcedureRepository.saveAndFlush(quirurgicalProcedure);

		int databaseSizeBeforeDelete = quirurgicalProcedureRepository.findAll().size();

        // Get the quirurgicalProcedure
        restQuirurgicalProcedureMockMvc.perform(delete("/api/quirurgicalProcedures/{id}", quirurgicalProcedure.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<QuirurgicalProcedure> quirurgicalProcedures = quirurgicalProcedureRepository.findAll();
        assertThat(quirurgicalProcedures).hasSize(databaseSizeBeforeDelete - 1);
    }
}
