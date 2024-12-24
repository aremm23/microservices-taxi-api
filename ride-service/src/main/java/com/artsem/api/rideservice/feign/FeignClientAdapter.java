package com.artsem.api.rideservice.feign;

import com.artsem.api.rideservice.exception.InternalServiceException;
import com.artsem.api.rideservice.feign.client.DriverServiceClient;
import com.artsem.api.rideservice.feign.client.PassengerServiceClient;
import com.artsem.api.rideservice.feign.client.PaymentServiceClient;
import com.artsem.api.rideservice.feign.request.DriverStatusUpdateRequestDto;
import com.artsem.api.rideservice.feign.response.IsBalancePositiveResponse;
import com.artsem.api.rideservice.feign.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeignClientAdapter {

    private final PassengerServiceClient passengerServiceClient;

    private final PaymentServiceClient paymentServiceClient;

    private final DriverServiceClient driverServiceClient;

    public UserResponse updateDriverStatusAndGetDriverById(Long id, boolean statusToUpdate) {
        log.info("Fetching driver with ID: {}", id);
        DriverStatusUpdateRequestDto driverStatusUpdateRequestDto = DriverStatusUpdateRequestDto.builder()
                .isFree(statusToUpdate)
                .build();
        return driverServiceClient.getDriverById(id, driverStatusUpdateRequestDto);
    }

    public UserResponse getPassengerById(Long id) {
        log.info("Fetching passenger with ID: {}", id);
        return passengerServiceClient.getPassengerById(id);
    }

    public IsBalancePositiveResponse getIsBalancePositiveById(Long id) {
        log.info("Fetching balance with ID: {}", id);
        try {
            return paymentServiceClient.getIsBalancePositiveById(id);
        } catch (Exception e) {
            log.error("Failed to fetch balance with ID: {}. Error: {}", id, e.getMessage());
            throw new InternalServiceException();
        }
    }
}
