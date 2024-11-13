package com.artsem.api.rideservice.model.dto.rest.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ListResponseDto<T>(int size, List<T> list) {
}
