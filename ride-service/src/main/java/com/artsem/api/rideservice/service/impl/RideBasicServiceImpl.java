package com.artsem.api.rideservice.service.impl;

import com.artsem.api.rideservice.exception.RideNotFoundException;
import com.artsem.api.rideservice.filter.RideFilter;
import com.artsem.api.rideservice.model.Ride;
import com.artsem.api.rideservice.model.dto.request.RideRequestDto;
import com.artsem.api.rideservice.model.dto.response.RideResponseDto;
import com.artsem.api.rideservice.repository.RideRepository;
import com.artsem.api.rideservice.service.RideBasicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideBasicServiceImpl implements RideBasicService {

    private final RideRepository rideRepository;

    private final ModelMapper mapper;

    private final MongoTemplate mongoTemplate;

    public Page<RideResponseDto> getList(RideFilter filter, Pageable pageable) {
        Query query = filter.toQuery();
        query.with(pageable);
        long total = mongoTemplate.count(query, Ride.class);
        List<Ride> rides = mongoTemplate.find(query, Ride.class);
        List<RideResponseDto> rideDtos = rides.stream()
                .map(ride -> mapper.map(ride, RideResponseDto.class))
                .toList();
        return new PageImpl<>(rideDtos, pageable, total);
    }

    public RideResponseDto getById(String id) {
        Ride ride = findRideById(id);
        return mapper.map(ride, RideResponseDto.class);
    }

    @Cacheable(value = "passengersIdCache", key = "#rideId")
    public Long getPassengerIdByRideId(String rideId) {
        return rideRepository.findById(rideId)
                .map(Ride::getPassengerId)
                .orElseThrow(RideNotFoundException::new);
    }

    public RideResponseDto create(RideRequestDto rideDto) {
        Ride ride = mapper.map(rideDto, Ride.class);
        Ride savedRide = rideRepository.save(ride);
        return mapper.map(savedRide, RideResponseDto.class);
    }

    public void delete(String id) {
        if (!rideRepository.existsById(id)) {
            throw new RideNotFoundException();
        }
        rideRepository.deleteById(id);
    }

    private Ride findRideById(String id) {
        return rideRepository.findById(id).orElseThrow(
                RideNotFoundException::new
        );
    }
}
