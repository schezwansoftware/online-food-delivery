package com.codesetters.restaurantservice.service;

import com.codesetters.restaurantservice.service.dto.MenuDTO;
import java.util.List;

/**
 * Service Interface for managing Menu.
 */
public interface MenuService {

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save
     * @return the persisted entity
     */
    MenuDTO save(MenuDTO menuDTO);

    /**
     *  Get all the menus.
     *
     *  @return the list of entities
     */
    List<MenuDTO> findAll();

    /**
     *  Get the "id" menu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MenuDTO findOne(String id);

    /**
     *  Delete the "id" menu.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
