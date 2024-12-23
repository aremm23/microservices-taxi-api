package com.artsem.api.authservice.model;

import lombok.Builder;

@Builder
public record UserCreateMessage(
        String email,
        String firstname,
        String lastname,
        String keycloakId
) {
}
