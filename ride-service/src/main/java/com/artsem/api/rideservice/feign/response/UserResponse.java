package com.artsem.api.rideservice.feign.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String firstname,
        String surname
) {
}
