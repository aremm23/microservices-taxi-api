package com.artsem.api.notificationservice.dto;

import lombok.Builder;

@Builder
public record UserEmailResponseDto(
        Long id,
        String email
) {
}