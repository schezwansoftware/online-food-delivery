package com.codesetters.restaurantservice.service;

import com.codesetters.restaurantservice.service.dto.RestaurantScheduleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing RestaurantSchedule.
 */
public interface RestaurantScheduleService {

    /**
     * Save a restaurantSchedule.
     *
     * @param restaurantScheduleDTO the entity to save
     * @return the persisted entity
     */
    RestaurantScheduleDTO save(RestaurantScheduleDTO restaurantScheduleDTO);

    /**
     * Get all the restaurantSchedules.
     *
     * @return the list of entities
     */
    List<RestaurantScheduleDTO> findAll();


    /**
     * Get the "id" restaurantSchedule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RestaurantScheduleDTO> findOne(UUID id);

    /**
     * Delete the "id" restaurantSchedule.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
