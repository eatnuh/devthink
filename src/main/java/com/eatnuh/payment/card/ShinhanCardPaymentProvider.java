package com.eatnuh.payment.card;

import com.eatnuh.payment.  PaymentProvider;

public class ShinhanCardPaymentProvider implements PaymentProvider {
    
    @Override
    public void pay(Object paymentInfo) {
        ShinhanCardPaymentInfo shinhanCardPaymentInfo = (ShinhanCardPaymentInfo) paymentInfo;

        String cardNo = shinhanCardPaymentInfo.getCardNo();
        String expiredYear = shinhanCardPaymentInfo.getExpiredYear();
        String cvc = shinhanCardPaymentInfo.getCvc();

        System.out.println("-----신한카드 결제-----");
        System.out.println(cardNo + " " + expiredYear + " " + cvc);
    }

    @Override
    public boolean supports(Class<?> paymentInfoClass) {
        return ShinhanCardPaymentInfo.class.isAssignableFrom(paymentInfoClass);
    }
}
