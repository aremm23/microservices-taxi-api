package com.artsem.api.notificationservice.feign.client;

import com.artsem.api.notificationservice.dto.UserEmailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service")
public interface PassengerServiceClient {

    @GetMapping("/api/v1/passengers/{id}/email")
    UserEmailResponseDto getPassengerEmailById(@PathVariable(name = "id") String id);

}
