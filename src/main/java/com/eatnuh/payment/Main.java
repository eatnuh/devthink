package com.eatnuh.payment;

import com.eatnuh.payment.card.KookminCardPaymentInfo;
import com.eatnuh.payment.card.KookminCardPaymentProvider;
import com.eatnuh.payment.card.ShinhanCardPaymentInfo;
import com.eatnuh.payment.card.ShinhanCardPaymentProvider;
import com.eatnuh.payment.phone.KTPhonePaymentInfo;
import com.eatnuh.payment.phone.KTPhonePaymentProvider;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PaymentManager paymentManager = new PaymentManager(
                List.of(
                        new ShinhanCardPaymentProvider(),
                        new KookminCardPaymentProvider(),
                        new KTPhonePaymentProvider()
                )
        );

        paymentManager.pay(new KTPhonePaymentInfo("010-2528-6291", "김태훈"));
        paymentManager.pay(new KookminCardPaymentInfo("1234-1234-1234-1234", "23", "348"));
        paymentManager.pay(new ShinhanCardPaymentInfo("1234-1234-1234-1234", "28", "586"));
    }
}
