package com.eatnuh.payment.provider;

import com.eatnuh.payment.request.ShinhanCardPaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShinhanCardPaymentProvider implements PaymentProvider {

    @Override
    public void pay(Object paymentRequest) {
        ShinhanCardPaymentRequest shinhanCardPaymentRequest = (ShinhanCardPaymentRequest) paymentRequest;

        String cardNo = shinhanCardPaymentRequest.getCardNo();
        String expiredYear = shinhanCardPaymentRequest.getExpiredYear();
        String cvc = shinhanCardPaymentRequest.getCvc();
        long totalAmount = shinhanCardPaymentRequest.getTotalAmount();

        log.info("-----신한카드 결제-----");
        log.info(cardNo + " " + expiredYear + " " + cvc + " " + totalAmount);

        // Payment Proceed..
    }

    @Override
    public boolean supports(Class<?> paymentRequestType) {
        return ShinhanCardPaymentRequest.class.isAssignableFrom(paymentRequestType);
    }
}
