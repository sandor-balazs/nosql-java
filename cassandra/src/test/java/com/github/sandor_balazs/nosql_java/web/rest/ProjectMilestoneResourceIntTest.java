package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.AbstractCassandraTest;
import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.ProjectMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectMilestoneRepository;
import com.github.sandor_balazs.nosql_java.service.ProjectMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectMilestoneMapper;

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
 * Test class for the ProjectMilestoneResource REST controller.
 *
 * @see ProjectMilestoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectMilestoneResourceIntTest extends AbstractCassandraTest {


    private static final Date DEFAULT_PLANNED = new Date();
    private static final Date UPDATED_PLANNED = new Date();

    private static final Date DEFAULT_ACTUAL = new Date();
    private static final Date UPDATED_ACTUAL = new Date();

    @Inject
    private ProjectMilestoneRepository projectMilestoneRepository;

    @Inject
    private ProjectMilestoneMapper projectMilestoneMapper;

    @Inject
    private ProjectMilestoneService projectMilestoneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectMilestoneMockMvc;

    private ProjectMilestone projectMilestone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectMilestoneResource projectMilestoneResource = new ProjectMilestoneResource();
        ReflectionTestUtils.setField(projectMilestoneResource, "projectMilestoneService", projectMilestoneService);
        ReflectionTestUtils.setField(projectMilestoneResource, "projectMilestoneMapper", projectMilestoneMapper);
        this.restProjectMilestoneMockMvc = MockMvcBuilders.standaloneSetup(projectMilestoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectMilestoneRepository.deleteAll();
        projectMilestone = new ProjectMilestone();
        projectMilestone.setPlanned(DEFAULT_PLANNED);
        projectMilestone.setActual(DEFAULT_ACTUAL);
    }

    @Test
    public void createProjectMilestone() throws Exception {
        int databaseSizeBeforeCreate = projectMilestoneRepository.findAll().size();

        // Create the ProjectMilestone
        ProjectMilestoneDTO projectMilestoneDTO = projectMilestoneMapper.projectMilestoneToProjectMilestoneDTO(projectMilestone);

        restProjectMilestoneMockMvc.perform(post("/api/projectMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectMilestoneDTO)))
                .andExpect(status().isCreated());

        // Validate the ProjectMilestone in the database
        List<ProjectMilestone> projectMilestones = projectMilestoneRepository.findAll();
        assertThat(projectMilestones).hasSize(databaseSizeBeforeCreate + 1);
        ProjectMilestone testProjectMilestone = projectMilestones.get(projectMilestones.size() - 1);
        assertThat(testProjectMilestone.getPlanned()).isEqualTo(DEFAULT_PLANNED);
        assertThat(testProjectMilestone.getActual()).isEqualTo(DEFAULT_ACTUAL);
    }

    @Test
    public void getAllProjectMilestones() throws Exception {
        // Initialize the database
        projectMilestoneRepository.save(projectMilestone);

        // Get all the projectMilestones
        restProjectMilestoneMockMvc.perform(get("/api/projectMilestones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectMilestone.getId().toString())))
                .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.getTime())))
                .andExpect(jsonPath("$.[*].actual").value(hasItem(DEFAULT_ACTUAL.getTime())));
    }

    @Test
    public void getProjectMilestone() throws Exception {
        // Initialize the database
        projectMilestoneRepository.save(projectMilestone);

        // Get the projectMilestone
        restProjectMilestoneMockMvc.perform(get("/api/projectMilestones/{id}", projectMilestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectMilestone.getId().toString()))
            .andExpect(jsonPath("$.planned").value(DEFAULT_PLANNED.getTime()))
            .andExpect(jsonPath("$.actual").value(DEFAULT_ACTUAL.getTime()));
    }

    @Test
    public void getNonExistingProjectMilestone() throws Exception {
        // Get the projectMilestone
        restProjectMilestoneMockMvc.perform(get("/api/projectMilestones/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectMilestone() throws Exception {
        // Initialize the database
        projectMilestoneRepository.save(projectMilestone);

		int databaseSizeBeforeUpdate = projectMilestoneRepository.findAll().size();

        // Update the projectMilestone
        projectMilestone.setPlanned(UPDATED_PLANNED);
        projectMilestone.setActual(UPDATED_ACTUAL);
        ProjectMilestoneDTO projectMilestoneDTO = projectMilestoneMapper.projectMilestoneToProjectMilestoneDTO(projectMilestone);

        restProjectMilestoneMockMvc.perform(put("/api/projectMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectMilestoneDTO)))
                .andExpect(status().isOk());

        // Validate the ProjectMilestone in the database
        List<ProjectMilestone> projectMilestones = projectMilestoneRepository.findAll();
        assertThat(projectMilestones).hasSize(databaseSizeBeforeUpdate);
        ProjectMilestone testProjectMilestone = projectMilestones.get(projectMilestones.size() - 1);
        assertThat(testProjectMilestone.getPlanned()).isEqualTo(UPDATED_PLANNED);
        assertThat(testProjectMilestone.getActual()).isEqualTo(UPDATED_ACTUAL);
    }

    @Test
    public void deleteProjectMilestone() throws Exception {
        // Initialize the database
        projectMilestoneRepository.save(projectMilestone);

		int databaseSizeBeforeDelete = projectMilestoneRepository.findAll().size();

        // Get the projectMilestone
        restProjectMilestoneMockMvc.perform(delete("/api/projectMilestones/{id}", projectMilestone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectMilestone> projectMilestones = projectMilestoneRepository.findAll();
        assertThat(projectMilestones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
