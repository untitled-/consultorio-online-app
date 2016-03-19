package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Contraceptives;
import com.untitled.consultorio.repository.ContraceptivesRepository;
import com.untitled.consultorio.repository.search.ContraceptivesSearchRepository;

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
 * Test class for the ContraceptivesResource REST controller.
 *
 * @see ContraceptivesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContraceptivesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ContraceptivesRepository contraceptivesRepository;

    @Inject
    private ContraceptivesSearchRepository contraceptivesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContraceptivesMockMvc;

    private Contraceptives contraceptives;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContraceptivesResource contraceptivesResource = new ContraceptivesResource();
        ReflectionTestUtils.setField(contraceptivesResource, "contraceptivesSearchRepository", contraceptivesSearchRepository);
        ReflectionTestUtils.setField(contraceptivesResource, "contraceptivesRepository", contraceptivesRepository);
        this.restContraceptivesMockMvc = MockMvcBuilders.standaloneSetup(contraceptivesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contraceptives = new Contraceptives();
        contraceptives.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContraceptives() throws Exception {
        int databaseSizeBeforeCreate = contraceptivesRepository.findAll().size();

        // Create the Contraceptives

        restContraceptivesMockMvc.perform(post("/api/contraceptivess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contraceptives)))
                .andExpect(status().isCreated());

        // Validate the Contraceptives in the database
        List<Contraceptives> contraceptivess = contraceptivesRepository.findAll();
        assertThat(contraceptivess).hasSize(databaseSizeBeforeCreate + 1);
        Contraceptives testContraceptives = contraceptivess.get(contraceptivess.size() - 1);
        assertThat(testContraceptives.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllContraceptivess() throws Exception {
        // Initialize the database
        contraceptivesRepository.saveAndFlush(contraceptives);

        // Get all the contraceptivess
        restContraceptivesMockMvc.perform(get("/api/contraceptivess?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contraceptives.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getContraceptives() throws Exception {
        // Initialize the database
        contraceptivesRepository.saveAndFlush(contraceptives);

        // Get the contraceptives
        restContraceptivesMockMvc.perform(get("/api/contraceptivess/{id}", contraceptives.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contraceptives.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContraceptives() throws Exception {
        // Get the contraceptives
        restContraceptivesMockMvc.perform(get("/api/contraceptivess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContraceptives() throws Exception {
        // Initialize the database
        contraceptivesRepository.saveAndFlush(contraceptives);

		int databaseSizeBeforeUpdate = contraceptivesRepository.findAll().size();

        // Update the contraceptives
        contraceptives.setName(UPDATED_NAME);

        restContraceptivesMockMvc.perform(put("/api/contraceptivess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contraceptives)))
                .andExpect(status().isOk());

        // Validate the Contraceptives in the database
        List<Contraceptives> contraceptivess = contraceptivesRepository.findAll();
        assertThat(contraceptivess).hasSize(databaseSizeBeforeUpdate);
        Contraceptives testContraceptives = contraceptivess.get(contraceptivess.size() - 1);
        assertThat(testContraceptives.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteContraceptives() throws Exception {
        // Initialize the database
        contraceptivesRepository.saveAndFlush(contraceptives);

		int databaseSizeBeforeDelete = contraceptivesRepository.findAll().size();

        // Get the contraceptives
        restContraceptivesMockMvc.perform(delete("/api/contraceptivess/{id}", contraceptives.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contraceptives> contraceptivess = contraceptivesRepository.findAll();
        assertThat(contraceptivess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
