package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.AbstractCassandraTest;
import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.ProjectCountry;
import com.github.sandor_balazs.nosql_java.repository.ProjectCountryRepository;
import com.github.sandor_balazs.nosql_java.service.ProjectCountryService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.ProjectCountryDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.ProjectCountryMapper;

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
 * Test class for the ProjectCountryResource REST controller.
 *
 * @see ProjectCountryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectCountryResourceIntTest extends AbstractCassandraTest {


    @Inject
    private ProjectCountryRepository projectCountryRepository;

    @Inject
    private ProjectCountryMapper projectCountryMapper;

    @Inject
    private ProjectCountryService projectCountryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectCountryMockMvc;

    private ProjectCountry projectCountry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectCountryResource projectCountryResource = new ProjectCountryResource();
        ReflectionTestUtils.setField(projectCountryResource, "projectCountryService", projectCountryService);
        ReflectionTestUtils.setField(projectCountryResource, "projectCountryMapper", projectCountryMapper);
        this.restProjectCountryMockMvc = MockMvcBuilders.standaloneSetup(projectCountryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projectCountryRepository.deleteAll();
        projectCountry = new ProjectCountry();
    }

    @Test
    public void createProjectCountry() throws Exception {
        int databaseSizeBeforeCreate = projectCountryRepository.findAll().size();

        // Create the ProjectCountry
        ProjectCountryDTO projectCountryDTO = projectCountryMapper.projectCountryToProjectCountryDTO(projectCountry);

        restProjectCountryMockMvc.perform(post("/api/projectCountrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectCountryDTO)))
                .andExpect(status().isCreated());

        // Validate the ProjectCountry in the database
        List<ProjectCountry> projectCountrys = projectCountryRepository.findAll();
        assertThat(projectCountrys).hasSize(databaseSizeBeforeCreate + 1);
        ProjectCountry testProjectCountry = projectCountrys.get(projectCountrys.size() - 1);
    }

    @Test
    public void getAllProjectCountrys() throws Exception {
        // Initialize the database
        projectCountryRepository.save(projectCountry);

        // Get all the projectCountrys
        restProjectCountryMockMvc.perform(get("/api/projectCountrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projectCountry.getId().toString())));
    }

    @Test
    public void getProjectCountry() throws Exception {
        // Initialize the database
        projectCountryRepository.save(projectCountry);

        // Get the projectCountry
        restProjectCountryMockMvc.perform(get("/api/projectCountrys/{id}", projectCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projectCountry.getId().toString()));
    }

    @Test
    public void getNonExistingProjectCountry() throws Exception {
        // Get the projectCountry
        restProjectCountryMockMvc.perform(get("/api/projectCountrys/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProjectCountry() throws Exception {
        // Initialize the database
        projectCountryRepository.save(projectCountry);

		int databaseSizeBeforeUpdate = projectCountryRepository.findAll().size();

        // Update the projectCountry
        ProjectCountryDTO projectCountryDTO = projectCountryMapper.projectCountryToProjectCountryDTO(projectCountry);

        restProjectCountryMockMvc.perform(put("/api/projectCountrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projectCountryDTO)))
                .andExpect(status().isOk());

        // Validate the ProjectCountry in the database
        List<ProjectCountry> projectCountrys = projectCountryRepository.findAll();
        assertThat(projectCountrys).hasSize(databaseSizeBeforeUpdate);
        ProjectCountry testProjectCountry = projectCountrys.get(projectCountrys.size() - 1);
    }

    @Test
    public void deleteProjectCountry() throws Exception {
        // Initialize the database
        projectCountryRepository.save(projectCountry);

		int databaseSizeBeforeDelete = projectCountryRepository.findAll().size();

        // Get the projectCountry
        restProjectCountryMockMvc.perform(delete("/api/projectCountrys/{id}", projectCountry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectCountry> projectCountrys = projectCountryRepository.findAll();
        assertThat(projectCountrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
