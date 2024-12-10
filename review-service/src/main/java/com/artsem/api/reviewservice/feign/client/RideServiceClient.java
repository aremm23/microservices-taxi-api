package com.artsem.api.reviewservice.feign.client;

import com.artsem.api.reviewservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ride-service", configuration = FeignConfig.class)
public interface RideServiceClient {

    @GetMapping("/api/v1/rides/{id}")
    RideResponseDto getRideById(@PathVariable(name = "id") String id);

}
