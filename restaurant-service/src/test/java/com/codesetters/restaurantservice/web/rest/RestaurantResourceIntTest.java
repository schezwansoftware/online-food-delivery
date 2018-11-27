package com.codesetters.restaurantservice.web.rest;

import com.codesetters.restaurantservice.AbstractCassandraTest;
import com.codesetters.restaurantservice.RestaurantServiceApp;

import com.codesetters.restaurantservice.config.SecurityBeanOverrideConfiguration;

import com.codesetters.restaurantservice.domain.Restaurant;
import com.codesetters.restaurantservice.repository.RestaurantRepository;
import com.codesetters.restaurantservice.service.RestaurantService;
import com.codesetters.restaurantservice.service.dto.RestaurantDTO;
import com.codesetters.restaurantservice.service.mapper.RestaurantMapper;
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

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import java.util.UUID;

import static com.codesetters.restaurantservice.web.rest.TestUtil.sameInstant;
import static com.codesetters.restaurantservice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestaurantResource REST controller.
 *
 * @see RestaurantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, RestaurantServiceApp.class})
public class RestaurantResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_CUISINE_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_CUISINE_TYPES = "BBBBBBBBBB";

    private static final UUID DEFAULT_CURRENT_MENU_ID = UUID.randomUUID();
    private static final UUID UPDATED_CURRENT_MENU_ID = UUID.randomUUID();

    private static final String DEFAULT_EXECUTIVE_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTIVE_LOGIN = "BBBBBBBBBB";

    private static final UUID DEFAULT_LOCATION_ID = UUID.randomUUID();
    private static final UUID UPDATED_LOCATION_ID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REGISTRATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REGISTRATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;
    
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestaurantResource restaurantResource = new RestaurantResource(restaurantService);
        this.restRestaurantMockMvc = MockMvcBuilders.standaloneSetup(restaurantResource)
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
    public static Restaurant createEntity() {
        Restaurant restaurant = new Restaurant()
            .cuisineTypes(DEFAULT_CUISINE_TYPES)
            .currentMenuId(DEFAULT_CURRENT_MENU_ID)
            .executiveLogin(DEFAULT_EXECUTIVE_LOGIN)
            .locationId(DEFAULT_LOCATION_ID)
            .name(DEFAULT_NAME)
            .registrationDate(DEFAULT_REGISTRATION_DATE);
        return restaurant;
    }

    @Before
    public void initTest() {
        restaurantRepository.deleteAll();
        restaurant = createEntity();
    }

    @Test
    public void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getCuisineTypes()).isEqualTo(DEFAULT_CUISINE_TYPES);
        assertThat(testRestaurant.getCurrentMenuId()).isEqualTo(DEFAULT_CURRENT_MENU_ID);
        assertThat(testRestaurant.getExecutiveLogin()).isEqualTo(DEFAULT_EXECUTIVE_LOGIN);
        assertThat(testRestaurant.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testRestaurant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRestaurant.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
    }

    @Test
    public void createRestaurantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant with an existing ID
        restaurant.setId(UUID.randomUUID());
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurant.setId(UUID.randomUUID());
        restaurantRepository.save(restaurant);

        // Get all the restaurantList
        restRestaurantMockMvc.perform(get("/api/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().toString())))
            .andExpect(jsonPath("$.[*].cuisineTypes").value(hasItem(DEFAULT_CUISINE_TYPES.toString())))
            .andExpect(jsonPath("$.[*].currentMenuId").value(hasItem(DEFAULT_CURRENT_MENU_ID.toString())))
            .andExpect(jsonPath("$.[*].executiveLogin").value(hasItem(DEFAULT_EXECUTIVE_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(sameInstant(DEFAULT_REGISTRATION_DATE))));
    }
    
    @Test
    public void getRestaurant() throws Exception {
        // Initialize the database
        restaurant.setId(UUID.randomUUID());
        restaurantRepository.save(restaurant);

        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurant.getId().toString()))
            .andExpect(jsonPath("$.cuisineTypes").value(DEFAULT_CUISINE_TYPES.toString()))
            .andExpect(jsonPath("$.currentMenuId").value(DEFAULT_CURRENT_MENU_ID.toString()))
            .andExpect(jsonPath("$.executiveLogin").value(DEFAULT_EXECUTIVE_LOGIN.toString()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.registrationDate").value(sameInstant(DEFAULT_REGISTRATION_DATE)));
    }

    @Test
    public void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRestaurant() throws Exception {
        // Initialize the database
        restaurant.setId(UUID.randomUUID());
        restaurantRepository.save(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        Restaurant updatedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        updatedRestaurant
            .cuisineTypes(UPDATED_CUISINE_TYPES)
            .currentMenuId(UPDATED_CURRENT_MENU_ID)
            .executiveLogin(UPDATED_EXECUTIVE_LOGIN)
            .locationId(UPDATED_LOCATION_ID)
            .name(UPDATED_NAME)
            .registrationDate(UPDATED_REGISTRATION_DATE);
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(updatedRestaurant);

        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getCuisineTypes()).isEqualTo(UPDATED_CUISINE_TYPES);
        assertThat(testRestaurant.getCurrentMenuId()).isEqualTo(UPDATED_CURRENT_MENU_ID);
        assertThat(testRestaurant.getExecutiveLogin()).isEqualTo(UPDATED_EXECUTIVE_LOGIN);
        assertThat(testRestaurant.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testRestaurant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRestaurant.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
    }

    @Test
    public void updateNonExistingRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Create the Restaurant
        RestaurantDTO restaurantDTO = restaurantMapper.toDto(restaurant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurant.setId(UUID.randomUUID());
        restaurantRepository.save(restaurant);

        int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Get the restaurant
        restRestaurantMockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restaurant.class);
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(UUID.randomUUID());
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(restaurant1.getId());
        assertThat(restaurant1).isEqualTo(restaurant2);
        restaurant2.setId(UUID.randomUUID());
        assertThat(restaurant1).isNotEqualTo(restaurant2);
        restaurant1.setId(null);
        assertThat(restaurant1).isNotEqualTo(restaurant2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestaurantDTO.class);
        RestaurantDTO restaurantDTO1 = new RestaurantDTO();
        restaurantDTO1.setId(UUID.randomUUID());
        RestaurantDTO restaurantDTO2 = new RestaurantDTO();
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
        restaurantDTO2.setId(restaurantDTO1.getId());
        assertThat(restaurantDTO1).isEqualTo(restaurantDTO2);
        restaurantDTO2.setId(UUID.randomUUID());
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
        restaurantDTO1.setId(null);
        assertThat(restaurantDTO1).isNotEqualTo(restaurantDTO2);
    }
}
