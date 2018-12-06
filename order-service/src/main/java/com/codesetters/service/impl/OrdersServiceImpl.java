package com.codesetters.service.impl;

import com.codesetters.service.OrdersService;
import com.codesetters.domain.Orders;
import com.codesetters.repository.OrdersRepository;
import com.codesetters.service.dto.OrdersDTO;
import com.codesetters.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

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

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
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
