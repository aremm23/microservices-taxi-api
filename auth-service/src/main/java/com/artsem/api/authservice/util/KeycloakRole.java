package com.artsem.api.authservice.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeycloakRole {
    ADMIN("ADMIN"),
    PASSENGER("PASSENGER"),
    DRIVER("DRIVER");

    private final String role;
}
