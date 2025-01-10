package com.artsem.api.reviewservice.feign.client;

import com.artsem.api.reviewservice.exceptions.InternalServiceException;
import com.artsem.api.reviewservice.exceptions.RideNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideServiceClientAdapter {

    private final RideServiceClient rideServiceClient;

    public RideResponseDto getRideById(String id) {
        try {
            log.info("Fetching ride with ID: {}", id);
            return rideServiceClient.getRideById(id);
        } catch (RideNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch ride with ID: {}. Error: {}", id, e.getMessage());
            throw new InternalServiceException(e.getMessage());
        }
    }
}
