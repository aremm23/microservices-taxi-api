package com.artsem.api.rideservice.controller;

import com.artsem.api.rideservice.controller.api.RideLifecycleControllerApi;
import com.artsem.api.rideservice.model.dto.request.AcceptedRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.CancelledRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.RequestedRideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import com.artsem.api.rideservice.service.RideLifecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideLifecycleController implements RideLifecycleControllerApi {

    private final RideLifecycleService rideService;

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isUserAuthorizedForId(#rideDto.passengerId, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PostMapping("/request")
    public ResponseEntity<RideResponseDto> requestRide(@RequestBody RequestedRideRequestDto rideDto) {
        RideResponseDto savedRide = rideService.requestRide(rideDto);
        return ResponseEntity.ok(savedRide);
    }

    @PreAuthorize("""
            (hasRole('ROLE_DRIVER') &&
            @userAccessValidator.isUserAuthorizedForId(#rideDto.driverId, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}/accept")
    public ResponseEntity<RideResponseDto> acceptRide(
            @PathVariable String id,
            @RequestBody AcceptedRideRequestDto rideDto
    ) {
        RideResponseDto savedRide = rideService.acceptRide(id, rideDto);
        return ResponseEntity.ok(savedRide);
    }

    @PreAuthorize("""
            (hasRole('ROLE_DRIVER') &&
            @userAccessValidator.isDriverAuthorizedForRideId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}/start")
    public ResponseEntity<RideResponseDto> startRide(@PathVariable String id) {
        RideResponseDto savedRide = rideService.startRide(id);
        return ResponseEntity.ok(savedRide);
    }

    @PreAuthorize("""
            (hasRole('ROLE_DRIVER') &&
            @userAccessValidator.isDriverAuthorizedForRideId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}/complete")
    public ResponseEntity<RideResponseDto> completeRide(@PathVariable String id) {
        RideResponseDto savedRide = rideService.finishRide(id);
        return ResponseEntity.ok(savedRide);
    }

    @PreAuthorize("""
            (hasRole('ROLE_PASSENGER') &&
            @userAccessValidator.isPassengerAuthorizedForRideId(#id, authentication)) ||
            (hasRole('ROLE_DRIVER') &&
            @userAccessValidator.isDriverAuthorizedForRideId(#id, authentication)) ||
            hasRole('ROLE_ADMIN')
            """)
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<RideResponseDto> cancelRide(
            @PathVariable String id,
            @RequestBody CancelledRideRequestDto rideDto
    ) {
        RideResponseDto savedRide = rideService.cancelRide(id, rideDto);
        return ResponseEntity.ok(savedRide);
    }

}
