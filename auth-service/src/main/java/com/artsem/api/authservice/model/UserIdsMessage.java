package com.artsem.api.authservice.model;

import lombok.Builder;

@Builder
public record UserIdsMessage(
        String userSequenceId,
        String userKeycloakId
) {
}
