package com.artsem.api.authservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    PASSENGER("PASSENGER"),
    DRIVER("DRIVER");

    private final String role;
}
