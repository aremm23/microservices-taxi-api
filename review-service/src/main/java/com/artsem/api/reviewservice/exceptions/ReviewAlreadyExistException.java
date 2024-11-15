package com.artsem.api.reviewservice.exceptions;


import com.artsem.api.reviewservice.util.ExceptionKeys;

public class ReviewAlreadyExistException extends RuntimeException {
    public ReviewAlreadyExistException() {
        super(ExceptionKeys.REVIEW_ALREADY_EXIST);
    }
}
