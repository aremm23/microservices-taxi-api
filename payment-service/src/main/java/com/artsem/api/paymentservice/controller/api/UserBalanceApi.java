package com.artsem.api.paymentservice.controller.api;

import com.artsem.api.paymentservice.model.dto.IsBalancePositiveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Balance Controller", description = "API for managing user balances by users ID")
public interface UserBalanceApi {

    @Operation(summary = "Check if the balance is positive", description = "Check whether the user’s balance is positive")
    ResponseEntity<IsBalancePositiveDto> isBalancePositive(
            @Parameter(description = "ID of the user to check balance") Long userId
    );
}
