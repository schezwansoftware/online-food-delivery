package com.codesetters.deliveryexecutives.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.codesetters.deliveryexecutives")
public class FeignConfiguration {

}
