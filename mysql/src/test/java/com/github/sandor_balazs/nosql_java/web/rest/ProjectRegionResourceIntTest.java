package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegion;
import com.github.sandor_balazs.nosql_java.repository.ProjectRegionRepository;
import com.github.sandor_balazs.nosql_java.service.ProjectRegionService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMapper;

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
 * Test class for the ProjectRegionResource REST controller.
 *
 * @see ProjectRegionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectRegionResourceIntTest {


    @Inject
    private ProjectRegionRepository projectRegionRepository;

    @Inject
    private ProjectRegionMapper projectRegionMapper;

    @Inject
    private ProjectRegionService projectRegionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectRegionMockMvc;

    private ProjectRegion projectRegion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectRegionResource projectRegionResource = new ProjectRegionResource();
        ReflectionTestUtils.setField(projectRegionResource, "projectRegionService", projectRegionService);
        ReflectionTestUtils.setField(projectRegionResource, "projectRegionMapper", projectRegionMapper);
        this.restProjectRegionMockMvc = MockMvcBuilders.standaloneSetup(projectRegionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectRegion = new ProjectRegion();
    }

    @Test
    @Transactional
    public void createProjectRegion() throws Exception {
        int databaseSizeBeforeCreate = projectRegionRepository.findAll().size();

        // Create the ProjectRegion
        ProjectRegionDTO projectRegionDTO = projectRegionMapper.projectRegionToProjectRegionDTO(projectRegion);

        restProjectRegionMockMvc.perform(post("/api/projectRegions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectRegionDTO)))
                .andExpect(status().isCreated());

        // Validate the ProjectRegion in the database
        List<ProjectRegion> projectRegions = projectRegionRepository.findAll();
        assertThat(projectRegions).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRegion testProjectRegion = projectRegions.get(projectRegions.size() - 1);
    }

    @Test
    @Transactional
    public void getAllProjectRegions() throws Exception {
        // Initialize the database
        projectRegionRepository.saveAndFlush(projectRegion);

        // Get all the projectRegions
        restProjectRegionMockMvc.perform(get("/api/projectRegions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectRegion.getId().intValue())));
    }

    @Test
    @Transactional
    public void getProjectRegion() throws Exception {
        // Initialize the database
        projectRegionRepository.saveAndFlush(projectRegion);

        // Get the projectRegion
        restProjectRegionMockMvc.perform(get("/api/projectRegions/{id}", projectRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectRegion.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectRegion() throws Exception {
        // Get the projectRegion
        restProjectRegionMockMvc.perform(get("/api/projectRegions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRegion() throws Exception {
        // Initialize the database
        projectRegionRepository.saveAndFlush(projectRegion);

		int databaseSizeBeforeUpdate = projectRegionRepository.findAll().size();

        // Update the projectRegion
        ProjectRegionDTO projectRegionDTO = projectRegionMapper.projectRegionToProjectRegionDTO(projectRegion);

        restProjectRegionMockMvc.perform(put("/api/projectRegions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectRegionDTO)))
                .andExpect(status().isOk());

        // Validate the ProjectRegion in the database
        List<ProjectRegion> projectRegions = projectRegionRepository.findAll();
        assertThat(projectRegions).hasSize(databaseSizeBeforeUpdate);
        ProjectRegion testProjectRegion = projectRegions.get(projectRegions.size() - 1);
    }

    @Test
    @Transactional
    public void deleteProjectRegion() throws Exception {
        // Initialize the database
        projectRegionRepository.saveAndFlush(projectRegion);

		int databaseSizeBeforeDelete = projectRegionRepository.findAll().size();

        // Get the projectRegion
        restProjectRegionMockMvc.perform(delete("/api/projectRegions/{id}", projectRegion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectRegion> projectRegions = projectRegionRepository.findAll();
        assertThat(projectRegions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
