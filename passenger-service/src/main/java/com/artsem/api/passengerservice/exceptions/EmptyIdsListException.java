package com.artsem.api.passengerservice.exceptions;

import com.artsem.api.passengerservice.util.ExceptionKeys;

public class EmptyIdsListException extends RuntimeException {
    public EmptyIdsListException() {
        super(ExceptionKeys.EMPTY_IDS_LIST);
    }
}
