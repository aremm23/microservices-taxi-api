package com.artsem.api.authservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionKeys {
    public static final String INVALID_RESPONSE_STATUS = "invalid-response-status";
    public static final String INVALID_USER_ROLE = "invalid-user-role";
    public static final String GROUP_NOT_FOUND = "group_not_found";
    public static final String INVALID_TOKEN_TYPE = "invalid-token-type";
    public static final String TOKEN_EXPIRED = "token-expired";
    public static final String INVALID_USER_EMAIL = "invalid-user-email";
}