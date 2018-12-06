package com.codesetters.web.rest;

import com.codesetters.AbstractCassandraTest;
import com.codesetters.OrderServiceApp;

import com.codesetters.config.SecurityBeanOverrideConfiguration;

import com.codesetters.domain.OrderItem;
import com.codesetters.repository.OrderItemRepository;
import com.codesetters.service.OrderItemService;
import com.codesetters.service.dto.OrderItemDTO;
import com.codesetters.service.mapper.OrderItemMapper;
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
 * Test class for the OrderItemResource REST controller.
 *
 * @see OrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, OrderServiceApp.class})
public class OrderItemResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_ORDER_ID = UUID.randomUUID();
    private static final UUID UPDATED_ORDER_ID = UUID.randomUUID();

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final UUID DEFAULT_ITEM_ID = UUID.randomUUID();
    private static final UUID UPDATED_ITEM_ID = UUID.randomUUID();

    private static final Double DEFAULT_ITEM_PRICE = 1D;
    private static final Double UPDATED_ITEM_PRICE = 2D;

    private static final Integer DEFAULT_ITEM_QUANTITY = 1;
    private static final Integer UPDATED_ITEM_QUANTITY = 2;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderItemResource orderItemResource = new OrderItemResource(orderItemService);
        this.restOrderItemMockMvc = MockMvcBuilders.standaloneSetup(orderItemResource)
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
    public static OrderItem createEntity() {
        OrderItem orderItem = new OrderItem()
            .orderId(DEFAULT_ORDER_ID)
            .itemName(DEFAULT_ITEM_NAME)
            .itemId(DEFAULT_ITEM_ID)
            .itemPrice(DEFAULT_ITEM_PRICE)
            .itemQuantity(DEFAULT_ITEM_QUANTITY);
        return orderItem;
    }

    @Before
    public void initTest() {
        orderItemRepository.deleteAll();
        orderItem = createEntity();
    }

    @Test
    public void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrderItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testOrderItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testOrderItem.getItemPrice()).isEqualTo(DEFAULT_ITEM_PRICE);
        assertThat(testOrderItem.getItemQuantity()).isEqualTo(DEFAULT_ITEM_QUANTITY);
    }

    @Test
    public void createOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem with an existing ID
        orderItem.setId(UUID.randomUUID());
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItem.setId(UUID.randomUUID());
        orderItemRepository.save(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc.perform(get("/api/order-items"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().toString())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].itemPrice").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].itemQuantity").value(hasItem(DEFAULT_ITEM_QUANTITY)));
    }
    
    @Test
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItem.setId(UUID.randomUUID());
        orderItemRepository.save(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().toString()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.toString()))
            .andExpect(jsonPath("$.itemPrice").value(DEFAULT_ITEM_PRICE.doubleValue()))
            .andExpect(jsonPath("$.itemQuantity").value(DEFAULT_ITEM_QUANTITY));
    }

    @Test
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItem.setId(UUID.randomUUID());
        orderItemRepository.save(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        updatedOrderItem
            .orderId(UPDATED_ORDER_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemId(UPDATED_ITEM_ID)
            .itemPrice(UPDATED_ITEM_PRICE)
            .itemQuantity(UPDATED_ITEM_QUANTITY);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(updatedOrderItem);

        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrderItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testOrderItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testOrderItem.getItemPrice()).isEqualTo(UPDATED_ITEM_PRICE);
        assertThat(testOrderItem.getItemQuantity()).isEqualTo(UPDATED_ITEM_QUANTITY);
    }

    @Test
    public void updateNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItem.setId(UUID.randomUUID());
        orderItemRepository.save(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Get the orderItem
        restOrderItemMockMvc.perform(delete("/api/order-items/{id}", orderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItem.class);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(UUID.randomUUID());
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(orderItem1.getId());
        assertThat(orderItem1).isEqualTo(orderItem2);
        orderItem2.setId(UUID.randomUUID());
        assertThat(orderItem1).isNotEqualTo(orderItem2);
        orderItem1.setId(null);
        assertThat(orderItem1).isNotEqualTo(orderItem2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItemDTO.class);
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setId(UUID.randomUUID());
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO2.setId(orderItemDTO1.getId());
        assertThat(orderItemDTO1).isEqualTo(orderItemDTO2);
        orderItemDTO2.setId(UUID.randomUUID());
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO1.setId(null);
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
    }
}
