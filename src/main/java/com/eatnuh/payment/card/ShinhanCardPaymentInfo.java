package com.eatnuh.payment.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShinhanCardPaymentInfo {

    private final String cardNo;
    private final String expiredYear;
    private final String cvc;

}
