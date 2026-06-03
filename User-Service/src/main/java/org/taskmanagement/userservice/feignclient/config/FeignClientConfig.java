package org.taskmanagement.userservice.feignclient.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.taskmanagement.userservice.feignclient.errordecoder.CustomFeignClientErrorDecode;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignClientErrorDecode();
    }
}
