package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.HeredoFamilyBkg;
import com.untitled.consultorio.repository.HeredoFamilyBkgRepository;
import com.untitled.consultorio.repository.search.HeredoFamilyBkgSearchRepository;

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
 * Test class for the HeredoFamilyBkgResource REST controller.
 *
 * @see HeredoFamilyBkgResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeredoFamilyBkgResourceIntTest {

    private static final String DEFAULT_OBSERVATION = "AAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBB";

    @Inject
    private HeredoFamilyBkgRepository heredoFamilyBkgRepository;

    @Inject
    private HeredoFamilyBkgSearchRepository heredoFamilyBkgSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeredoFamilyBkgMockMvc;

    private HeredoFamilyBkg heredoFamilyBkg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeredoFamilyBkgResource heredoFamilyBkgResource = new HeredoFamilyBkgResource();
        ReflectionTestUtils.setField(heredoFamilyBkgResource, "heredoFamilyBkgSearchRepository", heredoFamilyBkgSearchRepository);
        ReflectionTestUtils.setField(heredoFamilyBkgResource, "heredoFamilyBkgRepository", heredoFamilyBkgRepository);
        this.restHeredoFamilyBkgMockMvc = MockMvcBuilders.standaloneSetup(heredoFamilyBkgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heredoFamilyBkg = new HeredoFamilyBkg();
        heredoFamilyBkg.setObservation(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    public void createHeredoFamilyBkg() throws Exception {
        int databaseSizeBeforeCreate = heredoFamilyBkgRepository.findAll().size();

        // Create the HeredoFamilyBkg

        restHeredoFamilyBkgMockMvc.perform(post("/api/heredoFamilyBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heredoFamilyBkg)))
                .andExpect(status().isCreated());

        // Validate the HeredoFamilyBkg in the database
        List<HeredoFamilyBkg> heredoFamilyBkgs = heredoFamilyBkgRepository.findAll();
        assertThat(heredoFamilyBkgs).hasSize(databaseSizeBeforeCreate + 1);
        HeredoFamilyBkg testHeredoFamilyBkg = heredoFamilyBkgs.get(heredoFamilyBkgs.size() - 1);
        assertThat(testHeredoFamilyBkg.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllHeredoFamilyBkgs() throws Exception {
        // Initialize the database
        heredoFamilyBkgRepository.saveAndFlush(heredoFamilyBkg);

        // Get all the heredoFamilyBkgs
        restHeredoFamilyBkgMockMvc.perform(get("/api/heredoFamilyBkgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heredoFamilyBkg.getId().intValue())))
                .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())));
    }

    @Test
    @Transactional
    public void getHeredoFamilyBkg() throws Exception {
        // Initialize the database
        heredoFamilyBkgRepository.saveAndFlush(heredoFamilyBkg);

        // Get the heredoFamilyBkg
        restHeredoFamilyBkgMockMvc.perform(get("/api/heredoFamilyBkgs/{id}", heredoFamilyBkg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heredoFamilyBkg.getId().intValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeredoFamilyBkg() throws Exception {
        // Get the heredoFamilyBkg
        restHeredoFamilyBkgMockMvc.perform(get("/api/heredoFamilyBkgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeredoFamilyBkg() throws Exception {
        // Initialize the database
        heredoFamilyBkgRepository.saveAndFlush(heredoFamilyBkg);

		int databaseSizeBeforeUpdate = heredoFamilyBkgRepository.findAll().size();

        // Update the heredoFamilyBkg
        heredoFamilyBkg.setObservation(UPDATED_OBSERVATION);

        restHeredoFamilyBkgMockMvc.perform(put("/api/heredoFamilyBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heredoFamilyBkg)))
                .andExpect(status().isOk());

        // Validate the HeredoFamilyBkg in the database
        List<HeredoFamilyBkg> heredoFamilyBkgs = heredoFamilyBkgRepository.findAll();
        assertThat(heredoFamilyBkgs).hasSize(databaseSizeBeforeUpdate);
        HeredoFamilyBkg testHeredoFamilyBkg = heredoFamilyBkgs.get(heredoFamilyBkgs.size() - 1);
        assertThat(testHeredoFamilyBkg.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void deleteHeredoFamilyBkg() throws Exception {
        // Initialize the database
        heredoFamilyBkgRepository.saveAndFlush(heredoFamilyBkg);

		int databaseSizeBeforeDelete = heredoFamilyBkgRepository.findAll().size();

        // Get the heredoFamilyBkg
        restHeredoFamilyBkgMockMvc.perform(delete("/api/heredoFamilyBkgs/{id}", heredoFamilyBkg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeredoFamilyBkg> heredoFamilyBkgs = heredoFamilyBkgRepository.findAll();
        assertThat(heredoFamilyBkgs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
