package com.artsem.api.passengerservice.broker;

import lombok.Builder;

@Builder
public record UserCreateMessage(
        String email,
        String firstname,
        String lastname,
        String keycloakId
) {
}