package com.artsem.api.rideservice.feign.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DriverStatusUpdateRequestDto(
        @JsonProperty("isFree")
        Boolean isFree
) {
}