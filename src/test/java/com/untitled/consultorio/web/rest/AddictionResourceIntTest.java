package com.untitled.consultorio.web.rest;

import com.untitled.consultorio.Application;
import com.untitled.consultorio.domain.Addiction;
import com.untitled.consultorio.repository.AddictionRepository;
import com.untitled.consultorio.repository.search.AddictionSearchRepository;

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
 * Test class for the AddictionResource REST controller.
 *
 * @see AddictionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AddictionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private AddictionRepository addictionRepository;

    @Inject
    private AddictionSearchRepository addictionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAddictionMockMvc;

    private Addiction addiction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddictionResource addictionResource = new AddictionResource();
        ReflectionTestUtils.setField(addictionResource, "addictionSearchRepository", addictionSearchRepository);
        ReflectionTestUtils.setField(addictionResource, "addictionRepository", addictionRepository);
        this.restAddictionMockMvc = MockMvcBuilders.standaloneSetup(addictionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        addiction = new Addiction();
        addiction.setCode(DEFAULT_CODE);
        addiction.setName(DEFAULT_NAME);
        addiction.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAddiction() throws Exception {
        int databaseSizeBeforeCreate = addictionRepository.findAll().size();

        // Create the Addiction

        restAddictionMockMvc.perform(post("/api/addictions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addiction)))
                .andExpect(status().isCreated());

        // Validate the Addiction in the database
        List<Addiction> addictions = addictionRepository.findAll();
        assertThat(addictions).hasSize(databaseSizeBeforeCreate + 1);
        Addiction testAddiction = addictions.get(addictions.size() - 1);
        assertThat(testAddiction.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAddiction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddiction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAddictions() throws Exception {
        // Initialize the database
        addictionRepository.saveAndFlush(addiction);

        // Get all the addictions
        restAddictionMockMvc.perform(get("/api/addictions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(addiction.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAddiction() throws Exception {
        // Initialize the database
        addictionRepository.saveAndFlush(addiction);

        // Get the addiction
        restAddictionMockMvc.perform(get("/api/addictions/{id}", addiction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(addiction.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddiction() throws Exception {
        // Get the addiction
        restAddictionMockMvc.perform(get("/api/addictions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddiction() throws Exception {
        // Initialize the database
        addictionRepository.saveAndFlush(addiction);

		int databaseSizeBeforeUpdate = addictionRepository.findAll().size();

        // Update the addiction
        addiction.setCode(UPDATED_CODE);
        addiction.setName(UPDATED_NAME);
        addiction.setDescription(UPDATED_DESCRIPTION);

        restAddictionMockMvc.perform(put("/api/addictions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(addiction)))
                .andExpect(status().isOk());

        // Validate the Addiction in the database
        List<Addiction> addictions = addictionRepository.findAll();
        assertThat(addictions).hasSize(databaseSizeBeforeUpdate);
        Addiction testAddiction = addictions.get(addictions.size() - 1);
        assertThat(testAddiction.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAddiction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddiction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAddiction() throws Exception {
        // Initialize the database
        addictionRepository.saveAndFlush(addiction);

		int databaseSizeBeforeDelete = addictionRepository.findAll().size();

        // Get the addiction
        restAddictionMockMvc.perform(delete("/api/addictions/{id}", addiction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Addiction> addictions = addictionRepository.findAll();
        assertThat(addictions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
