package com.codesetters.client;

import com.codesetters.service.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedFeignClient(name = "account")
public interface UserFeignClient {

    @RequestMapping(value = "/api/users/id/{id}")
    UserDTO getAuthenticatedUser(@PathVariable(value = "id") String id);
}
