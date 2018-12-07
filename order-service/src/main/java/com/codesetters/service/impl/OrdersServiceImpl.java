package com.codesetters.service.impl;

import com.codesetters.domain.OrderStatus;
import com.codesetters.domain.PaymentStatus;
import com.codesetters.repository.OrderItemRepository;
import com.codesetters.service.OrdersService;
import com.codesetters.domain.Orders;
import com.codesetters.repository.OrdersRepository;
import com.codesetters.service.UserService;
import com.codesetters.service.dto.*;
import com.codesetters.service.mapper.OrderItemMapper;
import com.codesetters.service.mapper.OrdersMapper;
import com.codesetters.web.rest.errors.BadRequestAlertException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Orders.
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final UserService userService;

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper, UserService userService, OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);

        Orders orders = ordersMapper.toEntity(ordersDTO);
        orders = ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    @Override
    public List<OrdersDTO> findAll() {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll().stream()
            .map(ordersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orders by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<OrdersDTO> findOne(UUID id) {
        log.debug("Request to get Orders : {}", id);
        return ordersRepository.findById(id)
            .map(ordersMapper::toDto);
    }


    @Override
    public Order createOrder(Order order) {
        try {
            UserDTO userDTO = userService.getUserWithAuthorities(order.getOrderInfo().getUserId());
            order.getOrderInfo().setUserId(userDTO.getId());
            order.getOrderInfo().setStatus(OrderStatus.RECIEVED.name());
            order.getOrderInfo().setCreatedAt(Instant.now());
            order.getOrderInfo().setId(UUID.randomUUID());
            order.getOrderInfo().setPaymentStatus(order.getPaymentStatus().name());
            order = calculateCost(order);
            this.ordersRepository.save(ordersMapper.toEntity(order.getOrderInfo()));
        } catch (HystrixRuntimeException he) {
            if (he.getCause() instanceof FeignException) {
                if (((FeignException) he.getCause()).status() == 404) {
                    throw new BadRequestAlertException("User Not Found","orders","userNotFound");
                }
            }
        }
        return order;
    }

    @Override
    public OrdersDTO updateOrderStatus(OrderUpdateStatusDTO updateStatusDTO) {
        Optional<Orders> existingOrder = this.ordersRepository.findById(updateStatusDTO.getOrderId());
        if (!existingOrder.isPresent()) {
            throw new BadRequestAlertException("Order not found","orders","notFound");
        }
        Orders orders = existingOrder.get();
        orders.setStatus(updateStatusDTO.getOrderStatus().name());

        if (updateStatusDTO.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            if (updateStatusDTO.getPaymentId() == null){
                throw new BadRequestAlertException("Invalid Payment id for a Completed Payment","orders","invalidPaymentId");
            }
            //Find Payment from payment service and check whether payment is completed or not
            //if payment is completed, then
            orders.setPaymentStatus(updateStatusDTO.getPaymentStatus().name());
        }
        orders = this.ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }


    private Order calculateCost(Order order){
        double totalCost = 0;
        for (OrderItemDTO orederdItem : order.getItemsInfo()){
            totalCost = totalCost + orederdItem.getItemPrice() * orederdItem.getItemQuantity();
            orederdItem.setId(UUID.randomUUID());

            orederdItem.setOrderId(order.getOrderInfo().getId());
            this.orderItemRepository.save(orderItemMapper.toEntity(orederdItem));
        }
        order.getOrderInfo().setTotalPrice(totalCost);

        return order;
    }



    /**
     * Delete the orders by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
    }
}
