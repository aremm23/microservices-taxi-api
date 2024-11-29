package com.artsem.api.rideservice.service.impl;

import com.artsem.api.rideservice.exception.InvalidRideStatusException;
import com.artsem.api.rideservice.exception.RideNotFoundException;
import com.artsem.api.rideservice.model.Ride;
import com.artsem.api.rideservice.model.RideStatus;
import com.artsem.api.rideservice.model.dto.request.AcceptedRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.CancelledRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.RequestedRideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import com.artsem.api.rideservice.model.util.UserRole;
import com.artsem.api.rideservice.repository.RideRepository;
import com.artsem.api.rideservice.service.RideLifecycleService;
import com.artsem.api.rideservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideLifecycleServiceImpl implements RideLifecycleService {

    private final RideRepository rideRepository;

    private final ModelMapper mapper;

    @Override
    public RideResponseDto requestRide(RequestedRideRequestDto rideDto) {
        Ride ride = mapper.map(rideDto, Ride.class);
        ride.setRequestedTime(LocalDateTime.now());
        ride.setStatusId(RideStatus.REQUESTED.getId());
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    @Override
    public RideResponseDto acceptRide(String rideId, AcceptedRideRequestDto rideDto) {
        Ride ride = findRideOrThrow(rideId);
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.REQUESTED.getId(),
                ExceptionKeys.REQUESTED_STATUS_EXPECTED
        );
        setAcceptedStatusParameters(rideDto, ride);
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    private void setAcceptedStatusParameters(AcceptedRideRequestDto rideDto, Ride ride) {
        ride.setStatusId(RideStatus.ACCEPTED.getId());
        ride.setAcceptedTime(LocalDateTime.now());
        ride.setDriverId(rideDto.getDriverId());
        ride.setCarId(rideDto.getCarId());
        ride.setCarLicensePlate(rideDto.getCarLicensePlate());
    }

    @Override
    public RideResponseDto startRide(String rideId) {
        Ride ride = findRideOrThrow(rideId);
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.ACCEPTED.getId(),
                ExceptionKeys.ACCEPTED_STATUS_EXPECTED
        );
        ride.setStatusId(RideStatus.STARTED.getId());
        ride.setStartedTime(LocalDateTime.now());
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    @Override
    public RideResponseDto finishRide(String rideId) {
        Ride ride = findRideOrThrow(rideId);
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.STARTED.getId(),
                ExceptionKeys.STARTED_STATUS_EXPECTED
        );
        ride.setStatusId(RideStatus.FINISHED.getId());
        ride.setFinishedTime(LocalDateTime.now());
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    @Override
    public RideResponseDto cancelRide(String rideId, CancelledRideRequestDto rideDto) {
        Ride ride = findRideOrThrow(rideId);
        validateRideStatusToCancel(ride);
        ride.setStatusId(defineCancelledByStatusId(rideDto.getUserRole()));
        ride.setCanceledTime(LocalDateTime.now());
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    private int defineCancelledByStatusId(UserRole userRole) {
        if (userRole.equals(UserRole.DRIVER)) {
            return RideStatus.CANCELLED_BY_DRIVER.getId();
        } else if (userRole.equals(UserRole.PASSENGER)) {
            return RideStatus.CANCELLED_BY_PASSENGER.getId();
        } else {
            throw new IllegalArgumentException("Invalid user role");
        }
    }

    private Ride findRideOrThrow(String rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(RideNotFoundException::new);
    }

    private void validateRideStatus(int rideStatus, int expectedStatus, String message) {
        if (rideStatus != expectedStatus) {
            throw new InvalidRideStatusException(message);
        }
    }

    private void validateRideStatusToCancel(Ride ride) {
        if (
                !ride.getStatusId().equals(RideStatus.REQUESTED.getId())
                        && !ride.getStatusId().equals(RideStatus.ACCEPTED.getId())
                        && !ride.getStatusId().equals(RideStatus.STARTED.getId())
        ) {
            throw new InvalidRideStatusException(ExceptionKeys.UNABLE_CANCEL_RIDE);
        }
    }
}