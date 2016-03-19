package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.GynecoobstetricBkg;
import com.untitled.consultorio.repository.GynecoobstetricBkgRepository;
import com.untitled.consultorio.repository.search.GynecoobstetricBkgSearchRepository;

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
 * Test class for the GynecoobstetricBkgResource REST controller.
 *
 * @see GynecoobstetricBkgResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GynecoobstetricBkgResourceIntTest {


    private static final LocalDate DEFAULT_MENARCHE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MENARCHE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BEGGINING_SEXUAL_LIFE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGGINING_SEXUAL_LIFE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PREGNANCIES_NUMBER = 1;
    private static final Integer UPDATED_PREGNANCIES_NUMBER = 2;

    private static final Integer DEFAULT_MISCARRIAGES_NUMBER = 1;
    private static final Integer UPDATED_MISCARRIAGES_NUMBER = 2;

    private static final Integer DEFAULT_C_SECTIONS_NUMBER = 1;
    private static final Integer UPDATED_C_SECTIONS_NUMBER = 2;
    private static final String DEFAULT_PREGNANCY_DETAILS = "AAAAA";
    private static final String UPDATED_PREGNANCY_DETAILS = "BBBBB";

    private static final LocalDate DEFAULT_LATEST_PAP_TEST = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST_PAP_TEST = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LATEST_PAP_TEST_DETAILS = "AAAAA";
    private static final String UPDATED_LATEST_PAP_TEST_DETAILS = "BBBBB";

    private static final LocalDate DEFAULT_LATEST_MAMMOGRAPHY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST_MAMMOGRAPHY = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LATEST_MAMMOGRAPHY_DETAILS = "AAAAA";
    private static final String UPDATED_LATEST_MAMMOGRAPHY_DETAILS = "BBBBB";

    private static final Boolean DEFAULT_USES_CONTRACEPTIVES = false;
    private static final Boolean UPDATED_USES_CONTRACEPTIVES = true;

    private static final Boolean DEFAULT_HAS_MENOPAUSE = false;
    private static final Boolean UPDATED_HAS_MENOPAUSE = true;

    @Inject
    private GynecoobstetricBkgRepository gynecoobstetricBkgRepository;

    @Inject
    private GynecoobstetricBkgSearchRepository gynecoobstetricBkgSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGynecoobstetricBkgMockMvc;

    private GynecoobstetricBkg gynecoobstetricBkg;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GynecoobstetricBkgResource gynecoobstetricBkgResource = new GynecoobstetricBkgResource();
        ReflectionTestUtils.setField(gynecoobstetricBkgResource, "gynecoobstetricBkgSearchRepository", gynecoobstetricBkgSearchRepository);
        ReflectionTestUtils.setField(gynecoobstetricBkgResource, "gynecoobstetricBkgRepository", gynecoobstetricBkgRepository);
        this.restGynecoobstetricBkgMockMvc = MockMvcBuilders.standaloneSetup(gynecoobstetricBkgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gynecoobstetricBkg = new GynecoobstetricBkg();
        gynecoobstetricBkg.setMenarche(DEFAULT_MENARCHE);
        gynecoobstetricBkg.setBegginingSexualLife(DEFAULT_BEGGINING_SEXUAL_LIFE);
        gynecoobstetricBkg.setPregnanciesNumber(DEFAULT_PREGNANCIES_NUMBER);
        gynecoobstetricBkg.setMiscarriagesNumber(DEFAULT_MISCARRIAGES_NUMBER);
        gynecoobstetricBkg.setcSectionsNumber(DEFAULT_C_SECTIONS_NUMBER);
        gynecoobstetricBkg.setPregnancyDetails(DEFAULT_PREGNANCY_DETAILS);
        gynecoobstetricBkg.setLatestPapTest(DEFAULT_LATEST_PAP_TEST);
        gynecoobstetricBkg.setLatestPapTestDetails(DEFAULT_LATEST_PAP_TEST_DETAILS);
        gynecoobstetricBkg.setLatestMammography(DEFAULT_LATEST_MAMMOGRAPHY);
        gynecoobstetricBkg.setLatestMammographyDetails(DEFAULT_LATEST_MAMMOGRAPHY_DETAILS);
        gynecoobstetricBkg.setUsesContraceptives(DEFAULT_USES_CONTRACEPTIVES);
        gynecoobstetricBkg.setHasMenopause(DEFAULT_HAS_MENOPAUSE);
    }

    @Test
    @Transactional
    public void createGynecoobstetricBkg() throws Exception {
        int databaseSizeBeforeCreate = gynecoobstetricBkgRepository.findAll().size();

        // Create the GynecoobstetricBkg

        restGynecoobstetricBkgMockMvc.perform(post("/api/gynecoobstetricBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gynecoobstetricBkg)))
                .andExpect(status().isCreated());

        // Validate the GynecoobstetricBkg in the database
        List<GynecoobstetricBkg> gynecoobstetricBkgs = gynecoobstetricBkgRepository.findAll();
        assertThat(gynecoobstetricBkgs).hasSize(databaseSizeBeforeCreate + 1);
        GynecoobstetricBkg testGynecoobstetricBkg = gynecoobstetricBkgs.get(gynecoobstetricBkgs.size() - 1);
        assertThat(testGynecoobstetricBkg.getMenarche()).isEqualTo(DEFAULT_MENARCHE);
        assertThat(testGynecoobstetricBkg.getBegginingSexualLife()).isEqualTo(DEFAULT_BEGGINING_SEXUAL_LIFE);
        assertThat(testGynecoobstetricBkg.getPregnanciesNumber()).isEqualTo(DEFAULT_PREGNANCIES_NUMBER);
        assertThat(testGynecoobstetricBkg.getMiscarriagesNumber()).isEqualTo(DEFAULT_MISCARRIAGES_NUMBER);
        assertThat(testGynecoobstetricBkg.getcSectionsNumber()).isEqualTo(DEFAULT_C_SECTIONS_NUMBER);
        assertThat(testGynecoobstetricBkg.getPregnancyDetails()).isEqualTo(DEFAULT_PREGNANCY_DETAILS);
        assertThat(testGynecoobstetricBkg.getLatestPapTest()).isEqualTo(DEFAULT_LATEST_PAP_TEST);
        assertThat(testGynecoobstetricBkg.getLatestPapTestDetails()).isEqualTo(DEFAULT_LATEST_PAP_TEST_DETAILS);
        assertThat(testGynecoobstetricBkg.getLatestMammography()).isEqualTo(DEFAULT_LATEST_MAMMOGRAPHY);
        assertThat(testGynecoobstetricBkg.getLatestMammographyDetails()).isEqualTo(DEFAULT_LATEST_MAMMOGRAPHY_DETAILS);
        assertThat(testGynecoobstetricBkg.getUsesContraceptives()).isEqualTo(DEFAULT_USES_CONTRACEPTIVES);
        assertThat(testGynecoobstetricBkg.getHasMenopause()).isEqualTo(DEFAULT_HAS_MENOPAUSE);
    }

    @Test
    @Transactional
    public void getAllGynecoobstetricBkgs() throws Exception {
        // Initialize the database
        gynecoobstetricBkgRepository.saveAndFlush(gynecoobstetricBkg);

        // Get all the gynecoobstetricBkgs
        restGynecoobstetricBkgMockMvc.perform(get("/api/gynecoobstetricBkgs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gynecoobstetricBkg.getId().intValue())))
                .andExpect(jsonPath("$.[*].menarche").value(hasItem(DEFAULT_MENARCHE.toString())))
                .andExpect(jsonPath("$.[*].begginingSexualLife").value(hasItem(DEFAULT_BEGGINING_SEXUAL_LIFE.toString())))
                .andExpect(jsonPath("$.[*].pregnanciesNumber").value(hasItem(DEFAULT_PREGNANCIES_NUMBER)))
                .andExpect(jsonPath("$.[*].miscarriagesNumber").value(hasItem(DEFAULT_MISCARRIAGES_NUMBER)))
                .andExpect(jsonPath("$.[*].cSectionsNumber").value(hasItem(DEFAULT_C_SECTIONS_NUMBER)))
                .andExpect(jsonPath("$.[*].pregnancyDetails").value(hasItem(DEFAULT_PREGNANCY_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].latestPapTest").value(hasItem(DEFAULT_LATEST_PAP_TEST.toString())))
                .andExpect(jsonPath("$.[*].latestPapTestDetails").value(hasItem(DEFAULT_LATEST_PAP_TEST_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].latestMammography").value(hasItem(DEFAULT_LATEST_MAMMOGRAPHY.toString())))
                .andExpect(jsonPath("$.[*].latestMammographyDetails").value(hasItem(DEFAULT_LATEST_MAMMOGRAPHY_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].usesContraceptives").value(hasItem(DEFAULT_USES_CONTRACEPTIVES.booleanValue())))
                .andExpect(jsonPath("$.[*].hasMenopause").value(hasItem(DEFAULT_HAS_MENOPAUSE.booleanValue())));
    }

    @Test
    @Transactional
    public void getGynecoobstetricBkg() throws Exception {
        // Initialize the database
        gynecoobstetricBkgRepository.saveAndFlush(gynecoobstetricBkg);

        // Get the gynecoobstetricBkg
        restGynecoobstetricBkgMockMvc.perform(get("/api/gynecoobstetricBkgs/{id}", gynecoobstetricBkg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gynecoobstetricBkg.getId().intValue()))
            .andExpect(jsonPath("$.menarche").value(DEFAULT_MENARCHE.toString()))
            .andExpect(jsonPath("$.begginingSexualLife").value(DEFAULT_BEGGINING_SEXUAL_LIFE.toString()))
            .andExpect(jsonPath("$.pregnanciesNumber").value(DEFAULT_PREGNANCIES_NUMBER))
            .andExpect(jsonPath("$.miscarriagesNumber").value(DEFAULT_MISCARRIAGES_NUMBER))
            .andExpect(jsonPath("$.cSectionsNumber").value(DEFAULT_C_SECTIONS_NUMBER))
            .andExpect(jsonPath("$.pregnancyDetails").value(DEFAULT_PREGNANCY_DETAILS.toString()))
            .andExpect(jsonPath("$.latestPapTest").value(DEFAULT_LATEST_PAP_TEST.toString()))
            .andExpect(jsonPath("$.latestPapTestDetails").value(DEFAULT_LATEST_PAP_TEST_DETAILS.toString()))
            .andExpect(jsonPath("$.latestMammography").value(DEFAULT_LATEST_MAMMOGRAPHY.toString()))
            .andExpect(jsonPath("$.latestMammographyDetails").value(DEFAULT_LATEST_MAMMOGRAPHY_DETAILS.toString()))
            .andExpect(jsonPath("$.usesContraceptives").value(DEFAULT_USES_CONTRACEPTIVES.booleanValue()))
            .andExpect(jsonPath("$.hasMenopause").value(DEFAULT_HAS_MENOPAUSE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGynecoobstetricBkg() throws Exception {
        // Get the gynecoobstetricBkg
        restGynecoobstetricBkgMockMvc.perform(get("/api/gynecoobstetricBkgs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGynecoobstetricBkg() throws Exception {
        // Initialize the database
        gynecoobstetricBkgRepository.saveAndFlush(gynecoobstetricBkg);

		int databaseSizeBeforeUpdate = gynecoobstetricBkgRepository.findAll().size();

        // Update the gynecoobstetricBkg
        gynecoobstetricBkg.setMenarche(UPDATED_MENARCHE);
        gynecoobstetricBkg.setBegginingSexualLife(UPDATED_BEGGINING_SEXUAL_LIFE);
        gynecoobstetricBkg.setPregnanciesNumber(UPDATED_PREGNANCIES_NUMBER);
        gynecoobstetricBkg.setMiscarriagesNumber(UPDATED_MISCARRIAGES_NUMBER);
        gynecoobstetricBkg.setcSectionsNumber(UPDATED_C_SECTIONS_NUMBER);
        gynecoobstetricBkg.setPregnancyDetails(UPDATED_PREGNANCY_DETAILS);
        gynecoobstetricBkg.setLatestPapTest(UPDATED_LATEST_PAP_TEST);
        gynecoobstetricBkg.setLatestPapTestDetails(UPDATED_LATEST_PAP_TEST_DETAILS);
        gynecoobstetricBkg.setLatestMammography(UPDATED_LATEST_MAMMOGRAPHY);
        gynecoobstetricBkg.setLatestMammographyDetails(UPDATED_LATEST_MAMMOGRAPHY_DETAILS);
        gynecoobstetricBkg.setUsesContraceptives(UPDATED_USES_CONTRACEPTIVES);
        gynecoobstetricBkg.setHasMenopause(UPDATED_HAS_MENOPAUSE);

        restGynecoobstetricBkgMockMvc.perform(put("/api/gynecoobstetricBkgs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gynecoobstetricBkg)))
                .andExpect(status().isOk());

        // Validate the GynecoobstetricBkg in the database
        List<GynecoobstetricBkg> gynecoobstetricBkgs = gynecoobstetricBkgRepository.findAll();
        assertThat(gynecoobstetricBkgs).hasSize(databaseSizeBeforeUpdate);
        GynecoobstetricBkg testGynecoobstetricBkg = gynecoobstetricBkgs.get(gynecoobstetricBkgs.size() - 1);
        assertThat(testGynecoobstetricBkg.getMenarche()).isEqualTo(UPDATED_MENARCHE);
        assertThat(testGynecoobstetricBkg.getBegginingSexualLife()).isEqualTo(UPDATED_BEGGINING_SEXUAL_LIFE);
        assertThat(testGynecoobstetricBkg.getPregnanciesNumber()).isEqualTo(UPDATED_PREGNANCIES_NUMBER);
        assertThat(testGynecoobstetricBkg.getMiscarriagesNumber()).isEqualTo(UPDATED_MISCARRIAGES_NUMBER);
        assertThat(testGynecoobstetricBkg.getcSectionsNumber()).isEqualTo(UPDATED_C_SECTIONS_NUMBER);
        assertThat(testGynecoobstetricBkg.getPregnancyDetails()).isEqualTo(UPDATED_PREGNANCY_DETAILS);
        assertThat(testGynecoobstetricBkg.getLatestPapTest()).isEqualTo(UPDATED_LATEST_PAP_TEST);
        assertThat(testGynecoobstetricBkg.getLatestPapTestDetails()).isEqualTo(UPDATED_LATEST_PAP_TEST_DETAILS);
        assertThat(testGynecoobstetricBkg.getLatestMammography()).isEqualTo(UPDATED_LATEST_MAMMOGRAPHY);
        assertThat(testGynecoobstetricBkg.getLatestMammographyDetails()).isEqualTo(UPDATED_LATEST_MAMMOGRAPHY_DETAILS);
        assertThat(testGynecoobstetricBkg.getUsesContraceptives()).isEqualTo(UPDATED_USES_CONTRACEPTIVES);
        assertThat(testGynecoobstetricBkg.getHasMenopause()).isEqualTo(UPDATED_HAS_MENOPAUSE);
    }

    @Test
    @Transactional
    public void deleteGynecoobstetricBkg() throws Exception {
        // Initialize the database
        gynecoobstetricBkgRepository.saveAndFlush(gynecoobstetricBkg);

		int databaseSizeBeforeDelete = gynecoobstetricBkgRepository.findAll().size();

        // Get the gynecoobstetricBkg
        restGynecoobstetricBkgMockMvc.perform(delete("/api/gynecoobstetricBkgs/{id}", gynecoobstetricBkg.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GynecoobstetricBkg> gynecoobstetricBkgs = gynecoobstetricBkgRepository.findAll();
        assertThat(gynecoobstetricBkgs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
