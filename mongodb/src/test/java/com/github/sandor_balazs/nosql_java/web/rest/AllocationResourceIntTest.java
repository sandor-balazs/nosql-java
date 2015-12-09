package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.Allocation;
import com.github.sandor_balazs.nosql_java.repository.AllocationRepository;
import com.github.sandor_balazs.nosql_java.service.AllocationService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.AllocationDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.AllocationMapper;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AllocationResource REST controller.
 *
 * @see AllocationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AllocationResourceIntTest {


    private static final Float DEFAULT_FTE = 1F;
    private static final Float UPDATED_FTE = 2F;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    @Inject
    private AllocationRepository allocationRepository;

    @Inject
    private AllocationMapper allocationMapper;

    @Inject
    private AllocationService allocationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAllocationMockMvc;

    private Allocation allocation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AllocationResource allocationResource = new AllocationResource();
        ReflectionTestUtils.setField(allocationResource, "allocationService", allocationService);
        ReflectionTestUtils.setField(allocationResource, "allocationMapper", allocationMapper);
        this.restAllocationMockMvc = MockMvcBuilders.standaloneSetup(allocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        allocationRepository.deleteAll();
        allocation = new Allocation();
        allocation.setFte(DEFAULT_FTE);
        allocation.setYear(DEFAULT_YEAR);
        allocation.setMonth(DEFAULT_MONTH);
    }

    @Test
    public void createAllocation() throws Exception {
        int databaseSizeBeforeCreate = allocationRepository.findAll().size();

        // Create the Allocation
        AllocationDTO allocationDTO = allocationMapper.allocationToAllocationDTO(allocation);

        restAllocationMockMvc.perform(post("/api/allocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(allocationDTO)))
                .andExpect(status().isCreated());

        // Validate the Allocation in the database
        List<Allocation> allocations = allocationRepository.findAll();
        assertThat(allocations).hasSize(databaseSizeBeforeCreate + 1);
        Allocation testAllocation = allocations.get(allocations.size() - 1);
        assertThat(testAllocation.getFte()).isEqualTo(DEFAULT_FTE);
        assertThat(testAllocation.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAllocation.getMonth()).isEqualTo(DEFAULT_MONTH);
    }

    @Test
    public void getAllAllocations() throws Exception {
        // Initialize the database
        allocationRepository.save(allocation);

        // Get all the allocations
        restAllocationMockMvc.perform(get("/api/allocations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(allocation.getId())))
                .andExpect(jsonPath("$.[*].fte").value(hasItem(DEFAULT_FTE.doubleValue())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)));
    }

    @Test
    public void getAllocation() throws Exception {
        // Initialize the database
        allocationRepository.save(allocation);

        // Get the allocation
        restAllocationMockMvc.perform(get("/api/allocations/{id}", allocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(allocation.getId()))
            .andExpect(jsonPath("$.fte").value(DEFAULT_FTE.doubleValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH));
    }

    @Test
    public void getNonExistingAllocation() throws Exception {
        // Get the allocation
        restAllocationMockMvc.perform(get("/api/allocations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAllocation() throws Exception {
        // Initialize the database
        allocationRepository.save(allocation);

		int databaseSizeBeforeUpdate = allocationRepository.findAll().size();

        // Update the allocation
        allocation.setFte(UPDATED_FTE);
        allocation.setYear(UPDATED_YEAR);
        allocation.setMonth(UPDATED_MONTH);
        AllocationDTO allocationDTO = allocationMapper.allocationToAllocationDTO(allocation);

        restAllocationMockMvc.perform(put("/api/allocations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(allocationDTO)))
                .andExpect(status().isOk());

        // Validate the Allocation in the database
        List<Allocation> allocations = allocationRepository.findAll();
        assertThat(allocations).hasSize(databaseSizeBeforeUpdate);
        Allocation testAllocation = allocations.get(allocations.size() - 1);
        assertThat(testAllocation.getFte()).isEqualTo(UPDATED_FTE);
        assertThat(testAllocation.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAllocation.getMonth()).isEqualTo(UPDATED_MONTH);
    }

    @Test
    public void deleteAllocation() throws Exception {
        // Initialize the database
        allocationRepository.save(allocation);

		int databaseSizeBeforeDelete = allocationRepository.findAll().size();

        // Get the allocation
        restAllocationMockMvc.perform(delete("/api/allocations/{id}", allocation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Allocation> allocations = allocationRepository.findAll();
        assertThat(allocations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
