package com.codesetters.restaurantservice.service.impl;

import com.codesetters.restaurantservice.repository.DishesRepository;
import com.codesetters.restaurantservice.service.MenuService;
import com.codesetters.restaurantservice.domain.Menu;
import com.codesetters.restaurantservice.repository.MenuRepository;
import com.codesetters.restaurantservice.service.dto.DishesDTO;
import com.codesetters.restaurantservice.service.dto.MenuDTO;
import com.codesetters.restaurantservice.service.dto.MenuItemDto;
import com.codesetters.restaurantservice.service.mapper.DishesMapper;
import com.codesetters.restaurantservice.service.mapper.MenuMapper;
import com.codesetters.restaurantservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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

    private final DishesRepository dishesRepository;
    private final DishesMapper dishesMapper;

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, DishesRepository dishesRepository, DishesMapper dishesMapper, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.dishesRepository = dishesRepository;
        this.dishesMapper = dishesMapper;
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

    @Override
    public MenuItemDto saveMenuItem(MenuItemDto menuItemDto){
        Menu menu=new Menu();

            LocalDate date1=menuItemDto.getDate();
            menu.setEndDate(date1.atStartOfDay(ZoneId.systemDefault()));



        menu.setStartDate(ZonedDateTime.now());
        menu.setId(UUID.randomUUID());
        menu.setRestaurantId(menuItemDto.getRestaurantId());
        Menu savedMenu=menuRepository.save(menu);
        for (DishesDTO dish: menuItemDto.getDishes()){
            dish.setMenuId(savedMenu.getId());
            dish.setId(UUID.randomUUID());
            dishesRepository.save(dishesMapper.toEntity(dish));
        }

        return menuItemDto;
    }
    @Override
    public MenuDTO findByRestaurantId(String restaurantId){

       List<Menu> menus = menuRepository.findAll().stream().filter(menu -> menu.getRestaurantId().equals(UUID.fromString(restaurantId))).
           collect(Collectors.toList());
       if(!menus.isEmpty()){
           return menuMapper.toDto(menus.get(0));

       }
        throw new BadRequestAlertException("can Not find", "menu for ", "this restaurant");

    }

}
