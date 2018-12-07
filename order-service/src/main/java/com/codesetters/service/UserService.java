package com.codesetters.service;

import com.codesetters.client.UserFeignClient;
import com.codesetters.service.dto.UserDTO;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    ;
    private final UserFeignClient userFeignClient;

    public UserService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public UserDTO getUserWithAuthorities(String id) throws HystrixRuntimeException {
        return  userFeignClient.getAuthenticatedUser(id);
    }
}
