package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Disease;
import com.untitled.consultorio.repository.DiseaseRepository;
import com.untitled.consultorio.repository.search.DiseaseSearchRepository;

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
 * Test class for the DiseaseResource REST controller.
 *
 * @see DiseaseResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DiseaseResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final Boolean DEFAULT_IS_CONGENITAL = false;
    private static final Boolean UPDATED_IS_CONGENITAL = true;

    private static final Boolean DEFAULT_IS_INFECTIOUS = false;
    private static final Boolean UPDATED_IS_INFECTIOUS = true;

    @Inject
    private DiseaseRepository diseaseRepository;

    @Inject
    private DiseaseSearchRepository diseaseSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDiseaseMockMvc;

    private Disease disease;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiseaseResource diseaseResource = new DiseaseResource();
        ReflectionTestUtils.setField(diseaseResource, "diseaseSearchRepository", diseaseSearchRepository);
        ReflectionTestUtils.setField(diseaseResource, "diseaseRepository", diseaseRepository);
        this.restDiseaseMockMvc = MockMvcBuilders.standaloneSetup(diseaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        disease = new Disease();
        disease.setCode(DEFAULT_CODE);
        disease.setName(DEFAULT_NAME);
        disease.setDescription(DEFAULT_DESCRIPTION);
        disease.setType(DEFAULT_TYPE);
        disease.setIsCongenital(DEFAULT_IS_CONGENITAL);
        disease.setIsInfectious(DEFAULT_IS_INFECTIOUS);
    }

    @Test
    @Transactional
    public void createDisease() throws Exception {
        int databaseSizeBeforeCreate = diseaseRepository.findAll().size();

        // Create the Disease

        restDiseaseMockMvc.perform(post("/api/diseases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disease)))
                .andExpect(status().isCreated());

        // Validate the Disease in the database
        List<Disease> diseases = diseaseRepository.findAll();
        assertThat(diseases).hasSize(databaseSizeBeforeCreate + 1);
        Disease testDisease = diseases.get(diseases.size() - 1);
        assertThat(testDisease.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDisease.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDisease.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDisease.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDisease.getIsCongenital()).isEqualTo(DEFAULT_IS_CONGENITAL);
        assertThat(testDisease.getIsInfectious()).isEqualTo(DEFAULT_IS_INFECTIOUS);
    }

    @Test
    @Transactional
    public void getAllDiseases() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get all the diseases
        restDiseaseMockMvc.perform(get("/api/diseases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(disease.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].isCongenital").value(hasItem(DEFAULT_IS_CONGENITAL.booleanValue())))
                .andExpect(jsonPath("$.[*].isInfectious").value(hasItem(DEFAULT_IS_INFECTIOUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get the disease
        restDiseaseMockMvc.perform(get("/api/diseases/{id}", disease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(disease.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isCongenital").value(DEFAULT_IS_CONGENITAL.booleanValue()))
            .andExpect(jsonPath("$.isInfectious").value(DEFAULT_IS_INFECTIOUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDisease() throws Exception {
        // Get the disease
        restDiseaseMockMvc.perform(get("/api/diseases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

		int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Update the disease
        disease.setCode(UPDATED_CODE);
        disease.setName(UPDATED_NAME);
        disease.setDescription(UPDATED_DESCRIPTION);
        disease.setType(UPDATED_TYPE);
        disease.setIsCongenital(UPDATED_IS_CONGENITAL);
        disease.setIsInfectious(UPDATED_IS_INFECTIOUS);

        restDiseaseMockMvc.perform(put("/api/diseases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(disease)))
                .andExpect(status().isOk());

        // Validate the Disease in the database
        List<Disease> diseases = diseaseRepository.findAll();
        assertThat(diseases).hasSize(databaseSizeBeforeUpdate);
        Disease testDisease = diseases.get(diseases.size() - 1);
        assertThat(testDisease.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDisease.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDisease.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDisease.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDisease.getIsCongenital()).isEqualTo(UPDATED_IS_CONGENITAL);
        assertThat(testDisease.getIsInfectious()).isEqualTo(UPDATED_IS_INFECTIOUS);
    }

    @Test
    @Transactional
    public void deleteDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

		int databaseSizeBeforeDelete = diseaseRepository.findAll().size();

        // Get the disease
        restDiseaseMockMvc.perform(delete("/api/diseases/{id}", disease.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Disease> diseases = diseaseRepository.findAll();
        assertThat(diseases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
