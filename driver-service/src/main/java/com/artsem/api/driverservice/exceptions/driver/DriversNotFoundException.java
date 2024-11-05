package com.artsem.api.driverservice.exceptions.driver;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class DriversNotFoundException extends RuntimeException {
    public DriversNotFoundException() {
        super(ExceptionKeys.DRIVERS_NOT_FOUND);
    }
}
