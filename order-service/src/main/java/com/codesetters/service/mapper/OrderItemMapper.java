package com.codesetters.service.mapper;

import com.codesetters.domain.*;
import com.codesetters.service.dto.OrderItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderItem and its DTO OrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {


}
