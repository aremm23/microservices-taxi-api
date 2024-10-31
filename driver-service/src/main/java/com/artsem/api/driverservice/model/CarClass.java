package com.artsem.api.driverservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarClass {
    ECONOM("ECONOM"),
    COMFORT("COMFORT"),
    BUSINESS("BUSINESS");

    private final String carClass;
}
