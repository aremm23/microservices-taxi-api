package com.artsem.api.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static com.artsem.api.authservice.security.SecurityConstants.ACTUATOR_URI;
import static com.artsem.api.authservice.security.SecurityConstants.AUTH_SERVICE_DOCS_URI;
import static com.artsem.api.authservice.security.SecurityConstants.CONFIRM_EMAIL_URL;
import static com.artsem.api.authservice.security.SecurityConstants.USER_LOGIN_URI;
import static com.artsem.api.authservice.security.SecurityConstants.USER_REGISTER_URI;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                USER_REGISTER_URI,
                                ACTUATOR_URI,
                                USER_LOGIN_URI,
                                AUTH_SERVICE_DOCS_URI,
                                CONFIRM_EMAIL_URL
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oath2 -> oath2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

}
