package com.artsem.api.authservice.exception;

import com.artsem.api.authservice.util.ExceptionKeys;

public class ConfirmationTokenExpiredException extends RuntimeException {
    public ConfirmationTokenExpiredException() {
        super(ExceptionKeys.TOKEN_EXPIRED);
    }
}
