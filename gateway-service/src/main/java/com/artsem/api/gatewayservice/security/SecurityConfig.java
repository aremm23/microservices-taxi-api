package com.artsem.api.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.artsem.api.gatewayservice.util.SecurityConstants.ACTUATOR_HEALTH;
import static com.artsem.api.gatewayservice.util.SecurityConstants.AUTH_LOGIN;
import static com.artsem.api.gatewayservice.util.SecurityConstants.AUTH_REGISTER;
import static com.artsem.api.gatewayservice.util.SecurityConstants.AUTH_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.DRIVER_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.FALLBACK;
import static com.artsem.api.gatewayservice.util.SecurityConstants.NOTIFICATION_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.PASSENGER_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.PAYMENT_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.REVIEW_SERVICE_ACTUATOR;
import static com.artsem.api.gatewayservice.util.SecurityConstants.RIDE_SERVICE_ACTUATOR;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                .pathMatchers(
                        AUTH_LOGIN,
                        AUTH_REGISTER,
                        ACTUATOR_HEALTH,
                        AUTH_SERVICE_ACTUATOR,
                        PASSENGER_SERVICE_ACTUATOR,
                        DRIVER_SERVICE_ACTUATOR,
                        NOTIFICATION_SERVICE_ACTUATOR,
                        PAYMENT_SERVICE_ACTUATOR,
                        RIDE_SERVICE_ACTUATOR,
                        REVIEW_SERVICE_ACTUATOR,
                        FALLBACK
                ).permitAll()
                .anyExchange().authenticated()
        );
        http.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
}
