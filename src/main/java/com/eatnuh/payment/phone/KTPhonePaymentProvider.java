package com.eatnuh.payment.phone;

import com.eatnuh.payment.PaymentProvider;

public class KTPhonePaymentProvider implements PaymentProvider {
    @Override
    public void pay(Object paymentInfo) {
        KTPhonePaymentInfo ktPhonePaymentInfo = (KTPhonePaymentInfo) paymentInfo;

        String phoneNo = ktPhonePaymentInfo.getPhoneNo();
        String name = ktPhonePaymentInfo.getName();

        System.out.println("-----KT 핸드폰 결제-----");
        System.out.println(phoneNo + " " + name);
    }

    @Override
    public boolean supports(Class<?> paymentInfoClass) {
        return KTPhonePaymentInfo.class.isAssignableFrom(paymentInfoClass);
    }
}
