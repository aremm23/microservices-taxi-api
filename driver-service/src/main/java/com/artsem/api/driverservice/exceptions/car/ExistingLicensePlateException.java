package com.artsem.api.driverservice.exceptions.car;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class ExistingLicensePlateException extends RuntimeException {
    public ExistingLicensePlateException() {
        super(ExceptionKeys.LICENCE_PLATE_EXIST);
    }
}
