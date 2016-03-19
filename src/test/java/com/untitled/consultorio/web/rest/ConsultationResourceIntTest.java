package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Consultation;
import com.untitled.consultorio.repository.ConsultationRepository;
import com.untitled.consultorio.repository.search.ConsultationSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ConsultationResource REST controller.
 *
 * @see ConsultationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConsultationResourceIntTest {


    private static final LocalDate DEFAULT_CONSULTATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONSULTATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_IDX = "AAAAA";
    private static final String UPDATED_IDX = "BBBBB";
    private static final String DEFAULT_DIFERENTIAL_DIAGNOSTIC = "AAAAA";
    private static final String UPDATED_DIFERENTIAL_DIAGNOSTIC = "BBBBB";

    @Inject
    private ConsultationRepository consultationRepository;

    @Inject
    private ConsultationSearchRepository consultationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConsultationResource consultationResource = new ConsultationResource();
        ReflectionTestUtils.setField(consultationResource, "consultationSearchRepository", consultationSearchRepository);
        ReflectionTestUtils.setField(consultationResource, "consultationRepository", consultationRepository);
        this.restConsultationMockMvc = MockMvcBuilders.standaloneSetup(consultationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        consultation = new Consultation();
        consultation.setConsultationDate(DEFAULT_CONSULTATION_DATE);
        consultation.setIdx(DEFAULT_IDX);
        consultation.setDiferentialDiagnostic(DEFAULT_DIFERENTIAL_DIAGNOSTIC);
    }

    @Test
    @Transactional
    public void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation

        restConsultationMockMvc.perform(post("/api/consultations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consultation)))
                .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultations.get(consultations.size() - 1);
        assertThat(testConsultation.getConsultationDate()).isEqualTo(DEFAULT_CONSULTATION_DATE);
        assertThat(testConsultation.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testConsultation.getDiferentialDiagnostic()).isEqualTo(DEFAULT_DIFERENTIAL_DIAGNOSTIC);
    }

    @Test
    @Transactional
    public void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultations
        restConsultationMockMvc.perform(get("/api/consultations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
                .andExpect(jsonPath("$.[*].consultationDate").value(hasItem(DEFAULT_CONSULTATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
                .andExpect(jsonPath("$.[*].diferentialDiagnostic").value(hasItem(DEFAULT_DIFERENTIAL_DIAGNOSTIC.toString())));
    }

    @Test
    @Transactional
    public void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.consultationDate").value(DEFAULT_CONSULTATION_DATE.toString()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.diferentialDiagnostic").value(DEFAULT_DIFERENTIAL_DIAGNOSTIC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

		int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        consultation.setConsultationDate(UPDATED_CONSULTATION_DATE);
        consultation.setIdx(UPDATED_IDX);
        consultation.setDiferentialDiagnostic(UPDATED_DIFERENTIAL_DIAGNOSTIC);

        restConsultationMockMvc.perform(put("/api/consultations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(consultation)))
                .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultations.get(consultations.size() - 1);
        assertThat(testConsultation.getConsultationDate()).isEqualTo(UPDATED_CONSULTATION_DATE);
        assertThat(testConsultation.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testConsultation.getDiferentialDiagnostic()).isEqualTo(UPDATED_DIFERENTIAL_DIAGNOSTIC);
    }

    @Test
    @Transactional
    public void deleteConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

		int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Get the consultation
        restConsultationMockMvc.perform(delete("/api/consultations/{id}", consultation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
