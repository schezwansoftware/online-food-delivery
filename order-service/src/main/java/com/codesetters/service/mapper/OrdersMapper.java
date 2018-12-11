package com.codesetters.service.mapper;

import com.codesetters.domain.*;
import com.codesetters.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {


}
