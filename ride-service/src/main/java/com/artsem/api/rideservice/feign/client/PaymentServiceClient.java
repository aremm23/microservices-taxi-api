package com.artsem.api.rideservice.feign.client;

import com.artsem.api.rideservice.feign.response.IsBalancePositiveResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @GetMapping("/api/v1/users/{userId}/balance/is-positive")
    IsBalancePositiveResponse getIsBalancePositiveById(
            @PathVariable(name = "userId") Long id
    );

}
