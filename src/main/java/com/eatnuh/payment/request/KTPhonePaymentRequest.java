package com.eatnuh.payment.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KTPhonePaymentRequest {

    private final String phoneNo;
    private final String name;
    private final long totalAmount;

}
