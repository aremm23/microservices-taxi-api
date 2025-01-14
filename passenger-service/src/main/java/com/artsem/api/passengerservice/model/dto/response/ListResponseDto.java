package com.artsem.api.passengerservice.model.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ListResponseDto<T>(int size, List<T> list) {
}
