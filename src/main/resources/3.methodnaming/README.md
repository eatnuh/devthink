# 내가쓰는 메서드 작명 규칙 : create, get, find

-----

## 0. 소개
개인적으로 사용하는 인스턴스 반환 메서드 작명 규칙입니다.

## 1. create
> create는 생성한다는 의미이므로 인스턴스를 생성해서 반환할때 사용

```java
public class Foo {
    
    public Foo() {}
    
    public static createFoo() {
        return new Foo(); 
    }
    
}
```

## 2. get
> get은 무엇인가를 바로 가져온다는 의미이므로 인스턴스를 바로 반환할 수 있을때 사용
> 1. 이미 생성된 인스턴스 반환
> 2. 반환할 인스턴스 접근 시간이 O(1) ex) 필드값, Array, ArrayList, HsshMap 인덱스, 키 값 리턴 등
> 3. 인스턴스 접근시간을 모를 경우 : 메서드 파라메터가 없음 ex) interface의 추상메서드

```java
public class Foo {
    
    private Bar bar;
    
    public Bar getBar() {
        return this.bar; 
    }
}
```

```java
import java.util.HashMap;

public class Foo {
    private HashMap<String, String> hashMap;
    
    public String get(String key) {
        return hashMap.get(key);
    }
    
}
```

```java
public interface Foo {
    Bar getBar();
}
```

## 3. find
> find는 무엇인가를 찾는다는 의미이므로 인스턴스를 반환하는데 시간이 걸릴때 사용
> 1. 이미 생성된 인스턴스 반환
> 2. 반환할 인스턴스 접근 시간이 상수 시간이 아님
> 3. 인스턴스 접근시간을 모를 경우 : 메서드 파라메터가 있음

```java
import java.util.LinkedList;

public class Foo {
    private LinkedList<String> linkedList;

    public String find(int index) {
        return linkedList.get(index);
    }
}
```

```java
public interface Foo() {
    Bar findBar(String key);
}
```
