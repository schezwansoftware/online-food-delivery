package com.codesetters.account.service.mapper;

import com.codesetters.account.domain.*;
import com.codesetters.account.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {


}
