package com.artsem.api.driverservice.model.dto.responce;

import lombok.Builder;

import java.util.List;

@Builder
public record ListResponseDto<T>(int size, List<T> list) {
}
