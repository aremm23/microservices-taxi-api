package com.artsem.api.authservice.util;

import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

public class StatusCodeValidator {
    public static final Set<Integer> VALID_STATUSES = Set.of(200, 201, 204, 209);

    public static void validate(Response response) {
        int status = response.getStatus();
        String reasonPhrase = response.getStatusInfo().getReasonPhrase();
        if (!VALID_STATUSES.contains(status)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(status), reasonPhrase);
        }
    }
}
