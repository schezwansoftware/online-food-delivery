package com.codesetters.restaurantservice.service.impl;

import com.codesetters.restaurantservice.service.DishesService;
import com.codesetters.restaurantservice.domain.Dishes;
import com.codesetters.restaurantservice.repository.DishesRepository;
import com.codesetters.restaurantservice.service.dto.DishesDTO;
import com.codesetters.restaurantservice.service.mapper.DishesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Dishes.
 */
@Service
public class DishesServiceImpl implements DishesService{

    private final Logger log = LoggerFactory.getLogger(DishesServiceImpl.class);

    private final DishesRepository dishesRepository;

    private final DishesMapper dishesMapper;

    public DishesServiceImpl(DishesRepository dishesRepository, DishesMapper dishesMapper) {
        this.dishesRepository = dishesRepository;
        this.dishesMapper = dishesMapper;
    }

    /**
     * Save a dishes.
     *
     * @param dishesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DishesDTO save(DishesDTO dishesDTO) {
        log.debug("Request to save Dishes : {}", dishesDTO);
        Dishes dishes = dishesMapper.toEntity(dishesDTO);
        dishes = dishesRepository.save(dishes);
        return dishesMapper.toDto(dishes);
    }

    /**
     *  Get all the dishes.
     *
     *  @return the list of entities
     */
    @Override
    public List<DishesDTO> findAll() {
        log.debug("Request to get all Dishes");
        return dishesRepository.findAll().stream()
            .map(dishesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one dishes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public DishesDTO findOne(String id) {
        log.debug("Request to get Dishes : {}", id);
        Dishes dishes = dishesRepository.findOne(UUID.fromString(id));
        return dishesMapper.toDto(dishes);
    }

    /**
     *  Delete the  dishes by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Dishes : {}", id);
        dishesRepository.delete(UUID.fromString(id));
    }
    @Override
    public List<DishesDTO> dishByMenuId(String menuId){
        log.debug("Request to get Dishes by menu id : {}", menuId);
       return dishesRepository.findAll().stream().filter(dishes -> dishes.getMenuId().equals(UUID.fromString(menuId))).map(dishesMapper::toDto).collect(Collectors.toList());

    }
}
