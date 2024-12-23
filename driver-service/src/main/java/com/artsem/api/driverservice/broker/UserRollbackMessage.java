package com.artsem.api.driverservice.broker;

import lombok.Builder;

@Builder
public record UserRollbackMessage(
        String keycloakId
) {
}
