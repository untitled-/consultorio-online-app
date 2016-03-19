package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Alergy;
import com.untitled.consultorio.repository.AlergyRepository;
import com.untitled.consultorio.repository.search.AlergySearchRepository;

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
 * Test class for the AlergyResource REST controller.
 *
 * @see AlergyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AlergyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private AlergyRepository alergyRepository;

    @Inject
    private AlergySearchRepository alergySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAlergyMockMvc;

    private Alergy alergy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlergyResource alergyResource = new AlergyResource();
        ReflectionTestUtils.setField(alergyResource, "alergySearchRepository", alergySearchRepository);
        ReflectionTestUtils.setField(alergyResource, "alergyRepository", alergyRepository);
        this.restAlergyMockMvc = MockMvcBuilders.standaloneSetup(alergyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        alergy = new Alergy();
        alergy.setCode(DEFAULT_CODE);
        alergy.setName(DEFAULT_NAME);
        alergy.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAlergy() throws Exception {
        int databaseSizeBeforeCreate = alergyRepository.findAll().size();

        // Create the Alergy

        restAlergyMockMvc.perform(post("/api/alergys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alergy)))
                .andExpect(status().isCreated());

        // Validate the Alergy in the database
        List<Alergy> alergys = alergyRepository.findAll();
        assertThat(alergys).hasSize(databaseSizeBeforeCreate + 1);
        Alergy testAlergy = alergys.get(alergys.size() - 1);
        assertThat(testAlergy.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAlergy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlergy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAlergys() throws Exception {
        // Initialize the database
        alergyRepository.saveAndFlush(alergy);

        // Get all the alergys
        restAlergyMockMvc.perform(get("/api/alergys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(alergy.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAlergy() throws Exception {
        // Initialize the database
        alergyRepository.saveAndFlush(alergy);

        // Get the alergy
        restAlergyMockMvc.perform(get("/api/alergys/{id}", alergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(alergy.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlergy() throws Exception {
        // Get the alergy
        restAlergyMockMvc.perform(get("/api/alergys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlergy() throws Exception {
        // Initialize the database
        alergyRepository.saveAndFlush(alergy);

		int databaseSizeBeforeUpdate = alergyRepository.findAll().size();

        // Update the alergy
        alergy.setCode(UPDATED_CODE);
        alergy.setName(UPDATED_NAME);
        alergy.setDescription(UPDATED_DESCRIPTION);

        restAlergyMockMvc.perform(put("/api/alergys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alergy)))
                .andExpect(status().isOk());

        // Validate the Alergy in the database
        List<Alergy> alergys = alergyRepository.findAll();
        assertThat(alergys).hasSize(databaseSizeBeforeUpdate);
        Alergy testAlergy = alergys.get(alergys.size() - 1);
        assertThat(testAlergy.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAlergy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlergy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAlergy() throws Exception {
        // Initialize the database
        alergyRepository.saveAndFlush(alergy);

		int databaseSizeBeforeDelete = alergyRepository.findAll().size();

        // Get the alergy
        restAlergyMockMvc.perform(delete("/api/alergys/{id}", alergy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Alergy> alergys = alergyRepository.findAll();
        assertThat(alergys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
