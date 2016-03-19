package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Symptom;
import com.untitled.consultorio.repository.SymptomRepository;
import com.untitled.consultorio.repository.search.SymptomSearchRepository;

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
 * Test class for the SymptomResource REST controller.
 *
 * @see SymptomResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SymptomResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private SymptomRepository symptomRepository;

    @Inject
    private SymptomSearchRepository symptomSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSymptomMockMvc;

    private Symptom symptom;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SymptomResource symptomResource = new SymptomResource();
        ReflectionTestUtils.setField(symptomResource, "symptomSearchRepository", symptomSearchRepository);
        ReflectionTestUtils.setField(symptomResource, "symptomRepository", symptomRepository);
        this.restSymptomMockMvc = MockMvcBuilders.standaloneSetup(symptomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        symptom = new Symptom();
        symptom.setCode(DEFAULT_CODE);
        symptom.setName(DEFAULT_NAME);
        symptom.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSymptom() throws Exception {
        int databaseSizeBeforeCreate = symptomRepository.findAll().size();

        // Create the Symptom

        restSymptomMockMvc.perform(post("/api/symptoms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(symptom)))
                .andExpect(status().isCreated());

        // Validate the Symptom in the database
        List<Symptom> symptoms = symptomRepository.findAll();
        assertThat(symptoms).hasSize(databaseSizeBeforeCreate + 1);
        Symptom testSymptom = symptoms.get(symptoms.size() - 1);
        assertThat(testSymptom.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSymptom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSymptom.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSymptoms() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get all the symptoms
        restSymptomMockMvc.perform(get("/api/symptoms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(symptom.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

        // Get the symptom
        restSymptomMockMvc.perform(get("/api/symptoms/{id}", symptom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(symptom.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSymptom() throws Exception {
        // Get the symptom
        restSymptomMockMvc.perform(get("/api/symptoms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

		int databaseSizeBeforeUpdate = symptomRepository.findAll().size();

        // Update the symptom
        symptom.setCode(UPDATED_CODE);
        symptom.setName(UPDATED_NAME);
        symptom.setDescription(UPDATED_DESCRIPTION);

        restSymptomMockMvc.perform(put("/api/symptoms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(symptom)))
                .andExpect(status().isOk());

        // Validate the Symptom in the database
        List<Symptom> symptoms = symptomRepository.findAll();
        assertThat(symptoms).hasSize(databaseSizeBeforeUpdate);
        Symptom testSymptom = symptoms.get(symptoms.size() - 1);
        assertThat(testSymptom.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSymptom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSymptom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSymptom() throws Exception {
        // Initialize the database
        symptomRepository.saveAndFlush(symptom);

		int databaseSizeBeforeDelete = symptomRepository.findAll().size();

        // Get the symptom
        restSymptomMockMvc.perform(delete("/api/symptoms/{id}", symptom.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Symptom> symptoms = symptomRepository.findAll();
        assertThat(symptoms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
