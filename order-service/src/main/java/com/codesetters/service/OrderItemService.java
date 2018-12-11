package com.codesetters.service;

import com.codesetters.service.dto.OrderItemDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing OrderItem.
 */
public interface OrderItemService {

    /**
     * Save a orderItem.
     *
     * @param orderItemDTO the entity to save
     * @return the persisted entity
     */
    OrderItemDTO save(OrderItemDTO orderItemDTO);

    /**
     * Get all the orderItems.
     *
     * @return the list of entities
     */
    List<OrderItemDTO> findAll();


    /**
     * Get the "id" orderItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderItemDTO> findOne(UUID id);

    /**
     * Delete the "id" orderItem.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
