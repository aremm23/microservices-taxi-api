package com.artsem.api.reviewservice.feign;

import com.artsem.api.reviewservice.exceptions.RideNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RideClientErrorDecoder implements ErrorDecoder {

    private static final String RIDE_NOT_FOUND_MESSAGE = "Ride not found.";
    private static final String MESSAGE_FIELD = "message";
    private static final int RESPONSE_NOT_FOUND_CODE = 404;
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == RESPONSE_NOT_FOUND_CODE) {
            return handleNotFound(response);
        }
        return new Default().decode(methodKey, response);
    }

    @SneakyThrows
    private Exception handleNotFound(Response response) {
        Map<String, String> errorResponse = objectMapper.readValue(
                response.body().asInputStream(),
                new TypeReference<>() {
                }
        );
        String message = errorResponse.get(MESSAGE_FIELD);
        if (RIDE_NOT_FOUND_MESSAGE.equalsIgnoreCase(message)) {
            return new RideNotFoundException();
        }
        return new Default().decode("", response);
    }
}
