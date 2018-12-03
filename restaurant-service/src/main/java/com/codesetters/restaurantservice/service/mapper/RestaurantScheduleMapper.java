package com.codesetters.restaurantservice.service.mapper;

import com.codesetters.restaurantservice.domain.*;
import com.codesetters.restaurantservice.service.dto.RestaurantScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RestaurantSchedule and its DTO RestaurantScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestaurantScheduleMapper extends EntityMapper<RestaurantScheduleDTO, RestaurantSchedule> {


}
