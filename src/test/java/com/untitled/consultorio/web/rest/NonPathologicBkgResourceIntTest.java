package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.NonPathologicBkg;
import com.untitled.consultorio.repository.NonPathologicBkgRepository;
import com.untitled.consultorio.repository.search.NonPathologicBkgSearchRepository;

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
 * Test class for the NonPathologicBkgResource REST controller.
 *
 * @see NonPathologicBkgResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NonPathologicBkgResourceIntTest {

    private static final String DEFAULT_HOUSING = "AAAAA";
    private static final String UPDATED_HOUSING = "BBBBB";

    private static final Boolean DEFAULT_HAS_ZOONOSIS = false;
    private static final Boolean UPDATED_HAS_ZOONOSIS = true;
    private static final String DEFAULT_ZOONOSIS_DESC = "AAAAA";
    private static final String UPDATED_ZOONOSIS_DESC = "BBBBB";

    private static final Boolean DEFAULT_IS_OVERCROWDED = false;
    private static final Boolean UPDATED_IS_OVERCROWDED = true;
    private static final String DEFAULT_OVERCROWDING_DESC = "AAAAA";
    private static final String UPDATED_OVERCROWDING_DESC = "BBBBB";

    private static final Boolean DEFAULT_IS_FEEDING_BALANCED = false;
    private static final Boolean UPDATED_IS_FEEDING_BALANCED = true;
    private static final String DEFAULT_FEEDING_DESC = "AAAAA";
    private static final String UPDATED_FEEDING_DESC = "BBBBB";
    private static final String DEFAULT_HYGIENE_DESC = "AAAAA";
    private static final String UPDATED_HYGIENE_DESC = "BBBBB";

    @Inject
    private NonPathologicBkgRepository nonPathologicBkgRepository;

    @Inject
    private NonPathologicBkgSearchRepository nonPathologicBkgSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNonPathologicBkgMockMvc;

    private NonPathologicBkg nonPathologicBkg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NonPathologicBkgResource nonPathologicBkgResource = new NonPathologicBkgResource();
        ReflectionTestUtils.setField(nonPathologicBkgResource, "nonPathologicBkgSearchRepository", nonPathologicBkgSearchRepository);
        ReflectionTestUtils.setField(nonPathologicBkgResource, "nonPathologicBkgRepository", nonPathologicBkgRepository);
        this.restNonPathologicBkgMockMvc = MockMvcBuilders.standaloneSetup(nonPathologicBkgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        nonPathologicBkg = new NonPathologicBkg();
        nonPathologicBkg.setHousing(DEFAULT_HOUSING);
        nonPathologicBkg.setHasZoonosis(DEFAULT_HAS_ZOONOSIS);
        nonPathologicBkg.setZoonosisDesc(DEFAULT_ZOONOSIS_DESC);
        nonPathologicBkg.setIsOvercrowded(DEFAULT_IS_OVERCROWDED);
        nonPathologicBkg.setOvercrowdingDesc(DEFAULT_OVERCROWDING_DESC);
        nonPathologicBkg.setIsFeedingBalanced(DEFAULT_IS_FEEDING_BALANCED);
        nonPathologicBkg.setFeedingDesc(DEFAULT_FEEDING_DESC);
        nonPathologicBkg.setHygieneDesc(DEFAULT_HYGIENE_DESC);
    }

    @Test
    @Transactional
    public void createNonPathologicBkg() throws Exception {
        int databaseSizeBeforeCreate = nonPathologicBkgRepository.findAll().size();

        // Create the NonPathologicBkg

        restNonPathologicBkgMockMvc.perform(post("/api/nonPathologicBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nonPathologicBkg)))
                .andExpect(status().isCreated());

        // Validate the NonPathologicBkg in the database
        List<NonPathologicBkg> nonPathologicBkgs = nonPathologicBkgRepository.findAll();
        assertThat(nonPathologicBkgs).hasSize(databaseSizeBeforeCreate + 1);
        NonPathologicBkg testNonPathologicBkg = nonPathologicBkgs.get(nonPathologicBkgs.size() - 1);
        assertThat(testNonPathologicBkg.getHousing()).isEqualTo(DEFAULT_HOUSING);
        assertThat(testNonPathologicBkg.getHasZoonosis()).isEqualTo(DEFAULT_HAS_ZOONOSIS);
        assertThat(testNonPathologicBkg.getZoonosisDesc()).isEqualTo(DEFAULT_ZOONOSIS_DESC);
        assertThat(testNonPathologicBkg.getIsOvercrowded()).isEqualTo(DEFAULT_IS_OVERCROWDED);
        assertThat(testNonPathologicBkg.getOvercrowdingDesc()).isEqualTo(DEFAULT_OVERCROWDING_DESC);
        assertThat(testNonPathologicBkg.getIsFeedingBalanced()).isEqualTo(DEFAULT_IS_FEEDING_BALANCED);
        assertThat(testNonPathologicBkg.getFeedingDesc()).isEqualTo(DEFAULT_FEEDING_DESC);
        assertThat(testNonPathologicBkg.getHygieneDesc()).isEqualTo(DEFAULT_HYGIENE_DESC);
    }

    @Test
    @Transactional
    public void getAllNonPathologicBkgs() throws Exception {
        // Initialize the database
        nonPathologicBkgRepository.saveAndFlush(nonPathologicBkg);

        // Get all the nonPathologicBkgs
        restNonPathologicBkgMockMvc.perform(get("/api/nonPathologicBkgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nonPathologicBkg.getId().intValue())))
                .andExpect(jsonPath("$.[*].housing").value(hasItem(DEFAULT_HOUSING.toString())))
                .andExpect(jsonPath("$.[*].hasZoonosis").value(hasItem(DEFAULT_HAS_ZOONOSIS.booleanValue())))
                .andExpect(jsonPath("$.[*].zoonosisDesc").value(hasItem(DEFAULT_ZOONOSIS_DESC.toString())))
                .andExpect(jsonPath("$.[*].isOvercrowded").value(hasItem(DEFAULT_IS_OVERCROWDED.booleanValue())))
                .andExpect(jsonPath("$.[*].overcrowdingDesc").value(hasItem(DEFAULT_OVERCROWDING_DESC.toString())))
                .andExpect(jsonPath("$.[*].isFeedingBalanced").value(hasItem(DEFAULT_IS_FEEDING_BALANCED.booleanValue())))
                .andExpect(jsonPath("$.[*].feedingDesc").value(hasItem(DEFAULT_FEEDING_DESC.toString())))
                .andExpect(jsonPath("$.[*].hygieneDesc").value(hasItem(DEFAULT_HYGIENE_DESC.toString())));
    }

    @Test
    @Transactional
    public void getNonPathologicBkg() throws Exception {
        // Initialize the database
        nonPathologicBkgRepository.saveAndFlush(nonPathologicBkg);

        // Get the nonPathologicBkg
        restNonPathologicBkgMockMvc.perform(get("/api/nonPathologicBkgs/{id}", nonPathologicBkg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nonPathologicBkg.getId().intValue()))
            .andExpect(jsonPath("$.housing").value(DEFAULT_HOUSING.toString()))
            .andExpect(jsonPath("$.hasZoonosis").value(DEFAULT_HAS_ZOONOSIS.booleanValue()))
            .andExpect(jsonPath("$.zoonosisDesc").value(DEFAULT_ZOONOSIS_DESC.toString()))
            .andExpect(jsonPath("$.isOvercrowded").value(DEFAULT_IS_OVERCROWDED.booleanValue()))
            .andExpect(jsonPath("$.overcrowdingDesc").value(DEFAULT_OVERCROWDING_DESC.toString()))
            .andExpect(jsonPath("$.isFeedingBalanced").value(DEFAULT_IS_FEEDING_BALANCED.booleanValue()))
            .andExpect(jsonPath("$.feedingDesc").value(DEFAULT_FEEDING_DESC.toString()))
            .andExpect(jsonPath("$.hygieneDesc").value(DEFAULT_HYGIENE_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNonPathologicBkg() throws Exception {
        // Get the nonPathologicBkg
        restNonPathologicBkgMockMvc.perform(get("/api/nonPathologicBkgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonPathologicBkg() throws Exception {
        // Initialize the database
        nonPathologicBkgRepository.saveAndFlush(nonPathologicBkg);

		int databaseSizeBeforeUpdate = nonPathologicBkgRepository.findAll().size();

        // Update the nonPathologicBkg
        nonPathologicBkg.setHousing(UPDATED_HOUSING);
        nonPathologicBkg.setHasZoonosis(UPDATED_HAS_ZOONOSIS);
        nonPathologicBkg.setZoonosisDesc(UPDATED_ZOONOSIS_DESC);
        nonPathologicBkg.setIsOvercrowded(UPDATED_IS_OVERCROWDED);
        nonPathologicBkg.setOvercrowdingDesc(UPDATED_OVERCROWDING_DESC);
        nonPathologicBkg.setIsFeedingBalanced(UPDATED_IS_FEEDING_BALANCED);
        nonPathologicBkg.setFeedingDesc(UPDATED_FEEDING_DESC);
        nonPathologicBkg.setHygieneDesc(UPDATED_HYGIENE_DESC);

        restNonPathologicBkgMockMvc.perform(put("/api/nonPathologicBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nonPathologicBkg)))
                .andExpect(status().isOk());

        // Validate the NonPathologicBkg in the database
        List<NonPathologicBkg> nonPathologicBkgs = nonPathologicBkgRepository.findAll();
        assertThat(nonPathologicBkgs).hasSize(databaseSizeBeforeUpdate);
        NonPathologicBkg testNonPathologicBkg = nonPathologicBkgs.get(nonPathologicBkgs.size() - 1);
        assertThat(testNonPathologicBkg.getHousing()).isEqualTo(UPDATED_HOUSING);
        assertThat(testNonPathologicBkg.getHasZoonosis()).isEqualTo(UPDATED_HAS_ZOONOSIS);
        assertThat(testNonPathologicBkg.getZoonosisDesc()).isEqualTo(UPDATED_ZOONOSIS_DESC);
        assertThat(testNonPathologicBkg.getIsOvercrowded()).isEqualTo(UPDATED_IS_OVERCROWDED);
        assertThat(testNonPathologicBkg.getOvercrowdingDesc()).isEqualTo(UPDATED_OVERCROWDING_DESC);
        assertThat(testNonPathologicBkg.getIsFeedingBalanced()).isEqualTo(UPDATED_IS_FEEDING_BALANCED);
        assertThat(testNonPathologicBkg.getFeedingDesc()).isEqualTo(UPDATED_FEEDING_DESC);
        assertThat(testNonPathologicBkg.getHygieneDesc()).isEqualTo(UPDATED_HYGIENE_DESC);
    }

    @Test
    @Transactional
    public void deleteNonPathologicBkg() throws Exception {
        // Initialize the database
        nonPathologicBkgRepository.saveAndFlush(nonPathologicBkg);

		int databaseSizeBeforeDelete = nonPathologicBkgRepository.findAll().size();

        // Get the nonPathologicBkg
        restNonPathologicBkgMockMvc.perform(delete("/api/nonPathologicBkgs/{id}", nonPathologicBkg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NonPathologicBkg> nonPathologicBkgs = nonPathologicBkgRepository.findAll();
        assertThat(nonPathologicBkgs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
