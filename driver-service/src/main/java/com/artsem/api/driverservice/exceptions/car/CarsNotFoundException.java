package com.artsem.api.driverservice.exceptions.car;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class CarsNotFoundException extends RuntimeException {
    public CarsNotFoundException() {
        super(ExceptionKeys.CARS_NOT_FOUND);
    }
}
