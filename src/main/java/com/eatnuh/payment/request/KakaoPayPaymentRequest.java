package com.eatnuh.payment.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoPayPaymentRequest {
    private final String kakaoId;
    private final long totalAmount;
}
