package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.AbstractCassandraTest;
import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.Milestone;
import com.github.sandor_balazs.nosql_java.repository.MilestoneRepository;
import com.github.sandor_balazs.nosql_java.service.MilestoneService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.MilestoneDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.MilestoneMapper;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MilestoneResource REST controller.
 *
 * @see MilestoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MilestoneResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_APP_ORDER = 1;
    private static final Integer UPDATED_APP_ORDER = 2;

    @Inject
    private MilestoneRepository milestoneRepository;

    @Inject
    private MilestoneMapper milestoneMapper;

    @Inject
    private MilestoneService milestoneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMilestoneMockMvc;

    private Milestone milestone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MilestoneResource milestoneResource = new MilestoneResource();
        ReflectionTestUtils.setField(milestoneResource, "milestoneService", milestoneService);
        ReflectionTestUtils.setField(milestoneResource, "milestoneMapper", milestoneMapper);
        this.restMilestoneMockMvc = MockMvcBuilders.standaloneSetup(milestoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        milestoneRepository.deleteAll();
        milestone = new Milestone();
        milestone.setName(DEFAULT_NAME);
        milestone.setAppOrder(DEFAULT_APP_ORDER);
    }

    @Test
    public void createMilestone() throws Exception {
        int databaseSizeBeforeCreate = milestoneRepository.findAll().size();

        // Create the Milestone
        MilestoneDTO milestoneDTO = milestoneMapper.milestoneToMilestoneDTO(milestone);

        restMilestoneMockMvc.perform(post("/api/milestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(milestoneDTO)))
                .andExpect(status().isCreated());

        // Validate the Milestone in the database
        List<Milestone> milestones = milestoneRepository.findAll();
        assertThat(milestones).hasSize(databaseSizeBeforeCreate + 1);
        Milestone testMilestone = milestones.get(milestones.size() - 1);
        assertThat(testMilestone.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMilestone.getAppOrder()).isEqualTo(DEFAULT_APP_ORDER);
    }

    @Test
    public void getAllMilestones() throws Exception {
        // Initialize the database
        milestoneRepository.save(milestone);

        // Get all the milestones
        restMilestoneMockMvc.perform(get("/api/milestones?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(milestone.getId().toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].appOrder").value(hasItem(DEFAULT_APP_ORDER)));
    }

    @Test
    public void getMilestone() throws Exception {
        // Initialize the database
        milestoneRepository.save(milestone);

        // Get the milestone
        restMilestoneMockMvc.perform(get("/api/milestones/{id}", milestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(milestone.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.appOrder").value(DEFAULT_APP_ORDER));
    }

    @Test
    public void getNonExistingMilestone() throws Exception {
        // Get the milestone
        restMilestoneMockMvc.perform(get("/api/milestones/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateMilestone() throws Exception {
        // Initialize the database
        milestoneRepository.save(milestone);

		int databaseSizeBeforeUpdate = milestoneRepository.findAll().size();

        // Update the milestone
        milestone.setName(UPDATED_NAME);
        milestone.setAppOrder(UPDATED_APP_ORDER);
        MilestoneDTO milestoneDTO = milestoneMapper.milestoneToMilestoneDTO(milestone);

        restMilestoneMockMvc.perform(put("/api/milestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(milestoneDTO)))
                .andExpect(status().isOk());

        // Validate the Milestone in the database
        List<Milestone> milestones = milestoneRepository.findAll();
        assertThat(milestones).hasSize(databaseSizeBeforeUpdate);
        Milestone testMilestone = milestones.get(milestones.size() - 1);
        assertThat(testMilestone.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMilestone.getAppOrder()).isEqualTo(UPDATED_APP_ORDER);
    }

    @Test
    public void deleteMilestone() throws Exception {
        // Initialize the database
        milestoneRepository.save(milestone);

		int databaseSizeBeforeDelete = milestoneRepository.findAll().size();

        // Get the milestone
        restMilestoneMockMvc.perform(delete("/api/milestones/{id}", milestone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Milestone> milestones = milestoneRepository.findAll();
        assertThat(milestones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
