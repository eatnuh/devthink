package com.eatnuh.payment;

import java.util.List;
import java.util.function.Predicate;

public class PaymentManager {

    private List<PaymentProvider> providers;

    public PaymentManager(List<PaymentProvider> providers) {
        this.providers = providers;
    }

    public void pay(Object paymentInfo) {
        for(PaymentProvider provider : providers) {
            if(provider.supports(paymentInfo.getClass())) provider.pay(paymentInfo);
        }
    }
}
