package com.artsem.api.rideservice.feign.client;

import com.artsem.api.rideservice.config.PassengerFeignErrorDecoderConfig;
import com.artsem.api.rideservice.feign.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service", configuration = PassengerFeignErrorDecoderConfig.class)
public interface PassengerServiceClient {

    @GetMapping("/api/v1/passengers/{id}")
    UserResponse getPassengerById(
            @PathVariable(name = "id") Long id
    );

}
