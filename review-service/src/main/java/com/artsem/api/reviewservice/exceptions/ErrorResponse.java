package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.PatternUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternUtil.DATE_PATTERN)
        LocalDateTime timestamp) {
}
