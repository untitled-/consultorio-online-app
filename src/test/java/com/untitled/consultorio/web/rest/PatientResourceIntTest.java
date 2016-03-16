package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Patient;
import com.untitled.consultorio.repository.PatientRepository;
import com.untitled.consultorio.repository.search.PatientSearchRepository;

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

import com.untitled.consultorio.domain.enumeration.BloodType;
import com.untitled.consultorio.domain.enumeration.MaritalStatus;
import com.untitled.consultorio.domain.enumeration.Gender;

/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PatientResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_MIDDLE_NAME = "AAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_JOB = "AAAAA";
    private static final String UPDATED_JOB = "BBBBB";
    
    private static final BloodType DEFAULT_BLOOD_TYPE = BloodType.A_POSITIVE;
    private static final BloodType UPDATED_BLOOD_TYPE = BloodType.A_NEGATIVE;
    
    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.COMMON_LAW;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.DIVORCED;
    
    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private PatientSearchRepository patientSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PatientResource patientResource = new PatientResource();
        ReflectionTestUtils.setField(patientResource, "patientSearchRepository", patientSearchRepository);
        ReflectionTestUtils.setField(patientResource, "patientRepository", patientRepository);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        patient = new Patient();
        patient.setFirstName(DEFAULT_FIRST_NAME);
        patient.setMiddleName(DEFAULT_MIDDLE_NAME);
        patient.setLastName(DEFAULT_LAST_NAME);
        patient.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        patient.setJob(DEFAULT_JOB);
        patient.setBloodType(DEFAULT_BLOOD_TYPE);
        patient.setMaritalStatus(DEFAULT_MARITAL_STATUS);
        patient.setGender(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient

        restPatientMockMvc.perform(post("/api/patients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(patient)))
                .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patients.get(patients.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPatient.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPatient.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPatient.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testPatient.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patients
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB.toString())))
                .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())))
                .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB.toString()))
            .andExpect(jsonPath("$.bloodType").value(DEFAULT_BLOOD_TYPE.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

		int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        patient.setFirstName(UPDATED_FIRST_NAME);
        patient.setMiddleName(UPDATED_MIDDLE_NAME);
        patient.setLastName(UPDATED_LAST_NAME);
        patient.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        patient.setJob(UPDATED_JOB);
        patient.setBloodType(UPDATED_BLOOD_TYPE);
        patient.setMaritalStatus(UPDATED_MARITAL_STATUS);
        patient.setGender(UPDATED_GENDER);

        restPatientMockMvc.perform(put("/api/patients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(patient)))
                .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patients.get(patients.size() - 1);
        assertThat(testPatient.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPatient.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPatient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPatient.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPatient.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testPatient.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

		int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Get the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patients = patientRepository.findAll();
        assertThat(patients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
