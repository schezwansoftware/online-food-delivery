package com.codesetters.restaurantservice.service.mapper;

import com.codesetters.restaurantservice.domain.*;
import com.codesetters.restaurantservice.service.dto.DishesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dishes and its DTO DishesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DishesMapper extends EntityMapper<DishesDTO, Dishes> {

    

    
}
