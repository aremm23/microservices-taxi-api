package com.artsem.api.driverservice.broker;

import lombok.Builder;

@Builder
public record UserIdMessage(
        String userKeycloakId,
        Long userId
) {
}
