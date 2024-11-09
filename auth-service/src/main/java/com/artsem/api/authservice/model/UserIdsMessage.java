package com.artsem.api.authservice.model;

public record UserIdsMessage(
        String userSequenceId,
        String userKeycloakId
) {
}
