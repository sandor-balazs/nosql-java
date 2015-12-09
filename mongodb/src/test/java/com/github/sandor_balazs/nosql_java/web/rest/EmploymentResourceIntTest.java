package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.Employment;
import com.github.sandor_balazs.nosql_java.repository.EmploymentRepository;
import com.github.sandor_balazs.nosql_java.service.EmploymentService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.EmploymentDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.EmploymentMapper;

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
 * Test class for the EmploymentResource REST controller.
 *
 * @see EmploymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmploymentResourceIntTest {


    private static final Float DEFAULT_FTE = 1F;
    private static final Float UPDATED_FTE = 2F;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private EmploymentRepository employmentRepository;

    @Inject
    private EmploymentMapper employmentMapper;

    @Inject
    private EmploymentService employmentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmploymentMockMvc;

    private Employment employment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmploymentResource employmentResource = new EmploymentResource();
        ReflectionTestUtils.setField(employmentResource, "employmentService", employmentService);
        ReflectionTestUtils.setField(employmentResource, "employmentMapper", employmentMapper);
        this.restEmploymentMockMvc = MockMvcBuilders.standaloneSetup(employmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employmentRepository.deleteAll();
        employment = new Employment();
        employment.setFte(DEFAULT_FTE);
        employment.setStartDate(DEFAULT_START_DATE);
        employment.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    public void createEmployment() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.employmentToEmploymentDTO(employment);

        restEmploymentMockMvc.perform(post("/api/employments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
                .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employments = employmentRepository.findAll();
        assertThat(employments).hasSize(databaseSizeBeforeCreate + 1);
        Employment testEmployment = employments.get(employments.size() - 1);
        assertThat(testEmployment.getFte()).isEqualTo(DEFAULT_FTE);
        assertThat(testEmployment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    public void getAllEmployments() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        // Get all the employments
        restEmploymentMockMvc.perform(get("/api/employments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employment.getId())))
                .andExpect(jsonPath("$.[*].fte").value(hasItem(DEFAULT_FTE.doubleValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", employment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employment.getId()))
            .andExpect(jsonPath("$.fte").value(DEFAULT_FTE.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    public void getNonExistingEmployment() throws Exception {
        // Get the employment
        restEmploymentMockMvc.perform(get("/api/employments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

		int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment
        employment.setFte(UPDATED_FTE);
        employment.setStartDate(UPDATED_START_DATE);
        employment.setEndDate(UPDATED_END_DATE);
        EmploymentDTO employmentDTO = employmentMapper.employmentToEmploymentDTO(employment);

        restEmploymentMockMvc.perform(put("/api/employments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
                .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employments = employmentRepository.findAll();
        assertThat(employments).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employments.get(employments.size() - 1);
        assertThat(testEmployment.getFte()).isEqualTo(UPDATED_FTE);
        assertThat(testEmployment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void deleteEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

		int databaseSizeBeforeDelete = employmentRepository.findAll().size();

        // Get the employment
        restEmploymentMockMvc.perform(delete("/api/employments/{id}", employment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employment> employments = employmentRepository.findAll();
        assertThat(employments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
