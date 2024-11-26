package com.artsem.api.paymentservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BalanceResponseDto {
    private Long id;
    private Long userId;
    private BigDecimal amount;
}
