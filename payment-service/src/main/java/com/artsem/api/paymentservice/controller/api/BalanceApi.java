package com.artsem.api.paymentservice.controller.api;

import com.artsem.api.paymentservice.model.dto.request.AmountRequestDto;
import com.artsem.api.paymentservice.model.dto.request.InitBalanceRequestDto;
import com.artsem.api.paymentservice.model.dto.response.BalanceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Balance Controller", description = "API for managing user balances in the payment service")
public interface BalanceApi {

    @Operation(summary = "Initialize a balance", description = "Initialize a new balance for a user")
    ResponseEntity<BalanceResponseDto> initBalance(
            @Parameter(description = "Details of the balance initialization request")
            InitBalanceRequestDto initBalanceRequestDto
    );

    @Operation(summary = "Charge a balance", description = "Charge a user’s balance with a specified amount")
    ResponseEntity<BalanceResponseDto> charge(
            @Parameter(description = "ID of the balance to charge") Long id,
            @Parameter(description = "Amount to charge the balance") AmountRequestDto amount
    );

    @Operation(summary = "Get balance by ID", description = "Retrieve the balance details of a user by their balance ID")
    ResponseEntity<BalanceResponseDto> getById(
            @Parameter(description = "ID of the balance to retrieve") Long id
    );

    @Operation(summary = "Delete a balance by ID", description = "Delete a balance record by its ID")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID of the balance to delete") Long id
    );
}