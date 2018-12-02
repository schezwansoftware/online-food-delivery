package com.codesetters.restaurantservice.service;

import com.codesetters.restaurantservice.service.dto.DishesDTO;
import java.util.List;

/**
 * Service Interface for managing Dishes.
 */
public interface DishesService {

    /**
     * Save a dishes.
     *
     * @param dishesDTO the entity to save
     * @return the persisted entity
     */
    DishesDTO save(DishesDTO dishesDTO);

    /**
     *  Get all the dishes.
     *
     *  @return the list of entities
     */
    List<DishesDTO> findAll();

    /**
     *  Get the "id" dishes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DishesDTO findOne(String id);

    /**
     *  Delete the "id" dishes.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
