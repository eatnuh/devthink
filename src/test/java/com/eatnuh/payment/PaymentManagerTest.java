package com.eatnuh.payment;

import com.eatnuh.payment.provider.KTPhonePaymentProvider;
import com.eatnuh.payment.provider.KakaoPayPaymentProvider;
import com.eatnuh.payment.provider.KookminCardPaymentProvider;
import com.eatnuh.payment.provider.ShinhanCardPaymentProvider;
import com.eatnuh.payment.request.KTPhonePaymentRequest;
import com.eatnuh.payment.request.KakaoPayPaymentRequest;
import com.eatnuh.payment.request.KookminCardPaymentRequest;
import com.eatnuh.payment.request.ShinhanCardPaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("결제매니저 테스트")
@ExtendWith(MockitoExtension.class)
class PaymentManagerTest {

    private PaymentManager paymentManager;

    @Spy
    private KookminCardPaymentProvider kookminCardPaymentProvider;

    @Spy
    private ShinhanCardPaymentProvider shinhanCardPaymentProvider;

    @Spy
    private KTPhonePaymentProvider ktPhonePaymentProvider;

    @Spy
    private KakaoPayPaymentProvider kakaoPayPaymentProvider;

    @BeforeEach
    void beforeEach() {
        lenient().doNothing().when(kookminCardPaymentProvider).pay(any());
        lenient().doNothing().when(shinhanCardPaymentProvider).pay(any());
        lenient().doNothing().when(ktPhonePaymentProvider).pay(any());
        lenient().doNothing().when(kakaoPayPaymentProvider).pay(any());

        this.paymentManager = new PaymentManager()
                .add(kookminCardPaymentProvider)
                .add(shinhanCardPaymentProvider)
                .add(ktPhonePaymentProvider)
                .add(kakaoPayPaymentProvider);
    }


    @Test
    @DisplayName("국민카드 결제요청 시 국민카드 결제 진행")
    void kookminCardRequestTest() {
        //given
        KookminCardPaymentRequest paymentRequest =
                new KookminCardPaymentRequest(
                        "1234-1234-1234-1234",
                        "23",
                        "345",
                        10000L
                );

        //when
        paymentManager.pay(paymentRequest);

        //then
        verify(kookminCardPaymentProvider, times(1)).pay(paymentRequest);
        verify(shinhanCardPaymentProvider, never()).pay(paymentRequest);
        verify(ktPhonePaymentProvider, never()).pay(paymentRequest);
        verify(kakaoPayPaymentProvider, never()).pay(paymentRequest);
    }

    @Test
    @DisplayName("신한카드 결제요청시 신한카드 결제 진행")
    void shinhanCardRequestTest() {
        //given
        ShinhanCardPaymentRequest paymentRequest =
                new ShinhanCardPaymentRequest(
                        "1234-1234-1234-1234",
                        "25",
                        "123",
                        10000L
                );
        //when
        paymentManager.pay(paymentRequest);

        //then
        verify(kookminCardPaymentProvider, never()).pay(paymentRequest);
        verify(shinhanCardPaymentProvider, times(1)).pay(paymentRequest);
        verify(ktPhonePaymentProvider, never()).pay(paymentRequest);
        verify(kakaoPayPaymentProvider, never()).pay(paymentRequest);

    }

    @Test
    @DisplayName("KT 핸드폰 결제요청시 KT 핸드폰 결제 진행")
    void KtPhoneRequestTest() {
        //given
        KTPhonePaymentRequest paymentRequest =
                new KTPhonePaymentRequest(
                        "010-2345-2345",
                        "john",
                        30000L
                );
        //when
        paymentManager.pay(paymentRequest);

        //then
        verify(kookminCardPaymentProvider, never()).pay(paymentRequest);
        verify(shinhanCardPaymentProvider, never()).pay(paymentRequest);
        verify(ktPhonePaymentProvider, times(1)).pay(paymentRequest);
        verify(kakaoPayPaymentProvider, never()).pay(paymentRequest);

    }

    @Test
    @DisplayName("카카오페이 결제 요청시 카카오페이 결제 진행")
    void kakaoPayRequestTest() {
        //given
        KakaoPayPaymentRequest paymentRequest =
                new KakaoPayPaymentRequest(
                        "testId",
                        30000L
                );
        //when
        paymentManager.pay(paymentRequest);

        //then
        verify(kookminCardPaymentProvider, never()).pay(paymentRequest);
        verify(shinhanCardPaymentProvider, never()).pay(paymentRequest);
        verify(ktPhonePaymentProvider, never()).pay(paymentRequest);
        verify(kakaoPayPaymentProvider, times(1)).pay(paymentRequest);

    }

}