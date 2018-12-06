package com.codesetters.web.rest;

import com.codesetters.AbstractCassandraTest;
import com.codesetters.OrderServiceApp;

import com.codesetters.config.SecurityBeanOverrideConfiguration;

import com.codesetters.domain.Orders;
import com.codesetters.repository.OrdersRepository;
import com.codesetters.service.OrdersService;
import com.codesetters.service.dto.OrdersDTO;
import com.codesetters.service.mapper.OrdersMapper;
import com.codesetters.web.rest.errors.ExceptionTranslator;

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

import static com.codesetters.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, OrderServiceApp.class})
public class OrdersResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_RESTAURANT_ID = UUID.randomUUID();
    private static final UUID UPDATED_RESTAURANT_ID = UUID.randomUUID();

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;

    private static final String DEFAULT_DELIVERY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_NOTE = "BBBBBBBBBB";

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;
    
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdersResource ordersResource = new OrdersResource(ordersService);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
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
    public static Orders createEntity() {
        Orders orders = new Orders()
            .restaurantId(DEFAULT_RESTAURANT_ID)
            .userId(DEFAULT_USER_ID)
            .status(DEFAULT_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .deliveryAddress(DEFAULT_DELIVERY_ADDRESS)
            .specialNote(DEFAULT_SPECIAL_NOTE);
        return orders;
    }

    @Before
    public void initTest() {
        ordersRepository.deleteAll();
        orders = createEntity();
    }

    @Test
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getRestaurantId()).isEqualTo(DEFAULT_RESTAURANT_ID);
        assertThat(testOrders.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testOrders.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrders.getDeliveryAddress()).isEqualTo(DEFAULT_DELIVERY_ADDRESS);
        assertThat(testOrders.getSpecialNote()).isEqualTo(DEFAULT_SPECIAL_NOTE);
    }

    @Test
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(UUID.randomUUID());
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRestaurantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setRestaurantId(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setUserId(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setStatus(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaymentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setPaymentStatus(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        orders.setId(UUID.randomUUID());
        ordersRepository.save(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().toString())))
            .andExpect(jsonPath("$.[*].restaurantId").value(hasItem(DEFAULT_RESTAURANT_ID.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].specialNote").value(hasItem(DEFAULT_SPECIAL_NOTE.toString())));
    }
    
    @Test
    public void getOrders() throws Exception {
        // Initialize the database
        orders.setId(UUID.randomUUID());
        ordersRepository.save(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().toString()))
            .andExpect(jsonPath("$.restaurantId").value(DEFAULT_RESTAURANT_ID.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.deliveryAddress").value(DEFAULT_DELIVERY_ADDRESS.toString()))
            .andExpect(jsonPath("$.specialNote").value(DEFAULT_SPECIAL_NOTE.toString()));
    }

    @Test
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrders() throws Exception {
        // Initialize the database
        orders.setId(UUID.randomUUID());
        ordersRepository.save(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findById(orders.getId()).get();
        updatedOrders
            .restaurantId(UPDATED_RESTAURANT_ID)
            .userId(UPDATED_USER_ID)
            .status(UPDATED_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .specialNote(UPDATED_SPECIAL_NOTE);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getRestaurantId()).isEqualTo(UPDATED_RESTAURANT_ID);
        assertThat(testOrders.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrders.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrders.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testOrders.getSpecialNote()).isEqualTo(UPDATED_SPECIAL_NOTE);
    }

    @Test
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOrders() throws Exception {
        // Initialize the database
        orders.setId(UUID.randomUUID());
        ordersRepository.save(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orders.class);
        Orders orders1 = new Orders();
        orders1.setId(UUID.randomUUID());
        Orders orders2 = new Orders();
        orders2.setId(orders1.getId());
        assertThat(orders1).isEqualTo(orders2);
        orders2.setId(UUID.randomUUID());
        assertThat(orders1).isNotEqualTo(orders2);
        orders1.setId(null);
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersDTO.class);
        OrdersDTO ordersDTO1 = new OrdersDTO();
        ordersDTO1.setId(UUID.randomUUID());
        OrdersDTO ordersDTO2 = new OrdersDTO();
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO2.setId(ordersDTO1.getId());
        assertThat(ordersDTO1).isEqualTo(ordersDTO2);
        ordersDTO2.setId(UUID.randomUUID());
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO1.setId(null);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
    }
}
