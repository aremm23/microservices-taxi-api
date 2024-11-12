package com.artsem.api.rideservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gcp.api.maps")
@Data
public class MapsApiProperties {
    private String url;
    private String key;
    private String units;
}