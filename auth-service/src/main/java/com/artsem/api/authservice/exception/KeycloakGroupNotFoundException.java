package com.artsem.api.authservice.exception;

public class KeycloakGroupNotFoundException extends RuntimeException{
    public KeycloakGroupNotFoundException(String message) {
        super(message);
    }
}
