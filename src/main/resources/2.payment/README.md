# 스프링 시큐리티 인증 방식을 활용한 가상 다중 결제 처리

------

## 0. 소개

프로젝트를 하면서 인증 처리를 위해 스프링 시큐리티를 사용했었다.
스프링 시큐리티는 다양한 인증방식을 지원한다.
인증처리는 AuthenticationManager 구현체인 ProviderManager에서 모든 인증처리를 담당한다.
어떻게 이것이 가능한지 공부했던 내용과 이를 응용해서 만들어본 다양한 필드를 가지는 결제요청을
가상으로 처리하는 모듈을 만들어본 내용입니다.

참고 : 서블릿 애플리케이션(SpringMvc) 기준입니다.

## 1. 스프링 시큐리티 인증처리 요약

### 1.1. Authentication

다양한 인증 방식을 추상화한 인터페이스

```java
public interface Authentication extends Principal, Serializable {

    Collection<? extends GrantedAuthority> getAuthorities();    // 가지고 있는 권한들

    Object getCredentials();                                    // 자격증명 ex) 비밀번호

    Object getDetails();                                        // 추가 세부정보 ex) IP Address

    Object getPrincipal();                                      // 인증주체의 식별자 ex) id, email

    boolean isAuthenticated();                                  // 인증처리 여부

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;     // 인증처리 여부 변경자

}
```

### 1.2. AuthenticationManager

Authentication(인증 정보) 타입 파라미터를 받아서 인증처리 후 리턴한다 인증실패시 AuthenticationException 발생

```java
public interface AuthenticationManager {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

}
```

### 1.3. ProviderManager

AuthenticationManager의 구현체 SpringBoot 프로젝트에서는 자동으로 빈으로 등록된다.
필드값중에 List\<AuthenticationProvider\>의 provider는 각각 시큐리티의 다양한 인증처리를 제공한다.
리스트를 순회하면서 인증요청을 지원하는 provider로 인증처리한다.

```java
public class ProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {
    // 필드 생략 
    private List<AuthenticationProvider> providers;

    // Authentication 인증처리 오버라이드한 메서드
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        //생략..
        Iterator var9 = this.getProviders().iterator();

        while (var9.hasNext()) {
            AuthenticationProvider provider = (AuthenticationProvider) var9.next();
            if (provider.supports(toTest)) {    // AuthenticationProvider가 인증 지원여부 판단
                // 생략..

                try {
                    // 인증처리를 AuthenticationProvider에 위임
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        this.copyDetails(authentication, result);
                        break;
                    }
                } catch (InternalAuthenticationServiceException | AccountStatusException var14) {
                    this.prepareException(var14, authentication);
                    throw var14;
                } catch (AuthenticationException var15) {
                    lastException = var15;
                }
            }
        }
        // 생략..

    }

}
```

### 1.4. AuthenticationProvider
ProviderManager로부터 인증처리를 위임받는다.
```java
public interface AuthenticationProvider {

    // 인증처리한다.	
    Authentication authenticate(Authentication authentication) throws AuthenticationException;

    // authentication의 구현체 클래스를 파라메터로 받아서 지원하는지 방식인지 판단
    boolean supports(Class<?> authentication);

}
```

## 2. 시큐리티 인증방식을 참고한 가상 결제처리 모듈

### 2.1. PaymentManager

다양한 필드를 가지는 결제요청(카드, 핸드폰, 카카오페이)를 받아서 결제처리한다.

```java
public class PaymentManager {
    // 결제요청을 위임할 provider들
    List<PaymentProvider> providers;

    public PaymentManager() {
        this.providers = new ArrayList<>();
    }

    // provider 리스트를 순회하면서 지원하는 결제방식일때 결제처리를 한다.
    public void pay(Object paymentRequest) {
        for (PaymentProvider provider : providers) {
            if (provider.supports(paymentRequest.getClass())) {
                provider.pay(paymentRequest);
            }
        }
    }

    // provider 추가
    public PaymentManager add(PaymentProvider provider) {
        this.providers.add(provider);
        return this;
    }

}
```

