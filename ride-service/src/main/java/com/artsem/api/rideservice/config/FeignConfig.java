package com.artsem.api.rideservice.config;

import com.artsem.api.rideservice.security.KeycloakTokenProvider;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor(KeycloakTokenProvider tokenProvider) {
        return requestTemplate -> {
            String accessToken = tokenProvider.getAccessToken();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
        };
    }

}
