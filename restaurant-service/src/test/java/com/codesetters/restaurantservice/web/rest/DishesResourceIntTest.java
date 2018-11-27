package com.codesetters.restaurantservice.web.rest;

import com.codesetters.restaurantservice.AbstractCassandraTest;
import com.codesetters.restaurantservice.RestaurantServiceApp;

import com.codesetters.restaurantservice.config.SecurityBeanOverrideConfiguration;

import com.codesetters.restaurantservice.domain.Dishes;
import com.codesetters.restaurantservice.repository.DishesRepository;
import com.codesetters.restaurantservice.service.DishesService;
import com.codesetters.restaurantservice.service.dto.DishesDTO;
import com.codesetters.restaurantservice.service.mapper.DishesMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DishesResource REST controller.
 *
 * @see DishesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestaurantServiceApp.class, SecurityBeanOverrideConfiguration.class})
public class DishesResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_MENU_ID = UUID.randomUUID();
    private static final UUID UPDATED_MENU_ID = UUID.randomUUID();

    private static final String DEFAULT_DISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISH_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISH_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISH_PRICE = 1D;
    private static final Double UPDATED_DISH_PRICE = 2D;

    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private DishesMapper dishesMapper;

    @Autowired
    private DishesService dishesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDishesMockMvc;

    private Dishes dishes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DishesResource dishesResource = new DishesResource(dishesService);
        this.restDishesMockMvc = MockMvcBuilders.standaloneSetup(dishesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dishes createEntity() {
        Dishes dishes = new Dishes()
            .menuId(DEFAULT_MENU_ID)
            .dishName(DEFAULT_DISH_NAME)
            .dishType(DEFAULT_DISH_TYPE)
            .dishPrice(DEFAULT_DISH_PRICE);
        return dishes;
    }

    @Before
    public void initTest() {
        dishesRepository.deleteAll();
        dishes = createEntity();
    }

    @Test
    public void createDishes() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isCreated());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate + 1);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getMenuId()).isEqualTo(DEFAULT_MENU_ID);
        assertThat(testDishes.getDishName()).isEqualTo(DEFAULT_DISH_NAME);
        assertThat(testDishes.getDishType()).isEqualTo(DEFAULT_DISH_TYPE);
        assertThat(testDishes.getDishPrice()).isEqualTo(DEFAULT_DISH_PRICE);
    }

    @Test
    public void createDishesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishesRepository.findAll().size();

        // Create the Dishes with an existing ID
        dishes.setId(UUID.randomUUID());
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishesMockMvc.perform(post("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDishes() throws Exception {
        // Initialize the database
        dishesRepository.save(dishes);

        // Get all the dishesList
        restDishesMockMvc.perform(get("/api/dishes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dishes.getId().toString())))
            .andExpect(jsonPath("$.[*].menuId").value(hasItem(DEFAULT_MENU_ID.toString())))
            .andExpect(jsonPath("$.[*].dishName").value(hasItem(DEFAULT_DISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].dishType").value(hasItem(DEFAULT_DISH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dishPrice").value(hasItem(DEFAULT_DISH_PRICE.doubleValue())));
    }

    @Test
    public void getDishes() throws Exception {
        // Initialize the database
        dishesRepository.save(dishes);

        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", dishes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dishes.getId().toString()))
            .andExpect(jsonPath("$.menuId").value(DEFAULT_MENU_ID.toString()))
            .andExpect(jsonPath("$.dishName").value(DEFAULT_DISH_NAME.toString()))
            .andExpect(jsonPath("$.dishType").value(DEFAULT_DISH_TYPE.toString()))
            .andExpect(jsonPath("$.dishPrice").value(DEFAULT_DISH_PRICE.doubleValue()));
    }

    @Test
    public void getNonExistingDishes() throws Exception {
        // Get the dishes
        restDishesMockMvc.perform(get("/api/dishes/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDishes() throws Exception {
        // Initialize the database
        dishesRepository.save(dishes);
        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Update the dishes
        Dishes updatedDishes = dishesRepository.findOne(dishes.getId());
        updatedDishes
            .menuId(UPDATED_MENU_ID)
            .dishName(UPDATED_DISH_NAME)
            .dishType(UPDATED_DISH_TYPE)
            .dishPrice(UPDATED_DISH_PRICE);
        DishesDTO dishesDTO = dishesMapper.toDto(updatedDishes);

        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isOk());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate);
        Dishes testDishes = dishesList.get(dishesList.size() - 1);
        assertThat(testDishes.getMenuId()).isEqualTo(UPDATED_MENU_ID);
        assertThat(testDishes.getDishName()).isEqualTo(UPDATED_DISH_NAME);
        assertThat(testDishes.getDishType()).isEqualTo(UPDATED_DISH_TYPE);
        assertThat(testDishes.getDishPrice()).isEqualTo(UPDATED_DISH_PRICE);
    }

    @Test
    public void updateNonExistingDishes() throws Exception {
        int databaseSizeBeforeUpdate = dishesRepository.findAll().size();

        // Create the Dishes
        DishesDTO dishesDTO = dishesMapper.toDto(dishes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDishesMockMvc.perform(put("/api/dishes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dishesDTO)))
            .andExpect(status().isCreated());

        // Validate the Dishes in the database
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDishes() throws Exception {
        // Initialize the database
        dishesRepository.save(dishes);
        int databaseSizeBeforeDelete = dishesRepository.findAll().size();

        // Get the dishes
        restDishesMockMvc.perform(delete("/api/dishes/{id}", dishes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dishes> dishesList = dishesRepository.findAll();
        assertThat(dishesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dishes.class);
        Dishes dishes1 = new Dishes();
        dishes1.setId(UUID.randomUUID());
        Dishes dishes2 = new Dishes();
        dishes2.setId(dishes1.getId());
        assertThat(dishes1).isEqualTo(dishes2);
        dishes2.setId(UUID.randomUUID());
        assertThat(dishes1).isNotEqualTo(dishes2);
        dishes1.setId(null);
        assertThat(dishes1).isNotEqualTo(dishes2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishesDTO.class);
        DishesDTO dishesDTO1 = new DishesDTO();
        dishesDTO1.setId(UUID.randomUUID());
        DishesDTO dishesDTO2 = new DishesDTO();
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO2.setId(dishesDTO1.getId());
        assertThat(dishesDTO1).isEqualTo(dishesDTO2);
        dishesDTO2.setId(UUID.randomUUID());
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
        dishesDTO1.setId(null);
        assertThat(dishesDTO1).isNotEqualTo(dishesDTO2);
    }
}
