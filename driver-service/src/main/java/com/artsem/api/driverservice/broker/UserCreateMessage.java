package com.artsem.api.driverservice.broker;

import lombok.Builder;

@Builder
public record UserCreateMessage(
        String email,
        String firstname,
        String lastname,
        String keycloakId
) {
}
