package com.eatnuh.payment.provider;

import com.eatnuh.payment.request.KookminCardPaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KookminCardPaymentProvider implements PaymentProvider {

    @Override
    public void pay(Object paymentRequest) {
        KookminCardPaymentRequest kookminCardPaymentRequest = (KookminCardPaymentRequest) paymentRequest;

        String cardNo = kookminCardPaymentRequest.getCardNo();
        String expiredYear = kookminCardPaymentRequest.getExpiredYear();
        String cvc = kookminCardPaymentRequest.getCvc();
        long totalAmount = kookminCardPaymentRequest.getTotalAmount();

        log.info("-----국민카드 결제-----");
        log.info(cardNo + " " + expiredYear + " " + cvc + " " + totalAmount);

        // Payment Proceed..
    }

    @Override
    public boolean supports(Class<?> paymentRequestType) {
        return KookminCardPaymentRequest.class.isAssignableFrom(paymentRequestType);
    }
}
