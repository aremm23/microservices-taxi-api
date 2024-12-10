package com.artsem.api.reviewservice.model.dto.response;

import lombok.Builder;

@Builder
public record RatingResponseDto(
        Double rate
) {
}
