package com.artsem.api.authservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        try {
            AccessToken token = TokenVerifier.create(jwt.getTokenValue(), AccessToken.class).getToken();
            AccessToken.Access realmAccess = token.getRealmAccess();

            Collection<GrantedAuthority> authorities = (realmAccess == null || realmAccess.getRoles() == null)
                    ? Collections.emptySet()
                    : realmAccess.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + role))
                    .collect(Collectors.toSet());

            return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim(JwtClaimNames.SUB));
        } catch (VerificationException e) {
            log.error("Failed to verify JWT token", e);
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }
}