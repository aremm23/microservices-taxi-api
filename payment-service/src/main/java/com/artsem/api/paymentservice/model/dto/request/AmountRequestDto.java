package com.artsem.api.paymentservice.model.dto.request;

import com.artsem.api.paymentservice.util.ValidationKeys;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AmountRequestDto(
        @NotNull(message = ValidationKeys.AMOUNT_REQUIRED)
        @Min(value = 1, message = ValidationKeys.AMOUNT_MIN_VALUE)
        @Max(value = 10000, message = ValidationKeys.AMOUNT_MAX_VALUE)
        Integer amount
) {
}
