package com.eatnuh.payment.provider;

import com.eatnuh.payment.request.KakaoPayPaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoPayPaymentProvider implements PaymentProvider {
    
    @Override
    public void pay(Object paymentRequest) {
        KakaoPayPaymentRequest kakaoPaymentRequest = (KakaoPayPaymentRequest) paymentRequest;

        String kakaoId = kakaoPaymentRequest.getKakaoId();
        long totalAmount = kakaoPaymentRequest.getTotalAmount();

        log.info("카카오페이 결제");
        log.info(kakaoId + " " + totalAmount);

        // Payment Proceed..
    }

    @Override
    public boolean supports(Class<?> paymentRequestType) {
        return KakaoPayPaymentRequest.class.isAssignableFrom(paymentRequestType);
    }
}
