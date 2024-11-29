package com.artsem.api.rideservice.config;

import com.artsem.api.rideservice.model.Ride;
import com.artsem.api.rideservice.model.dto.request.RequestedRideRequestDto;
import com.artsem.api.rideservice.model.dto.request.RideRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureModelMapper(modelMapper);
        return modelMapper;
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        setupRequestedRideDtoMapping(modelMapper);
        setupRideRequestDtoMapping(modelMapper);
    }

    private void setupRequestedRideDtoMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(RequestedRideRequestDto.class, Ride.class)
                .addMappings(mapper -> mapper.skip(Ride::setId));
    }

    private void setupRideRequestDtoMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(RideRequestDto.class, Ride.class)
                .addMappings(mapper -> {
                    mapper.skip(Ride::setId);
                    mapper.map(RideRequestDto::getCarId, Ride::setCarId);
                    mapper.map(RideRequestDto::getDriverId, Ride::setDriverId);
                    mapper.map(RideRequestDto::getTariffId, Ride::setTariffId);
                    mapper.map(RideRequestDto::getStatusId, Ride::setStatusId);
                    mapper.map(RideRequestDto::getPaymentMethodId, Ride::setPaymentMethodId);
                    mapper.map(RideRequestDto::getPassengerId, Ride::setPassengerId);
                });
    }
}
