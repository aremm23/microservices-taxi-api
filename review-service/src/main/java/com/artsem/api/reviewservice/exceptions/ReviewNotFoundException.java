package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.ExceptionKeys;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super(ExceptionKeys.REVIEW_NOT_FOUND);
    }
}
