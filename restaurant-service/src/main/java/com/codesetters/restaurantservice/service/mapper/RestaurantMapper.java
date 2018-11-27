package com.codesetters.restaurantservice.service.mapper;

import com.codesetters.restaurantservice.domain.*;
import com.codesetters.restaurantservice.service.dto.RestaurantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Restaurant and its DTO RestaurantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {


}
