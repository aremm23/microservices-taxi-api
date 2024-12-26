package com.artsem.api.rideservice.feign.client;

import com.artsem.api.rideservice.config.DriverFeignErrorDecoderConfig;
import com.artsem.api.rideservice.feign.request.DriverStatusUpdateRequestDto;
import com.artsem.api.rideservice.feign.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "driver-service", configuration = DriverFeignErrorDecoderConfig.class)
public interface DriverServiceClient {

    @PutMapping("/api/v1/drivers/{id}/status")
    UserResponse getDriverById(
            @PathVariable(name = "id") Long id,
            @RequestBody DriverStatusUpdateRequestDto statusUpdateRequestDto
    );

}
