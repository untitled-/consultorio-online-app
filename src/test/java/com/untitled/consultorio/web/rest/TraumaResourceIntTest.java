package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Trauma;
import com.untitled.consultorio.repository.TraumaRepository;
import com.untitled.consultorio.repository.search.TraumaSearchRepository;

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
 * Test class for the TraumaResource REST controller.
 *
 * @see TraumaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TraumaResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private TraumaRepository traumaRepository;

    @Inject
    private TraumaSearchRepository traumaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTraumaMockMvc;

    private Trauma trauma;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraumaResource traumaResource = new TraumaResource();
        ReflectionTestUtils.setField(traumaResource, "traumaSearchRepository", traumaSearchRepository);
        ReflectionTestUtils.setField(traumaResource, "traumaRepository", traumaRepository);
        this.restTraumaMockMvc = MockMvcBuilders.standaloneSetup(traumaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        trauma = new Trauma();
        trauma.setCode(DEFAULT_CODE);
        trauma.setName(DEFAULT_NAME);
        trauma.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTrauma() throws Exception {
        int databaseSizeBeforeCreate = traumaRepository.findAll().size();

        // Create the Trauma

        restTraumaMockMvc.perform(post("/api/traumas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trauma)))
                .andExpect(status().isCreated());

        // Validate the Trauma in the database
        List<Trauma> traumas = traumaRepository.findAll();
        assertThat(traumas).hasSize(databaseSizeBeforeCreate + 1);
        Trauma testTrauma = traumas.get(traumas.size() - 1);
        assertThat(testTrauma.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrauma.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrauma.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTraumas() throws Exception {
        // Initialize the database
        traumaRepository.saveAndFlush(trauma);

        // Get all the traumas
        restTraumaMockMvc.perform(get("/api/traumas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(trauma.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTrauma() throws Exception {
        // Initialize the database
        traumaRepository.saveAndFlush(trauma);

        // Get the trauma
        restTraumaMockMvc.perform(get("/api/traumas/{id}", trauma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(trauma.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrauma() throws Exception {
        // Get the trauma
        restTraumaMockMvc.perform(get("/api/traumas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrauma() throws Exception {
        // Initialize the database
        traumaRepository.saveAndFlush(trauma);

		int databaseSizeBeforeUpdate = traumaRepository.findAll().size();

        // Update the trauma
        trauma.setCode(UPDATED_CODE);
        trauma.setName(UPDATED_NAME);
        trauma.setDescription(UPDATED_DESCRIPTION);

        restTraumaMockMvc.perform(put("/api/traumas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(trauma)))
                .andExpect(status().isOk());

        // Validate the Trauma in the database
        List<Trauma> traumas = traumaRepository.findAll();
        assertThat(traumas).hasSize(databaseSizeBeforeUpdate);
        Trauma testTrauma = traumas.get(traumas.size() - 1);
        assertThat(testTrauma.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrauma.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrauma.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTrauma() throws Exception {
        // Initialize the database
        traumaRepository.saveAndFlush(trauma);

		int databaseSizeBeforeDelete = traumaRepository.findAll().size();

        // Get the trauma
        restTraumaMockMvc.perform(delete("/api/traumas/{id}", trauma.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Trauma> traumas = traumaRepository.findAll();
        assertThat(traumas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
