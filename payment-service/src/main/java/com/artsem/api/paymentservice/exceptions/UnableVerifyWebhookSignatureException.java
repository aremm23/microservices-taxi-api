package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;

public class UnableVerifyWebhookSignatureException extends RuntimeException {
    public UnableVerifyWebhookSignatureException() {
        super(ExceptionKeys.INVALID_WEBHOOK_SIGNATURE);
    }
}
