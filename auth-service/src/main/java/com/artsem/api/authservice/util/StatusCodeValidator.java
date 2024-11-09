package com.artsem.api.authservice.util;

import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

public class StatusCodeValidator {

    public static final Set<Integer> VALID_STATUSES = Set.of(200, 201, 204, 209);

    public static void validate(Response response) {
        int status = response.getStatus();
        if (!VALID_STATUSES.contains(status)) {
            throw new ResponseStatusException(HttpStatus.valueOf(status), ExceptionKeys.INVALID_RESPONSE_STATUS);
        }
    }
}
