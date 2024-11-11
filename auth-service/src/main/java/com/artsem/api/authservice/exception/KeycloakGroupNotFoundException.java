package com.artsem.api.authservice.exception;

import com.artsem.api.authservice.util.ExceptionKeys;

public class KeycloakGroupNotFoundException extends RuntimeException{
    public KeycloakGroupNotFoundException() {
        super(ExceptionKeys.GROUP_NOT_FOUND);
    }
}
