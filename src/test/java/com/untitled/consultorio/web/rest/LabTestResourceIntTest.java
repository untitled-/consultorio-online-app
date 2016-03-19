package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.LabTest;
import com.untitled.consultorio.repository.LabTestRepository;
import com.untitled.consultorio.repository.search.LabTestSearchRepository;

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
 * Test class for the LabTestResource REST controller.
 *
 * @see LabTestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LabTestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private LabTestRepository labTestRepository;

    @Inject
    private LabTestSearchRepository labTestSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLabTestMockMvc;

    private LabTest labTest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LabTestResource labTestResource = new LabTestResource();
        ReflectionTestUtils.setField(labTestResource, "labTestSearchRepository", labTestSearchRepository);
        ReflectionTestUtils.setField(labTestResource, "labTestRepository", labTestRepository);
        this.restLabTestMockMvc = MockMvcBuilders.standaloneSetup(labTestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        labTest = new LabTest();
        labTest.setName(DEFAULT_NAME);
        labTest.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLabTest() throws Exception {
        int databaseSizeBeforeCreate = labTestRepository.findAll().size();

        // Create the LabTest

        restLabTestMockMvc.perform(post("/api/labTests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(labTest)))
                .andExpect(status().isCreated());

        // Validate the LabTest in the database
        List<LabTest> labTests = labTestRepository.findAll();
        assertThat(labTests).hasSize(databaseSizeBeforeCreate + 1);
        LabTest testLabTest = labTests.get(labTests.size() - 1);
        assertThat(testLabTest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLabTest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLabTests() throws Exception {
        // Initialize the database
        labTestRepository.saveAndFlush(labTest);

        // Get all the labTests
        restLabTestMockMvc.perform(get("/api/labTests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(labTest.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLabTest() throws Exception {
        // Initialize the database
        labTestRepository.saveAndFlush(labTest);

        // Get the labTest
        restLabTestMockMvc.perform(get("/api/labTests/{id}", labTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(labTest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLabTest() throws Exception {
        // Get the labTest
        restLabTestMockMvc.perform(get("/api/labTests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLabTest() throws Exception {
        // Initialize the database
        labTestRepository.saveAndFlush(labTest);

		int databaseSizeBeforeUpdate = labTestRepository.findAll().size();

        // Update the labTest
        labTest.setName(UPDATED_NAME);
        labTest.setDescription(UPDATED_DESCRIPTION);

        restLabTestMockMvc.perform(put("/api/labTests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(labTest)))
                .andExpect(status().isOk());

        // Validate the LabTest in the database
        List<LabTest> labTests = labTestRepository.findAll();
        assertThat(labTests).hasSize(databaseSizeBeforeUpdate);
        LabTest testLabTest = labTests.get(labTests.size() - 1);
        assertThat(testLabTest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLabTest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteLabTest() throws Exception {
        // Initialize the database
        labTestRepository.saveAndFlush(labTest);

		int databaseSizeBeforeDelete = labTestRepository.findAll().size();

        // Get the labTest
        restLabTestMockMvc.perform(delete("/api/labTests/{id}", labTest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LabTest> labTests = labTestRepository.findAll();
        assertThat(labTests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
