package com.artsem.api.paymentservice.exceptions;

import com.artsem.api.paymentservice.util.ExceptionKeys;
import com.stripe.exception.StripeException;
import lombok.Getter;

@Getter
public class StripeSessionException extends RuntimeException {
    private final String stripeExceptionMessage;

    public StripeSessionException(StripeException e) {
        super(ExceptionKeys.STRIPE_SESSION_EXCEPTION);
        this.stripeExceptionMessage = e.getMessage();
    }
}
