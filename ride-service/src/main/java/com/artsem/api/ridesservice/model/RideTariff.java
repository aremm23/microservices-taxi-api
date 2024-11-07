package com.artsem.api.ridesservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RideTariff {
    ECONOM(1),
    COMFORT(2),
    BUSINESS(3);

    private final int id;

    public static RideTariff fromId(int id) {
        for (RideTariff tariff : values()) {
            if (tariff.getId() == id) {
                return tariff;
            }
        }
        throw new IllegalArgumentException("Invalid id for RideTariff: " + id);
    }
}