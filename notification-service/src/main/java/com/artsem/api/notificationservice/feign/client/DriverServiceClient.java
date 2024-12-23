package com.artsem.api.notificationservice.feign.client;

import com.artsem.api.notificationservice.dto.UserEmailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service")
public interface DriverServiceClient {

    @GetMapping("/api/v1/drivers/{id}/email")
    UserEmailResponseDto getDriverEmailById(@PathVariable(name = "id") String id);

}
