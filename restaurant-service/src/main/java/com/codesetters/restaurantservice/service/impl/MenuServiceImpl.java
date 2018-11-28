package com.codesetters.restaurantservice.service.impl;

import com.codesetters.restaurantservice.service.MenuService;
import com.codesetters.restaurantservice.domain.Menu;
import com.codesetters.restaurantservice.repository.MenuRepository;
import com.codesetters.restaurantservice.service.dto.MenuDTO;
import com.codesetters.restaurantservice.service.mapper.MenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Menu.
 */
@Service
public class MenuServiceImpl implements MenuService{

    private final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MenuDTO save(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.toEntity(menuDTO);
        menu = menuRepository.save(menu);
        return menuMapper.toDto(menu);
    }

    /**
     *  Get all the menus.
     *
     *  @return the list of entities
     */
    @Override
    public List<MenuDTO> findAll() {
        log.debug("Request to get all Menus");
        return menuRepository.findAll().stream()
            .map(menuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one menu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public MenuDTO findOne(String id) {
        log.debug("Request to get Menu : {}", id);
        Menu menu = menuRepository.findOne(UUID.fromString(id));
        return menuMapper.toDto(menu);
    }

    /**
     *  Delete the  menu by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.delete(UUID.fromString(id));
    }
}