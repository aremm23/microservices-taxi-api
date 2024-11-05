package com.artsem.api.driverservice.exceptions.driver;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class DriverNotUpdatedException extends RuntimeException {
    public DriverNotUpdatedException() {
        super(ExceptionKeys.DRIVER_NOT_UPDATED);
    }
}
