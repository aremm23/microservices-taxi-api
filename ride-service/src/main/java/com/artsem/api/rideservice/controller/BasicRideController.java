package com.artsem.api.rideservice.controller;

import com.artsem.api.rideservice.controller.api.BasicRideControllerApi;
import com.artsem.api.rideservice.filter.RideFilter;
import com.artsem.api.rideservice.model.dto.request.RideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import com.artsem.api.rideservice.service.RideBasicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class BasicRideController implements BasicRideControllerApi {

    private final RideBasicService rideBasicService;

    @PreAuthorize("hasAnyRole('PASSENGER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<RideResponseDto>> getList(
            @ModelAttribute
            RideFilter filter,
            Pageable pageable
    ) {
        Page<RideResponseDto> rideResponseDtos = rideBasicService.getList(filter, pageable);
        return ResponseEntity.ok(new PagedModel<>(rideResponseDtos));
    }

    @PreAuthorize("hasAnyRole('PASSENGER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getById(
            @PathVariable
            String id
    ) {
        RideResponseDto ride = rideBasicService.getById(id);
        return ResponseEntity.ok(ride);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RideResponseDto> create(
            @Valid
            @RequestBody
            RideRequestDto rideDto
    ) {
        RideResponseDto createdRide = rideBasicService.create(rideDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdRide);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            String id
    ) {
        rideBasicService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}