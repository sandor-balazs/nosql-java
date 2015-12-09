package com.github.sandor_balazs.nosql_java.web.rest;

import com.github.sandor_balazs.nosql_java.AbstractCassandraTest;
import com.github.sandor_balazs.nosql_java.Application;
import com.github.sandor_balazs.nosql_java.domain.Status;
import com.github.sandor_balazs.nosql_java.repository.StatusRepository;
import com.github.sandor_balazs.nosql_java.service.StatusService;
import com.github.sandor_balazs.nosql_java.web.rest.dto.StatusDTO;
import com.github.sandor_balazs.nosql_java.web.rest.mapper.StatusMapper;

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
 * Test class for the StatusResource REST controller.
 *
 * @see StatusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StatusResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_APP_ORDER = 1;
    private static final Integer UPDATED_APP_ORDER = 2;

    @Inject
    private StatusRepository statusRepository;

    @Inject
    private StatusMapper statusMapper;

    @Inject
    private StatusService statusService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStatusMockMvc;

    private Status status;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatusResource statusResource = new StatusResource();
        ReflectionTestUtils.setField(statusResource, "statusService", statusService);
        ReflectionTestUtils.setField(statusResource, "statusMapper", statusMapper);
        this.restStatusMockMvc = MockMvcBuilders.standaloneSetup(statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        statusRepository.deleteAll();
        status = new Status();
        status.setName(DEFAULT_NAME);
        status.setAppOrder(DEFAULT_APP_ORDER);
    }

    @Test
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status
        StatusDTO statusDTO = statusMapper.statusToStatusDTO(status);

        restStatusMockMvc.perform(post("/api/statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statusDTO)))
                .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statuss = statusRepository.findAll();
        assertThat(statuss).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statuss.get(statuss.size() - 1);
        assertThat(testStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatus.getAppOrder()).isEqualTo(DEFAULT_APP_ORDER);
    }

    @Test
    public void getAllStatuss() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get all the statuss
        restStatusMockMvc.perform(get("/api/statuss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].appOrder").value(hasItem(DEFAULT_APP_ORDER)));
    }

    @Test
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuss/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(status.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.appOrder").value(DEFAULT_APP_ORDER));
    }

    @Test
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuss/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

		int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        status.setName(UPDATED_NAME);
        status.setAppOrder(UPDATED_APP_ORDER);
        StatusDTO statusDTO = statusMapper.statusToStatusDTO(status);

        restStatusMockMvc.perform(put("/api/statuss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statusDTO)))
                .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statuss = statusRepository.findAll();
        assertThat(statuss).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statuss.get(statuss.size() - 1);
        assertThat(testStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatus.getAppOrder()).isEqualTo(UPDATED_APP_ORDER);
    }

    @Test
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

		int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Get the status
        restStatusMockMvc.perform(delete("/api/statuss/{id}", status.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Status> statuss = statusRepository.findAll();
        assertThat(statuss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
