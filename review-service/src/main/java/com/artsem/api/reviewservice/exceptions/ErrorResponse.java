package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.PatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_PATTERN)
        LocalDateTime timestamp) {
}
