package com.eatnuh.payment.provider;

import com.eatnuh.payment.request.KTPhonePaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KTPhonePaymentProvider implements PaymentProvider {
    @Override
    public void pay(Object paymentRequest) {

        KTPhonePaymentRequest ktPhonePaymentRequest = (KTPhonePaymentRequest) paymentRequest;

        String phoneNo = ktPhonePaymentRequest.getPhoneNo();
        String name = ktPhonePaymentRequest.getName();
        long totalAmount = ktPhonePaymentRequest.getTotalAmount();
        log.info("-----KT 핸드폰 결제-----");
        log.info(phoneNo + " " + name + " " + totalAmount);

        // Payment Proceed..
    }

    @Override
    public boolean supports(Class<?> paymentRequestType) {
        return KTPhonePaymentRequest.class.isAssignableFrom(paymentRequestType);
    }
}
