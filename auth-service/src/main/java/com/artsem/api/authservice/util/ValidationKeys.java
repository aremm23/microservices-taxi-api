package com.artsem.api.authservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationKeys {
    public static final String USERNAME_REQUIRED = "username-required";
    public static final String USERNAME_MAX_SIZE = "username-max-size";
    public static final String PASSWORD_REQUIRED = "password-required";
    public static final String PASSWORD_MIN_SIZE = "password-min-size";
    public static final String EMAIL_REQUIRED = "email-required";
    public static final String INVALID_EMAIL_FORMAT = "invalid-email-format";
    public static final String FIRSTNAME_REQUIRED = "firstname-required";
    public static final String FIRSTNAME_MAX_SIZE = "firstname-max-size";
    public static final String SURNAME_REQUIRED = "surname-required";
    public static final String SURNAME_MAX_SIZE = "surname-max-size";
    public static final String USER_ROLE_REQUIRED = "user-role-required";
    public static final String UNKNOWN_MESSAGE = "unknown-message";
}
