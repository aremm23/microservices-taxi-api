package com.artsem.api.authservice.model;

import lombok.Builder;

@Builder
public record UserRegisterDto(
        String username,
        String password,
        String email,
        String firstname,
        String lastname
) {
}
