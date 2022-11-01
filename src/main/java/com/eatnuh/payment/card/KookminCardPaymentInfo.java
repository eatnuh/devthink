package com.eatnuh.payment.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KookminCardPaymentInfo {

    private final String cardNo;
    private final String expiredYear;
    private final String cvc;

}
