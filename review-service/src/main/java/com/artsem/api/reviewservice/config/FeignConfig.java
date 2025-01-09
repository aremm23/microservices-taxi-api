package com.artsem.api.reviewservice.config;

import com.artsem.api.reviewservice.exceptions.InternalServiceException;
import com.artsem.api.reviewservice.feign.RideClientErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class FeignConfig {

    public static final String AUTHENTICATION_NOT_FOUND_MESSAGE = "No authentication found in SecurityContext";

    @Bean
    public ErrorDecoder driverErrorDecoder(RideClientErrorDecoder rideClientErrorDecoder) {
        return rideClientErrorDecoder;
    }

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
            throw new InternalServiceException(AUTHENTICATION_NOT_FOUND_MESSAGE);
        }
        Jwt principal = (Jwt) authentication.getPrincipal();
        return principal.getTokenValue();
    }
}
