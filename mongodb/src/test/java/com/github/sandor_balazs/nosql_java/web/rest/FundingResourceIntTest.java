package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.Funding;
import com.github.sandor_balazs.nosql_java.repository.FundingRepository;
import com.github.sandor_balazs.nosql_java.service.FundingService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.FundingDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.FundingMapper;

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
 * Test class for the FundingResource REST controller.
 *
 * @see FundingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FundingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private FundingRepository fundingRepository;

    @Inject
    private FundingMapper fundingMapper;

    @Inject
    private FundingService fundingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFundingMockMvc;

    private Funding funding;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FundingResource fundingResource = new FundingResource();
        ReflectionTestUtils.setField(fundingResource, "fundingService", fundingService);
        ReflectionTestUtils.setField(fundingResource, "fundingMapper", fundingMapper);
        this.restFundingMockMvc = MockMvcBuilders.standaloneSetup(fundingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fundingRepository.deleteAll();
        funding = new Funding();
        funding.setName(DEFAULT_NAME);
    }

    @Test
    public void createFunding() throws Exception {
        int databaseSizeBeforeCreate = fundingRepository.findAll().size();

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.fundingToFundingDTO(funding);

        restFundingMockMvc.perform(post("/api/fundings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fundingDTO)))
                .andExpect(status().isCreated());

        // Validate the Funding in the database
        List<Funding> fundings = fundingRepository.findAll();
        assertThat(fundings).hasSize(databaseSizeBeforeCreate + 1);
        Funding testFunding = fundings.get(fundings.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void getAllFundings() throws Exception {
        // Initialize the database
        fundingRepository.save(funding);

        // Get all the fundings
        restFundingMockMvc.perform(get("/api/fundings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(funding.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getFunding() throws Exception {
        // Initialize the database
        fundingRepository.save(funding);

        // Get the funding
        restFundingMockMvc.perform(get("/api/fundings/{id}", funding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(funding.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingFunding() throws Exception {
        // Get the funding
        restFundingMockMvc.perform(get("/api/fundings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFunding() throws Exception {
        // Initialize the database
        fundingRepository.save(funding);

		int databaseSizeBeforeUpdate = fundingRepository.findAll().size();

        // Update the funding
        funding.setName(UPDATED_NAME);
        FundingDTO fundingDTO = fundingMapper.fundingToFundingDTO(funding);

        restFundingMockMvc.perform(put("/api/fundings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fundingDTO)))
                .andExpect(status().isOk());

        // Validate the Funding in the database
        List<Funding> fundings = fundingRepository.findAll();
        assertThat(fundings).hasSize(databaseSizeBeforeUpdate);
        Funding testFunding = fundings.get(fundings.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void deleteFunding() throws Exception {
        // Initialize the database
        fundingRepository.save(funding);

		int databaseSizeBeforeDelete = fundingRepository.findAll().size();

        // Get the funding
        restFundingMockMvc.perform(delete("/api/fundings/{id}", funding.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Funding> fundings = fundingRepository.findAll();
        assertThat(fundings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
