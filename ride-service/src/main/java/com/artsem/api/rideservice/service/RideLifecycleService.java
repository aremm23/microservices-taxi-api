package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.exception.InvalidRideStatusException;
import com.artsem.api.rideservice.exception.RideNotFoundException;
import com.artsem.api.rideservice.model.Ride;
import com.artsem.api.rideservice.model.RideStatus;
import com.artsem.api.rideservice.model.UserRole;
import com.artsem.api.rideservice.model.dto.AcceptedRideDto;
import com.artsem.api.rideservice.model.dto.CancelledRideDto;
import com.artsem.api.rideservice.model.dto.CompletedRideDto;
import com.artsem.api.rideservice.model.dto.RequestedRideDto;
import com.artsem.api.rideservice.model.dto.StartedRideDto;
import com.artsem.api.rideservice.repository.RideRepository;
import com.artsem.api.rideservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideLifecycleService {

    private final RideRepository rideRepository;

    private final ModelMapper mapper;

    public void requestRide(RequestedRideDto rideDto) {
        Ride ride = mapper.map(rideDto, Ride.class);
        ride.setRequestedTime(LocalDateTime.now());
        ride.setStatusId(RideStatus.REQUESTED.getId());
        rideRepository.save(ride);
    }

    public void acceptRide(AcceptedRideDto rideDto) {
        Ride ride = findRideOrThrow(rideDto.getRideId());
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.REQUESTED.getId(),
                ExceptionKeys.REQUESTED_STATUS_EXPECTED
        );
        setAcceptedStatusParameters(rideDto, ride);
        rideRepository.save(ride);
    }

    private void setAcceptedStatusParameters(AcceptedRideDto rideDto, Ride ride) {
        ride.setStatusId(RideStatus.ACCEPTED.getId());
        ride.setAcceptedTime(LocalDateTime.now());
        ride.setDriverId(rideDto.getDriverId());
        ride.setCarId(rideDto.getCarId());
        ride.setCarLicensePlate(rideDto.getCarLicensePlate());
    }

    public void startRide(StartedRideDto rideDto) {
        Ride ride = findRideOrThrow(rideDto.getRideId());
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.ACCEPTED.getId(),
                ExceptionKeys.ACCEPTED_STATUS_EXPECTED
        );
        ride.setStatusId(RideStatus.STARTED.getId());
        ride.setStartedTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    public void finishRide(CompletedRideDto rideDto) {
        Ride ride = findRideOrThrow(rideDto.getRideId());
        validateRideStatus(
                ride.getStatusId(),
                RideStatus.STARTED.getId(),
                ExceptionKeys.STARTED_STATUS_EXPECTED
        );
        ride.setStatusId(RideStatus.FINISHED.getId());
        ride.setFinishedTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    public void cancelRide(CancelledRideDto rideDto) {
        Ride ride = findRideOrThrow(rideDto.getRideId());
        validateRideStatusToCancel(ride);
        ride.setStatusId(defineCancelledByStatusId(rideDto.getUserRole()));
        ride.setCanceledTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    private int defineCancelledByStatusId(UserRole userRole) {
        if (userRole.equals(UserRole.DRIVER)) {
            return RideStatus.CANCELLED_BY_DRIVER.getId();
        } else if (userRole.equals(UserRole.PASSENGER)) {
            return RideStatus.CANCELLED_BY_PASSENGER.getId();
        } else throw new IllegalArgumentException("Invalid user role");
    }

    private Ride findRideOrThrow(String rideId) {
        return rideRepository.findById(rideId).orElseThrow(RideNotFoundException::new);
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