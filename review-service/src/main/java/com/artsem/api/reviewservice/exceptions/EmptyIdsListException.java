package com.artsem.api.reviewservice.exceptions;

import com.artsem.api.reviewservice.util.ExceptionKeys;

public class EmptyIdsListException extends RuntimeException {
    public EmptyIdsListException() {
        super(ExceptionKeys.EMPTY_IDS_LIST);
    }
}
