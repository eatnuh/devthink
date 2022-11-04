package com.eatnuh.payment;

import com.eatnuh.payment.provider.PaymentProvider;

import java.util.ArrayList;
import java.util.List;

public class PaymentManager {

    List<PaymentProvider> providers;

    public PaymentManager() {
        this.providers = new ArrayList<>();
    }

    public void pay(Object paymentRequest) {
        for (PaymentProvider provider : providers) {
            if (provider.supports(paymentRequest.getClass())) {
                provider.pay(paymentRequest);
            }
        }
    }

    public PaymentManager add(PaymentProvider provider) {
        this.providers.add(provider);
        return this;
    }

}
