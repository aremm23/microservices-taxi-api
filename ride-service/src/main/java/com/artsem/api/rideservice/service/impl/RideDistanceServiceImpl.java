package com.artsem.api.rideservice.service.impl;

import com.artsem.api.rideservice.config.MapsApiProperties;
import com.artsem.api.rideservice.exception.InvalidResponseElementException;
import com.artsem.api.rideservice.exception.InvalidResponseException;
import com.artsem.api.rideservice.model.util.DistanceAndDurationValues;
import com.artsem.api.rideservice.model.util.DistanceMatrixResponse;
import com.artsem.api.rideservice.service.RideDistanceService;
import com.artsem.api.rideservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class RideDistanceServiceImpl implements RideDistanceService {

    public static final String OK_STATUS = "OK";
    public static final String URL_ORIGIN_PARAM = "origins";
    public static final String URL_DESTINATION_PARAM = "destinations";
    public static final String URL_UNITS_PARAM = "units";
    public static final String URL_KEY_PARAM = "key";
    private final MapsApiProperties mapsApiProperties;
    private final RestTemplate restTemplate;

    @Cacheable(value = "distanceAndDurationCache", key = "#pickUpLocation + ':' + #dropOffLocation")
    public DistanceAndDurationValues getDistanceAndDuration(String pickUpLocation, String dropOffLocation) {
        if (pickUpLocation == null || dropOffLocation == null) {
            throw new IllegalArgumentException(ExceptionKeys.PICKUP_AND_DROP_OFF_REQUIRED);
        }
        String url = buildUrl(pickUpLocation, dropOffLocation);
        DistanceMatrixResponse response = restTemplate.getForObject(url, DistanceMatrixResponse.class);
        validateResponse(response);
        DistanceMatrixResponse.Element element = response.getRows().get(0).getElements().get(0);
        validateElement(element);
        return DistanceAndDurationValues.builder()
                .distanceValue(element.getDistance().getValue())
                .durationValue(element.getDuration().getValue())
                .build();
    }

    private String buildUrl(String pickUpLocation, String dropOffLocation) {
        return UriComponentsBuilder.fromHttpUrl(mapsApiProperties.getUrl())
                .queryParam(URL_ORIGIN_PARAM, pickUpLocation)
                .queryParam(URL_DESTINATION_PARAM, dropOffLocation)
                .queryParam(URL_UNITS_PARAM, mapsApiProperties.getUnits())
                .queryParam(URL_KEY_PARAM, mapsApiProperties.getKey())
                .build()
                .toUriString();
    }

    private void validateElement(DistanceMatrixResponse.Element element) {
        if (
                !OK_STATUS.equals(element.getStatus())
                        || element.getDistance() == null
                        || element.getDuration() == null) {
            throw new InvalidResponseElementException();
        }
    }

    private void validateResponse(DistanceMatrixResponse response) {
        if (
                response == null
                        || !OK_STATUS.equals(response.getStatus())
                        || response.getRows().isEmpty()
                        || response.getRows().get(0).getElements().isEmpty()) {
            throw new InvalidResponseException();
        }
    }
}