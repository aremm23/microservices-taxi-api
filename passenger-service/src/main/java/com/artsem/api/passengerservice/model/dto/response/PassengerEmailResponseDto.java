package com.artsem.api.passengerservice.model.dto.response;

import lombok.Builder;

@Builder
public record PassengerEmailResponseDto(
        Long id,
        String email
) {
}