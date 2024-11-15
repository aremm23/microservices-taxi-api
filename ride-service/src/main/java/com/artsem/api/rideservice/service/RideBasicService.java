package com.artsem.api.rideservice.service;

import com.artsem.api.rideservice.filter.RideFilter;
import com.artsem.api.rideservice.model.dto.rest.request.RideRequestDto;
import com.artsem.api.rideservice.model.dto.rest.response.RideResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RideBasicService {

    Page<RideResponseDto> getList(RideFilter filter, Pageable pageable);

    RideResponseDto getById(String id);

    RideResponseDto create(RideRequestDto rideDto);

    void delete(String id);

}
