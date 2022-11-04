package com.eatnuh.payment.provider;

public interface PaymentProvider {

    void pay(Object paymentRequest);

    boolean supports(Class<?> paymentRequestType);
}
