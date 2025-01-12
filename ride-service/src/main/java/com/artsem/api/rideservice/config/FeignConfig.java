package com.artsem.api.rideservice.config;

import com.artsem.api.rideservice.exception.InternalServiceException;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return requestTemplate -> {
            String accessToken = getAccessToken();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
        };
    }

    private String getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new InternalServiceException();
        }
        Jwt principal = (Jwt) authentication.getPrincipal();
        return principal.getTokenValue();
    }

}
