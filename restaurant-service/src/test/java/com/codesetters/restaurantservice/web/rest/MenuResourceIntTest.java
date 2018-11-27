package com.codesetters.restaurantservice.web.rest;

import com.codesetters.restaurantservice.AbstractCassandraTest;
import com.codesetters.restaurantservice.RestaurantServiceApp;

import com.codesetters.restaurantservice.config.SecurityBeanOverrideConfiguration;

import com.codesetters.restaurantservice.domain.Menu;
import com.codesetters.restaurantservice.repository.MenuRepository;
import com.codesetters.restaurantservice.service.MenuService;
import com.codesetters.restaurantservice.service.dto.MenuDTO;
import com.codesetters.restaurantservice.service.mapper.MenuMapper;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MenuResource REST controller.
 *
 * @see MenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestaurantServiceApp.class, SecurityBeanOverrideConfiguration.class})
public class MenuResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_RESTAURANT_ID = UUID.randomUUID();
    private static final UUID UPDATED_RESTAURANT_ID = UUID.randomUUID();

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMenuMockMvc;

    private Menu menu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenuResource menuResource = new MenuResource(menuService);
        this.restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
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
    public static Menu createEntity() {
        Menu menu = new Menu()
            .restaurantId(DEFAULT_RESTAURANT_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return menu;
    }

    @Before
    public void initTest() {
        menuRepository.deleteAll();
        menu = createEntity();
    }

    @Test
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getRestaurantId()).isEqualTo(DEFAULT_RESTAURANT_ID);
        assertThat(testMenu.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMenu.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    public void createMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu with an existing ID
        menu.setId(UUID.randomUUID());
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.save(menu);

        // Get all the menuList
        restMenuMockMvc.perform(get("/api/menus"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().toString())))
            .andExpect(jsonPath("$.[*].restaurantId").value(hasItem(DEFAULT_RESTAURANT_ID.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.save(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().toString()))
            .andExpect(jsonPath("$.restaurantId").value(DEFAULT_RESTAURANT_ID.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMenu() throws Exception {
        // Initialize the database
        menuRepository.save(menu);
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findOne(menu.getId());
        updatedMenu
            .restaurantId(UPDATED_RESTAURANT_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        MenuDTO menuDTO = menuMapper.toDto(updatedMenu);

        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getRestaurantId()).isEqualTo(UPDATED_RESTAURANT_ID);
        assertThat(testMenu.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMenu.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void updateNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Create the Menu
        MenuDTO menuDTO = menuMapper.toDto(menu);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menuDTO)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuRepository.save(menu);
        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Get the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = new Menu();
        menu1.setId(UUID.randomUUID());
        Menu menu2 = new Menu();
        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);
        menu2.setId(UUID.randomUUID());
        assertThat(menu1).isNotEqualTo(menu2);
        menu1.setId(null);
        assertThat(menu1).isNotEqualTo(menu2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuDTO.class);
        MenuDTO menuDTO1 = new MenuDTO();
        menuDTO1.setId(UUID.randomUUID());
        MenuDTO menuDTO2 = new MenuDTO();
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
        menuDTO2.setId(menuDTO1.getId());
        assertThat(menuDTO1).isEqualTo(menuDTO2);
        menuDTO2.setId(UUID.randomUUID());
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
        menuDTO1.setId(null);
        assertThat(menuDTO1).isNotEqualTo(menuDTO2);
    }
}
