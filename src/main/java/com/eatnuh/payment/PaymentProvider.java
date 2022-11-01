package com.eatnuh.payment;

public interface PaymentProvider {

    void pay(Object paymentInfo);

    boolean supports(Class<?> paymentInfoClass);
}
