package com.artsem.api.passengerservice.broker;

import lombok.Builder;

@Builder
public record UserIdMessage(
        String userKeycloakId,
        Long userId
) {
}
