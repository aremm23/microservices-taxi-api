package com.artsem.api.authservice.exception;

import com.artsem.api.authservice.util.ExceptionKeys;

public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException() {
        super(ExceptionKeys.INVALID_USER_ROLE);
    }
}
