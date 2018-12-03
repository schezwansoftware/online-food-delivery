package com.codesetters.restaurantservice.web.rest;

import com.codesetters.restaurantservice.AbstractCassandraTest;
import com.codesetters.restaurantservice.RestaurantServiceApp;

import com.codesetters.restaurantservice.config.SecurityBeanOverrideConfiguration;

import com.codesetters.restaurantservice.domain.RestaurantSchedule;
import com.codesetters.restaurantservice.repository.RestaurantScheduleRepository;
import com.codesetters.restaurantservice.service.RestaurantScheduleService;
import com.codesetters.restaurantservice.service.dto.RestaurantScheduleDTO;
import com.codesetters.restaurantservice.service.mapper.RestaurantScheduleMapper;
import com.codesetters.restaurantservice.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import java.util.UUID;

import static com.codesetters.restaurantservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestaurantScheduleResource REST controller.
 *
 * @see RestaurantScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RestaurantServiceApp.class})
public class RestaurantScheduleResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_RESTAURANT_ID = UUID.randomUUID();
    private static final UUID UPDATED_RESTAURANT_ID = UUID.randomUUID();

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_OPENING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CLOSING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CLOSING_TIME = "BBBBBBBBBB";

    @Autowired
    private RestaurantScheduleRepository restaurantScheduleRepository;

    @Autowired
    private RestaurantScheduleMapper restaurantScheduleMapper;
    
    @Autowired
    private RestaurantScheduleService restaurantScheduleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRestaurantScheduleMockMvc;

    private RestaurantSchedule restaurantSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestaurantScheduleResource restaurantScheduleResource = new RestaurantScheduleResource(restaurantScheduleService);
        this.restRestaurantScheduleMockMvc = MockMvcBuilders.standaloneSetup(restaurantScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestaurantSchedule createEntity() {
        RestaurantSchedule restaurantSchedule = new RestaurantSchedule()
            .restaurantId(DEFAULT_RESTAURANT_ID)
            .day(DEFAULT_DAY)
            .openingTime(DEFAULT_OPENING_TIME)
            .closingTime(DEFAULT_CLOSING_TIME);
        return restaurantSchedule;
    }

    @Before
    public void initTest() {
        restaurantScheduleRepository.deleteAll();
        restaurantSchedule = createEntity();
    }

    @Test
    public void createRestaurantSchedule() throws Exception {
        int databaseSizeBeforeCreate = restaurantScheduleRepository.findAll().size();

        // Create the RestaurantSchedule
        RestaurantScheduleDTO restaurantScheduleDTO = restaurantScheduleMapper.toDto(restaurantSchedule);
        restRestaurantScheduleMockMvc.perform(post("/api/restaurant-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the RestaurantSchedule in the database
        List<RestaurantSchedule> restaurantScheduleList = restaurantScheduleRepository.findAll();
        assertThat(restaurantScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        RestaurantSchedule testRestaurantSchedule = restaurantScheduleList.get(restaurantScheduleList.size() - 1);
        assertThat(testRestaurantSchedule.getRestaurantId()).isEqualTo(DEFAULT_RESTAURANT_ID);
        assertThat(testRestaurantSchedule.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testRestaurantSchedule.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testRestaurantSchedule.getClosingTime()).isEqualTo(DEFAULT_CLOSING_TIME);
    }

    @Test
    public void createRestaurantScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantScheduleRepository.findAll().size();

        // Create the RestaurantSchedule with an existing ID
        restaurantSchedule.setId(UUID.randomUUID());
        RestaurantScheduleDTO restaurantScheduleDTO = restaurantScheduleMapper.toDto(restaurantSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantScheduleMockMvc.perform(post("/api/restaurant-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantSchedule in the database
        List<RestaurantSchedule> restaurantScheduleList = restaurantScheduleRepository.findAll();
        assertThat(restaurantScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRestaurantSchedules() throws Exception {
        // Initialize the database
        restaurantSchedule.setId(UUID.randomUUID());
        restaurantScheduleRepository.save(restaurantSchedule);

        // Get all the restaurantScheduleList
        restRestaurantScheduleMockMvc.perform(get("/api/restaurant-schedules"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurantSchedule.getId().toString())))
            .andExpect(jsonPath("$.[*].restaurantId").value(hasItem(DEFAULT_RESTAURANT_ID.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME.toString())))
            .andExpect(jsonPath("$.[*].closingTime").value(hasItem(DEFAULT_CLOSING_TIME.toString())));
    }
    
    @Test
    public void getRestaurantSchedule() throws Exception {
        // Initialize the database
        restaurantSchedule.setId(UUID.randomUUID());
        restaurantScheduleRepository.save(restaurantSchedule);

        // Get the restaurantSchedule
        restRestaurantScheduleMockMvc.perform(get("/api/restaurant-schedules/{id}", restaurantSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurantSchedule.getId().toString()))
            .andExpect(jsonPath("$.restaurantId").value(DEFAULT_RESTAURANT_ID.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.openingTime").value(DEFAULT_OPENING_TIME.toString()))
            .andExpect(jsonPath("$.closingTime").value(DEFAULT_CLOSING_TIME.toString()));
    }

    @Test
    public void getNonExistingRestaurantSchedule() throws Exception {
        // Get the restaurantSchedule
        restRestaurantScheduleMockMvc.perform(get("/api/restaurant-schedules/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRestaurantSchedule() throws Exception {
        // Initialize the database
        restaurantSchedule.setId(UUID.randomUUID());
        restaurantScheduleRepository.save(restaurantSchedule);

        int databaseSizeBeforeUpdate = restaurantScheduleRepository.findAll().size();

        // Update the restaurantSchedule
        RestaurantSchedule updatedRestaurantSchedule = restaurantScheduleRepository.findById(restaurantSchedule.getId()).get();
        updatedRestaurantSchedule
            .restaurantId(UPDATED_RESTAURANT_ID)
            .day(UPDATED_DAY)
            .openingTime(UPDATED_OPENING_TIME)
            .closingTime(UPDATED_CLOSING_TIME);
        RestaurantScheduleDTO restaurantScheduleDTO = restaurantScheduleMapper.toDto(updatedRestaurantSchedule);

        restRestaurantScheduleMockMvc.perform(put("/api/restaurant-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the RestaurantSchedule in the database
        List<RestaurantSchedule> restaurantScheduleList = restaurantScheduleRepository.findAll();
        assertThat(restaurantScheduleList).hasSize(databaseSizeBeforeUpdate);
        RestaurantSchedule testRestaurantSchedule = restaurantScheduleList.get(restaurantScheduleList.size() - 1);
        assertThat(testRestaurantSchedule.getRestaurantId()).isEqualTo(UPDATED_RESTAURANT_ID);
        assertThat(testRestaurantSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testRestaurantSchedule.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testRestaurantSchedule.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
    }

    @Test
    public void updateNonExistingRestaurantSchedule() throws Exception {
        int databaseSizeBeforeUpdate = restaurantScheduleRepository.findAll().size();

        // Create the RestaurantSchedule
        RestaurantScheduleDTO restaurantScheduleDTO = restaurantScheduleMapper.toDto(restaurantSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantScheduleMockMvc.perform(put("/api/restaurant-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestaurantSchedule in the database
        List<RestaurantSchedule> restaurantScheduleList = restaurantScheduleRepository.findAll();
        assertThat(restaurantScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRestaurantSchedule() throws Exception {
        // Initialize the database
        restaurantSchedule.setId(UUID.randomUUID());
        restaurantScheduleRepository.save(restaurantSchedule);

        int databaseSizeBeforeDelete = restaurantScheduleRepository.findAll().size();

        // Get the restaurantSchedule
        restRestaurantScheduleMockMvc.perform(delete("/api/restaurant-schedules/{id}", restaurantSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RestaurantSchedule> restaurantScheduleList = restaurantScheduleRepository.findAll();
        assertThat(restaurantScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantSchedule.class);
        RestaurantSchedule restaurantSchedule1 = new RestaurantSchedule();
        restaurantSchedule1.setId(UUID.randomUUID());
        RestaurantSchedule restaurantSchedule2 = new RestaurantSchedule();
        restaurantSchedule2.setId(restaurantSchedule1.getId());
        assertThat(restaurantSchedule1).isEqualTo(restaurantSchedule2);
        restaurantSchedule2.setId(UUID.randomUUID());
        assertThat(restaurantSchedule1).isNotEqualTo(restaurantSchedule2);
        restaurantSchedule1.setId(null);
        assertThat(restaurantSchedule1).isNotEqualTo(restaurantSchedule2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantScheduleDTO.class);
        RestaurantScheduleDTO restaurantScheduleDTO1 = new RestaurantScheduleDTO();
        restaurantScheduleDTO1.setId(UUID.randomUUID());
        RestaurantScheduleDTO restaurantScheduleDTO2 = new RestaurantScheduleDTO();
        assertThat(restaurantScheduleDTO1).isNotEqualTo(restaurantScheduleDTO2);
        restaurantScheduleDTO2.setId(restaurantScheduleDTO1.getId());
        assertThat(restaurantScheduleDTO1).isEqualTo(restaurantScheduleDTO2);
        restaurantScheduleDTO2.setId(UUID.randomUUID());
        assertThat(restaurantScheduleDTO1).isNotEqualTo(restaurantScheduleDTO2);
        restaurantScheduleDTO1.setId(null);
        assertThat(restaurantScheduleDTO1).isNotEqualTo(restaurantScheduleDTO2);
    }
}
