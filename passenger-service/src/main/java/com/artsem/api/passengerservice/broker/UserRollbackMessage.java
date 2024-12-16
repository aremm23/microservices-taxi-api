package com.artsem.api.passengerservice.broker;

import lombok.Builder;

@Builder
public record UserRollbackMessage(
        String keycloakId
) {}