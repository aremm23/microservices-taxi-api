package com.artsem.api.driverservice.exceptions;

import com.artsem.api.driverservice.util.ExceptionKeys;

public class EmptyIdsListException extends RuntimeException {
    public EmptyIdsListException() {
        super(ExceptionKeys.EMPTY_IDS_LIST);
    }
}
