package com.codesetters.service;

import com.codesetters.service.dto.OrdersDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing Orders.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    List<OrdersDTO> findAll();


    /**
     * Get the "id" orders.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrdersDTO> findOne(UUID id);

    /**
     * Delete the "id" orders.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
