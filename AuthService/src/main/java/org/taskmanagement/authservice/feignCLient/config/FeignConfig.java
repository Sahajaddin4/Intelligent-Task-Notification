package org.taskmanagement.authservice.feignCLient.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.taskmanagement.authservice.feignCLient.errorhandle.CustomFeignError;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return  new CustomFeignError();
    }
}
