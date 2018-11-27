package com.codesetters.restaurantservice.service.mapper;

import com.codesetters.restaurantservice.domain.*;
import com.codesetters.restaurantservice.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    

    
}
