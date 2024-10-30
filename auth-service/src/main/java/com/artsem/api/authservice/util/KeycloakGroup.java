package com.artsem.api.authservice.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeycloakGroup {
    DRIVER("DRIVER"),
    PASSENGER("PASSENGER"),
    MANAGER("MANAGER");

    private final String group;
}
