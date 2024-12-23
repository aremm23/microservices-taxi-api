package com.artsem.api.authservice.broker;

import lombok.Builder;

@Builder
public record EmailVerificationMessage(
        String email,
        String token
) {
}
