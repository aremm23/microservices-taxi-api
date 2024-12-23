package com.artsem.api.authservice.broker;

import lombok.Builder;

@Builder
public record UserRollbackMessage(
        String keycloakId
) {
}
