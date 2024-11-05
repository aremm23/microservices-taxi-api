package com.artsem.api.driverservice.exceptions.driver;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class DriverNotCreatedException extends RuntimeException {
    public DriverNotCreatedException() {
        super(ExceptionKeys.DRIVER_NOT_CREATED);
    }
}
