package com.artsem.api.gatewayservice.controller;

import com.artsem.api.gatewayservice.dto.FallbackResponseDto;
import com.artsem.api.gatewayservice.util.FallbackMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping(value = "/auth-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> authServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.AUTH_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }

    @RequestMapping(value = "/passenger-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> passengerServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.PASSENGER_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }

    @RequestMapping(value = "/driver-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> driverServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.DRIVER_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }

    @RequestMapping(value = "/ride-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> rideServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.RIDE_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }

    @RequestMapping(value = "/review-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> reviewServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.REVIEW_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }

    @RequestMapping(value = "/payment-service",
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<FallbackResponseDto> paymentServiceFallback() {
        return new ResponseEntity<>(
                new FallbackResponseDto(FallbackMessages.PAYMENT_SERVICE_DOWN),
                HttpStatus.GATEWAY_TIMEOUT
        );
    }
}
