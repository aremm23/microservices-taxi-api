package com.artsem.api.notificationservice.dto;

import lombok.Builder;

@Builder
public record EmailConfirmationDto(
        String email,
        String token
) {
}
