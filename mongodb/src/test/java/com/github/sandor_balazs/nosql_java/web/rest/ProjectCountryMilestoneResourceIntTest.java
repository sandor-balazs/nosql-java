package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountryMilestone;
import com.github.sandor_balazs.nosql_java.repository.ProjectCountryMilestoneRepository;
import com.github.sandor_balazs.nosql_java.service.ProjectCountryMilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryMilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMilestoneMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProjectCountryMilestoneResource REST controller.
 *
 * @see ProjectCountryMilestoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectCountryMilestoneResourceIntTest {


    private static final LocalDate DEFAULT_PLANNED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTUAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTUAL = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ProjectCountryMilestoneRepository projectCountryMilestoneRepository;

    @Inject
    private ProjectCountryMilestoneMapper projectCountryMilestoneMapper;

    @Inject
    private ProjectCountryMilestoneService projectCountryMilestoneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectCountryMilestoneMockMvc;

    private ProjectCountryMilestone projectCountryMilestone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectCountryMilestoneResource projectCountryMilestoneResource = new ProjectCountryMilestoneResource();
        ReflectionTestUtils.setField(projectCountryMilestoneResource, "projectCountryMilestoneService", projectCountryMilestoneService);
        ReflectionTestUtils.setField(projectCountryMilestoneResource, "projectCountryMilestoneMapper", projectCountryMilestoneMapper);
        this.restProjectCountryMilestoneMockMvc = MockMvcBuilders.standaloneSetup(projectCountryMilestoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectCountryMilestoneRepository.deleteAll();
        projectCountryMilestone = new ProjectCountryMilestone();
        projectCountryMilestone.setPlanned(DEFAULT_PLANNED);
        projectCountryMilestone.setActual(DEFAULT_ACTUAL);
    }

    @Test
    public void createProjectCountryMilestone() throws Exception {
        int databaseSizeBeforeCreate = projectCountryMilestoneRepository.findAll().size();

        // Create the ProjectCountryMilestone
        ProjectCountryMilestoneDTO projectCountryMilestoneDTO = projectCountryMilestoneMapper.projectCountryMilestoneToProjectCountryMilestoneDTO(projectCountryMilestone);

        restProjectCountryMilestoneMockMvc.perform(post("/api/projectCountryMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectCountryMilestoneDTO)))
                .andExpect(status().isCreated());

        // Validate the ProjectCountryMilestone in the database
        List<ProjectCountryMilestone> projectCountryMilestones = projectCountryMilestoneRepository.findAll();
        assertThat(projectCountryMilestones).hasSize(databaseSizeBeforeCreate + 1);
        ProjectCountryMilestone testProjectCountryMilestone = projectCountryMilestones.get(projectCountryMilestones.size() - 1);
        assertThat(testProjectCountryMilestone.getPlanned()).isEqualTo(DEFAULT_PLANNED);
        assertThat(testProjectCountryMilestone.getActual()).isEqualTo(DEFAULT_ACTUAL);
    }

    @Test
    public void getAllProjectCountryMilestones() throws Exception {
        // Initialize the database
        projectCountryMilestoneRepository.save(projectCountryMilestone);

        // Get all the projectCountryMilestones
        restProjectCountryMilestoneMockMvc.perform(get("/api/projectCountryMilestones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectCountryMilestone.getId())))
                .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.toString())))
                .andExpect(jsonPath("$.[*].actual").value(hasItem(DEFAULT_ACTUAL.toString())));
    }

    @Test
    public void getProjectCountryMilestone() throws Exception {
        // Initialize the database
        projectCountryMilestoneRepository.save(projectCountryMilestone);

        // Get the projectCountryMilestone
        restProjectCountryMilestoneMockMvc.perform(get("/api/projectCountryMilestones/{id}", projectCountryMilestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectCountryMilestone.getId()))
            .andExpect(jsonPath("$.planned").value(DEFAULT_PLANNED.toString()))
            .andExpect(jsonPath("$.actual").value(DEFAULT_ACTUAL.toString()));
    }

    @Test
    public void getNonExistingProjectCountryMilestone() throws Exception {
        // Get the projectCountryMilestone
        restProjectCountryMilestoneMockMvc.perform(get("/api/projectCountryMilestones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectCountryMilestone() throws Exception {
        // Initialize the database
        projectCountryMilestoneRepository.save(projectCountryMilestone);

		int databaseSizeBeforeUpdate = projectCountryMilestoneRepository.findAll().size();

        // Update the projectCountryMilestone
        projectCountryMilestone.setPlanned(UPDATED_PLANNED);
        projectCountryMilestone.setActual(UPDATED_ACTUAL);
        ProjectCountryMilestoneDTO projectCountryMilestoneDTO = projectCountryMilestoneMapper.projectCountryMilestoneToProjectCountryMilestoneDTO(projectCountryMilestone);

        restProjectCountryMilestoneMockMvc.perform(put("/api/projectCountryMilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectCountryMilestoneDTO)))
                .andExpect(status().isOk());

        // Validate the ProjectCountryMilestone in the database
        List<ProjectCountryMilestone> projectCountryMilestones = projectCountryMilestoneRepository.findAll();
        assertThat(projectCountryMilestones).hasSize(databaseSizeBeforeUpdate);
        ProjectCountryMilestone testProjectCountryMilestone = projectCountryMilestones.get(projectCountryMilestones.size() - 1);
        assertThat(testProjectCountryMilestone.getPlanned()).isEqualTo(UPDATED_PLANNED);
        assertThat(testProjectCountryMilestone.getActual()).isEqualTo(UPDATED_ACTUAL);
    }

    @Test
    public void deleteProjectCountryMilestone() throws Exception {
        // Initialize the database
        projectCountryMilestoneRepository.save(projectCountryMilestone);

		int databaseSizeBeforeDelete = projectCountryMilestoneRepository.findAll().size();

        // Get the projectCountryMilestone
        restProjectCountryMilestoneMockMvc.perform(delete("/api/projectCountryMilestones/{id}", projectCountryMilestone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectCountryMilestone> projectCountryMilestones = projectCountryMilestoneRepository.findAll();
        assertThat(projectCountryMilestones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
