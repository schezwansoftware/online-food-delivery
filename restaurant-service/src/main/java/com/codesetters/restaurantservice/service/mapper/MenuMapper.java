package com.codesetters.restaurantservice.service.mapper;

import com.codesetters.restaurantservice.domain.*;
import com.codesetters.restaurantservice.service.dto.MenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Menu and its DTO MenuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    

    
}
