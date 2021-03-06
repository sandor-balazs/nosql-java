package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.AbstractCassandraTest;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class ProjectRegionMilestoneResourceIntTest extends AbstractCassandraTest {


    private static final Date DEFAULT_PLANNED = new Date();
    private static final Date UPDATED_PLANNED = new Date();

    private static final Date DEFAULT_ACTUAL = new Date();
    private static final Date UPDATED_ACTUAL = new Date();

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
        projectRegionMilestoneRepository.deleteAll();
        projectRegionMilestone = new ProjectRegionMilestone();
        projectRegionMilestone.setPlanned(DEFAULT_PLANNED);
        projectRegionMilestone.setActual(DEFAULT_ACTUAL);
    }

    @Test
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
    public void getAllProjectRegionMilestones() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.save(projectRegionMilestone);

        // Get all the projectRegionMilestones
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectRegionMilestone.getId().toString())))
                .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.getTime())))
                .andExpect(jsonPath("$.[*].actual").value(hasItem(DEFAULT_ACTUAL.getTime())));
    }

    @Test
    public void getProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.save(projectRegionMilestone);

        // Get the projectRegionMilestone
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones/{id}", projectRegionMilestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectRegionMilestone.getId().toString()))
            .andExpect(jsonPath("$.planned").value(DEFAULT_PLANNED.getTime()))
            .andExpect(jsonPath("$.actual").value(DEFAULT_ACTUAL.getTime()));
    }

    @Test
    public void getNonExistingProjectRegionMilestone() throws Exception {
        // Get the projectRegionMilestone
        restProjectRegionMilestoneMockMvc.perform(get("/api/projectRegionMilestones/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.save(projectRegionMilestone);

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
    public void deleteProjectRegionMilestone() throws Exception {
        // Initialize the database
        projectRegionMilestoneRepository.save(projectRegionMilestone);

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
