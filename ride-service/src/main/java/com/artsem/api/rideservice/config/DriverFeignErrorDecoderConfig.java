package com.artsem.api.rideservice.config;

import com.artsem.api.rideservice.feign.DriverClientErrorDecoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DriverFeignErrorDecoderConfig {

    @Bean
    public ErrorDecoder driverErrorDecoder(DriverClientErrorDecoder driverClientErrorDecoder) {
        return driverClientErrorDecoder;
    }

}
