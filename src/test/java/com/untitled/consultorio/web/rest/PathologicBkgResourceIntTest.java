package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.PathologicBkg;
import com.untitled.consultorio.repository.PathologicBkgRepository;
import com.untitled.consultorio.repository.search.PathologicBkgSearchRepository;

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
 * Test class for the PathologicBkgResource REST controller.
 *
 * @see PathologicBkgResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PathologicBkgResourceIntTest {

    private static final String DEFAULT_OBSERVATIONS = "AAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBB";

    @Inject
    private PathologicBkgRepository pathologicBkgRepository;

    @Inject
    private PathologicBkgSearchRepository pathologicBkgSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPathologicBkgMockMvc;

    private PathologicBkg pathologicBkg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PathologicBkgResource pathologicBkgResource = new PathologicBkgResource();
        ReflectionTestUtils.setField(pathologicBkgResource, "pathologicBkgSearchRepository", pathologicBkgSearchRepository);
        ReflectionTestUtils.setField(pathologicBkgResource, "pathologicBkgRepository", pathologicBkgRepository);
        this.restPathologicBkgMockMvc = MockMvcBuilders.standaloneSetup(pathologicBkgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pathologicBkg = new PathologicBkg();
        pathologicBkg.setObservations(DEFAULT_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void createPathologicBkg() throws Exception {
        int databaseSizeBeforeCreate = pathologicBkgRepository.findAll().size();

        // Create the PathologicBkg

        restPathologicBkgMockMvc.perform(post("/api/pathologicBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pathologicBkg)))
                .andExpect(status().isCreated());

        // Validate the PathologicBkg in the database
        List<PathologicBkg> pathologicBkgs = pathologicBkgRepository.findAll();
        assertThat(pathologicBkgs).hasSize(databaseSizeBeforeCreate + 1);
        PathologicBkg testPathologicBkg = pathologicBkgs.get(pathologicBkgs.size() - 1);
        assertThat(testPathologicBkg.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllPathologicBkgs() throws Exception {
        // Initialize the database
        pathologicBkgRepository.saveAndFlush(pathologicBkg);

        // Get all the pathologicBkgs
        restPathologicBkgMockMvc.perform(get("/api/pathologicBkgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pathologicBkg.getId().intValue())))
                .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS.toString())));
    }

    @Test
    @Transactional
    public void getPathologicBkg() throws Exception {
        // Initialize the database
        pathologicBkgRepository.saveAndFlush(pathologicBkg);

        // Get the pathologicBkg
        restPathologicBkgMockMvc.perform(get("/api/pathologicBkgs/{id}", pathologicBkg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pathologicBkg.getId().intValue()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPathologicBkg() throws Exception {
        // Get the pathologicBkg
        restPathologicBkgMockMvc.perform(get("/api/pathologicBkgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePathologicBkg() throws Exception {
        // Initialize the database
        pathologicBkgRepository.saveAndFlush(pathologicBkg);

		int databaseSizeBeforeUpdate = pathologicBkgRepository.findAll().size();

        // Update the pathologicBkg
        pathologicBkg.setObservations(UPDATED_OBSERVATIONS);

        restPathologicBkgMockMvc.perform(put("/api/pathologicBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pathologicBkg)))
                .andExpect(status().isOk());

        // Validate the PathologicBkg in the database
        List<PathologicBkg> pathologicBkgs = pathologicBkgRepository.findAll();
        assertThat(pathologicBkgs).hasSize(databaseSizeBeforeUpdate);
        PathologicBkg testPathologicBkg = pathologicBkgs.get(pathologicBkgs.size() - 1);
        assertThat(testPathologicBkg.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void deletePathologicBkg() throws Exception {
        // Initialize the database
        pathologicBkgRepository.saveAndFlush(pathologicBkg);

		int databaseSizeBeforeDelete = pathologicBkgRepository.findAll().size();

        // Get the pathologicBkg
        restPathologicBkgMockMvc.perform(delete("/api/pathologicBkgs/{id}", pathologicBkg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PathologicBkg> pathologicBkgs = pathologicBkgRepository.findAll();
        assertThat(pathologicBkgs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
