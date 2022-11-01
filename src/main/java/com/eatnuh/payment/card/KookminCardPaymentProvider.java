package com.eatnuh.payment.card;

import com.eatnuh.payment.PaymentProvider;

public class KookminCardPaymentProvider implements PaymentProvider {
    
    @Override
    public void pay(Object paymentInfo) {
        KookminCardPaymentInfo kookminCardPaymentInfo = (KookminCardPaymentInfo) paymentInfo;

        String cardNo = kookminCardPaymentInfo.getCardNo();
        String expiredYear = kookminCardPaymentInfo.getExpiredYear();
        String cvc = kookminCardPaymentInfo.getCvc();

        System.out.println("-----국민카드 결제-----");
        System.out.println(cardNo + " " + expiredYear + " " + cvc);
    }

    @Override
    public boolean supports(Class<?> paymentInfoClass) {
        return KookminCardPaymentInfo.class.isAssignableFrom(paymentInfoClass);
    }
}
