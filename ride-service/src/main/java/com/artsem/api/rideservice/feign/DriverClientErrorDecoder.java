package com.artsem.api.rideservice.feign;

import com.artsem.api.rideservice.exception.DriverNotFoundException;
import com.artsem.api.rideservice.exception.PassengerNotFoundException;
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
public class DriverClientErrorDecoder implements ErrorDecoder {

    private static final String RIDE_NOT_FOUND_MESSAGE = "Driver not found";
    private static final String MESSAGE_FIELD = "message";
    private static final int RESPONSE_BAD_REQUEST_CODE = 400;
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == RESPONSE_BAD_REQUEST_CODE) {
            return handleBadRequest(response);
        }
        return new Default().decode(methodKey, response);
    }

    @SneakyThrows
    private Exception handleBadRequest(Response response) {
        Map<String, String> errorResponse = objectMapper.readValue(
                response.body().asInputStream(),
                new TypeReference<>() {
                }
        );
        String message = errorResponse.get(MESSAGE_FIELD);
        if (RIDE_NOT_FOUND_MESSAGE.equalsIgnoreCase(message)) {
            return new DriverNotFoundException();
        }
        return new Default().decode("", response);
    }
}
