package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.ProjectRegionMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectRegionMilestoneRepository;
import com.github.sandor_balazs.nosql_java.service.ProjectRegionMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectRegionMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectRegionMilestoneMapper;

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
 * Test class for the ProjectRegionMilestoneResource REST controller.
 *
 * @see ProjectRegionMilestoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectRegionMilestoneResourceIntTest {


    private static final LocalDate DEFAULT_PLANNED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ProjectRegionMilestoneRepository projectRegionMilestoneRepository;

    @Inject
    private ProjectRegionMilestoneMapper projectRegionMilestoneMapper;

    @Inject
    private ProjectRegionMilestoneService projectRegionMilestoneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectRegionMilestoneMockMvc;

    private ProjectRegionMilestone projectRegionMilestone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectRegionMilestoneResource projectRegionMilestoneResource = new ProjectRegionMilestoneResource();
        ReflectionTestUtils.setField(projectRegionMilestoneResource, "projectRegionMilestoneService", projectRegionMilestoneService);
        ReflectionTestUtils.setField(projectRegionMilestoneResource, "projectRegionMilestoneMapper", projectRegionMilestoneMapper);
        this.restProjectRegionMilestoneMockMvc = MockMvcBuilders.standaloneSetup(projectRegionMilestoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectRegionMilestone = new ProjectRegionMilestone();
        projectRegionMilestone.setPlanned(DEFAULT_PLANNED);
        projectRegionMilestone.setActual(DEFAULT_ACTUAL);
    }

    @Test
    @Transactional
    public void createProjectRegionMilestone() throws Exception {
        int databaseSizeBeforeCreate = projectRegionMilestoneRepository.findAll().size();

        // Create the ProjectRegionMilestone
        ProjectRegionMilestoneDTO projectRegionMilestoneDTO = projectRegionMilestoneMapper.projectRegionMilestoneToProjectRegionMilestoneDTO(projectRegionMilestone);

        restProjectRegionMilestoneMockMvc.perform(post("/api/projectRegionMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectRegionMilestoneDTO)))
                .andExpect(status().isCreated());

        // Validate the ProjectRegionMilestone in the database
        List<ProjectRegionMilestone> projectRegionMilestones = projectRegionMilestoneRepository.findAll();
        assertThat(projectRegionMilestones).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRegionMilestone testProjectRegionMilestone = projectRegionMilestones.get(projectRegionMilestones.size() - 1);
        assertThat(testProjectRegionMilestone.getPlanned()).isEqualTo(DEFAULT_PLANNED);
        assertThat(testProjectRegionMilestone.getActual()).isEqualTo(DEFAULT_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllProjectRegionMilestones() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.saveAndFlush(projectRegionMilestone);

        // Get all the projectRegionMilestones
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectRegionMilestone.getId().intValue())))
                .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.toString())))
                .andExpect(jsonPath("$.[*].actual").value(hasItem(DEFAULT_ACTUAL.toString())));
    }

    @Test
    @Transactional
    public void getProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.saveAndFlush(projectRegionMilestone);

        // Get the projectRegionMilestone
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones/{id}", projectRegionMilestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectRegionMilestone.getId().intValue()))
            .andExpect(jsonPath("$.planned").value(DEFAULT_PLANNED.toString()))
            .andExpect(jsonPath("$.actual").value(DEFAULT_ACTUAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectRegionMilestone() throws Exception {
        // Get the projectRegionMilestone
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.saveAndFlush(projectRegionMilestone);

		int databaseSizeBeforeUpdate = projectRegionMilestoneRepository.findAll().size();

        // Update the projectRegionMilestone
        projectRegionMilestone.setPlanned(UPDATED_PLANNED);
        projectRegionMilestone.setActual(UPDATED_ACTUAL);
        ProjectRegionMilestoneDTO projectRegionMilestoneDTO = projectRegionMilestoneMapper.projectRegionMilestoneToProjectRegionMilestoneDTO(projectRegionMilestone);

        restProjectRegionMilestoneMockMvc.perform(put("/api/projectRegionMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectRegionMilestoneDTO)))
                .andExpect(status().isOk());

        // Validate the ProjectRegionMilestone in the database
        List<ProjectRegionMilestone> projectRegionMilestones = projectRegionMilestoneRepository.findAll();
        assertThat(projectRegionMilestones).hasSize(databaseSizeBeforeUpdate);
        ProjectRegionMilestone testProjectRegionMilestone = projectRegionMilestones.get(projectRegionMilestones.size() - 1);
        assertThat(testProjectRegionMilestone.getPlanned()).isEqualTo(UPDATED_PLANNED);
        assertThat(testProjectRegionMilestone.getActual()).isEqualTo(UPDATED_ACTUAL);
    }

    @Test
    @Transactional
    public void deleteProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.saveAndFlush(projectRegionMilestone);

		int databaseSizeBeforeDelete = projectRegionMilestoneRepository.findAll().size();

        // Get the projectRegionMilestone
        restProjectRegionMilestoneMockMvc.perform(delete("/api/projectRegionMilestones/{id}", projectRegionMilestone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectRegionMilestone> projectRegionMilestones = projectRegionMilestoneRepository.findAll();
        assertThat(projectRegionMilestones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
