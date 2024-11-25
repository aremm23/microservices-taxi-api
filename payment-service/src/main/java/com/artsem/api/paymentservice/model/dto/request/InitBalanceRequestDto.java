package com.artsem.api.paymentservice.model.dto.request;

import com.artsem.api.paymentservice.util.ValidationKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record InitBalanceRequestDto(
        @NotNull(message = ValidationKeys.USER_ID_REQUIRED)
        Long userId
) {
}
