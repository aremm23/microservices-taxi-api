package com.artsem.api.rideservice.config;

import com.artsem.api.rideservice.model.Ride;
import com.artsem.api.rideservice.model.dto.internal.RequestedRideDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(RequestedRideDto.class, Ride.class).addMappings(mapper ->
                mapper.skip(Ride::setId));
        return modelMapper;
    }
}
