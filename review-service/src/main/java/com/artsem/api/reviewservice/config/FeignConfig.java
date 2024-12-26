package com.artsem.api.reviewservice.config;

import com.artsem.api.reviewservice.feign.RideClientErrorDecoder;
import com.artsem.api.reviewservice.security.KeycloakTokenProvider;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder driverErrorDecoder(RideClientErrorDecoder rideClientErrorDecoder) {
        return rideClientErrorDecoder;
    }

    @Bean
    public RequestInterceptor feignClientInterceptor(KeycloakTokenProvider tokenProvider) {
        return requestTemplate -> {
            String accessToken = tokenProvider.getAccessToken();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
        };
    }
}