### 2.2. PaymentProvider
PaymentManager로 부터 결제처리를 위임받는다.
```java
public interface PaymentProvider {
    // 결제처리
    void pay(Object paymentRequest);

    // 지원하는 방식인지 판단
    boolean supports(Class<?> paymentRequestType);
}

```

### 2.3. KookminCardPaymentProvider
PaymentManager의 구현체의 예시
```java

@Slf4j
public class KookminCardPaymentProvider implements PaymentProvider {

    // 국민카드 가상 결제 처리
    @Override
    public void pay(Object paymentRequest) {
        KookminCardPaymentRequest kookminCardPaymentRequest = (KookminCardPaymentRequest) paymentRequest;

        String cardNo = kookminCardPaymentRequest.getCardNo();
        String expiredYear = kookminCardPaymentRequest.getExpiredYear();
        String cvc = kookminCardPaymentRequest.getCvc();
        long totalAmount = kookminCardPaymentRequest.getTotalAmount();

        log.info("-----국민카드 결제-----");
        log.info(cardNo + " " + expiredYear + " " + cvc + " " + totalAmount);

        // Payment Proceed..
    }

    // 결제요청 클래스가 국민카드결제요청 클래스일때 이 방식을 지원한다.
    @Override
    public boolean supports(Class<?> paymentRequestType) {
        return KookminCardPaymentRequest.class.isAssignableFrom(paymentRequestType);
    }
}

```

### 2.4. 최종

```java
import com.eatnuh.payment.PaymentManager;
import com.eatnuh.payment.provider.KTPhonePaymentProvider;
import com.eatnuh.payment.provider.KakaoPayPaymentProvider;
import com.eatnuh.payment.provider.KookminCardPaymentProvider;
import com.eatnuh.payment.provider.ShinhanCardPaymentProvider;
import com.eatnuh.payment.request.KTPhonePaymentRequest;
import com.eatnuh.payment.request.KakaoPayPaymentRequest;
import com.eatnuh.payment.request.KookminCardPaymentRequest;
import com.eatnuh.payment.request.ShinhanCardPaymentRequest;

public class Main {
    public static void main(String[] args) {
        // 국민카드 결제, 신한카드 결제, KT핸드폰 결제, 카카오페이 결제 지원
        PaymentManager paymentManager = new PaymentManager()
                .add(new KookminCardPaymentProvider())
                .add(new ShinhanCardPaymentProvider())
                .add(new KTPhonePaymentProvider())
                .add(new KakaoPayPaymentProvider());

        // 국민카드 결제처리
        paymentManager.pay(new KookminCardPaymentRequest("1234-1234-1234-1234", "23", 30000L));

        // 신한카드 결제처리
        paymentManager.pay(new ShinhanCardPaymentRequest("1234-1234-1234-1234", "25", 40000L));

        // KT 핸드폰 결제처리
        paymentManager.pay(new KTPhonePaymentRequest("010-1234-1234", "john", 50000L));

        // 카카오페이 결제처리
        paymentManager.pay(new KakaoPayPaymentRequest("abcd", 10000L));

    }
}
```

### 2.5. 생각정리

java는 타입에 의한 런타임 에러를 최소화 하기 위해 발전해왔다고 생각한다. (JDK 1.5 제네릭 지원 등)
위의 방식은 다양한 결제요청을 받을 수 있지만 타입 안정성이 낮아진다고 생각한다. (PaymentManager의 pay메서드 파라메터 타입을 Object로 사용)
실제로 스프링 시큐리티를 쓰면서 authentication을 형변환해서 사용하는 코드들을 다수 찾아볼수 있었다.


[전체 코드](https://github.com/eatnuh/devthink/tree/main/src/main/java/com/eatnuh/payment)

