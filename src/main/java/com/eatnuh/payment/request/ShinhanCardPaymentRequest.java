package com.eatnuh.payment.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShinhanCardPaymentRequest {

    private final String cardNo;
    private final String expiredYear;
    private final String cvc;
    private final long totalAmount;

}
