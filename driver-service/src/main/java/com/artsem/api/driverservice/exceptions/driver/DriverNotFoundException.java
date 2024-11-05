package com.artsem.api.driverservice.exceptions.driver;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException() {
        super(ExceptionKeys.DRIVER_NOT_FOUND);
    }
}
