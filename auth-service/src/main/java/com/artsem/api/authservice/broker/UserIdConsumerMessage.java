package com.artsem.api.authservice.broker;

import lombok.Builder;

@Builder
public record UserIdConsumerMessage(
        String userKeycloakId,
        Long userId
) {
}
