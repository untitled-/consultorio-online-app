package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Treatment;
import com.untitled.consultorio.repository.TreatmentRepository;
import com.untitled.consultorio.repository.search.TreatmentSearchRepository;

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
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TreatmentResourceIntTest {

    private static final String DEFAULT_PRESCRIPTION_NUMBER = "AAAAA";
    private static final String UPDATED_PRESCRIPTION_NUMBER = "BBBBB";

    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentSearchRepository treatmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TreatmentResource treatmentResource = new TreatmentResource();
        ReflectionTestUtils.setField(treatmentResource, "treatmentSearchRepository", treatmentSearchRepository);
        ReflectionTestUtils.setField(treatmentResource, "treatmentRepository", treatmentRepository);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        treatment = new Treatment();
        treatment.setPrescriptionNumber(DEFAULT_PRESCRIPTION_NUMBER);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment

        restTreatmentMockMvc.perform(post("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(treatment)))
                .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
        assertThat(testTreatment.getPrescriptionNumber()).isEqualTo(DEFAULT_PRESCRIPTION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatments
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
                .andExpect(jsonPath("$.[*].prescriptionNumber").value(hasItem(DEFAULT_PRESCRIPTION_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.prescriptionNumber").value(DEFAULT_PRESCRIPTION_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

		int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        treatment.setPrescriptionNumber(UPDATED_PRESCRIPTION_NUMBER);

        restTreatmentMockMvc.perform(put("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(treatment)))
                .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
        assertThat(testTreatment.getPrescriptionNumber()).isEqualTo(UPDATED_PRESCRIPTION_NUMBER);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

		int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
