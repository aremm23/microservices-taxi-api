package com.artsem.api.driverservice.exceptions.car;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException() {
        super(ExceptionKeys.CAR_NOT_FOUND);
    }
}
