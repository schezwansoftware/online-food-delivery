package com.codesetters.restaurantservice.service;

import com.codesetters.restaurantservice.service.dto.RestaurantDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing Restaurant.
 */
public interface RestaurantService {

    /**
     * Save a restaurant.
     *
     * @param restaurantDTO the entity to save
     * @return the persisted entity
     */
    RestaurantDTO save(RestaurantDTO restaurantDTO);

    /**
     * Get all the restaurants.
     *
     * @return the list of entities
     */
    List<RestaurantDTO> findAll();


    /**
     * Get the "id" restaurant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RestaurantDTO> findOne(UUID id);

    /**
     * Delete the "id" restaurant.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
