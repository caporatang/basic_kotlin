# ☕️ Kotlin의 반복문 다루기

## 1. for-each (향상된 for문)

Java의 향상된 for문과 Kotlin의 for-each문은 매우 유사하며, `Iterable`이 구현된 모든 타입을 순회할 수 있다. 가장 큰 차이점은 콜론(`:`) 대신 `in` 키워드를 사용하는 것이다.

* **Java 예시**
```java
    List<Long> numbers = Arrays.asList(1L, 2L, 3L);
    for (long number : numbers)  {
        System.out.println(number);
    }
```

* **Kotlin 예시**
```kotlin
    val numbers = listOf(1L, 2L, 3L)
    for (number in numbers) {
        println(number)
    }
```

> **핵심**: Java의 `:`가 Kotlin에서는 `in`으로 변경되었다.

## 2. 전통적인 for문

Kotlin에서는 Java의 전통적인 `for (int i = 0; ...)` 문법 대신, 범위(Range)와 `in` 키워드를 조합하여 훨씬 간결하게 표현 가능하다.

#### **1) 1씩 증가**
* **Java 예시**
```java
    for (int i = 1; i <= 3; i++) {
        System.out.println(i);
    }
```
* **Kotlin 예시**
```kotlin
    for (i in 1..3) {
        println(i)
    }
```

#### **2) 1씩 감소**
* **Java 예시**
```java
    for (int i = 3; i >= 1; i--) {
        System.out.println(i);
    }
```
* **Kotlin 예시**
  `downTo` 중위 함수 사용.
```kotlin
    for (i in 3 downTo 1) {
        println(i)
    }
```

#### **3) N만큼 증가 (step)**
* **Java 예시**
```java
    for (int i = 1; i <= 5; i += 2) {
        System.out.println(i);
    }
```
* **Kotlin 예시**
  `step` 중위 함수를 사용
```kotlin
    for (i in 1..5 step 2) {
        println(i)
    }
```

---

## 3. Progression과 Range

**"Kotlin의 for문은 왜 이렇게 동작하는가?"**

그 이유는 Kotlin의 for문이 **등차수열(Progression)** 개념을 기반으로 하기 때문이다.

1.  `..` 연산자는 `IntRange`라는 실제 클래스 객체를 만든다.
2.  `IntRange` 클래스는 `IntProgression` 클래스를 상속는데, 이는 이름 그대로 **등차수열**을 의미한다.
3.  등차수열은 **시작 값, 끝 값, 공차(step)** 세 가지 요소로 이루어진다.
4.  `1..5`는 `시작 값: 1`, `끝 값: 5`, `공차: 1`인 등차수열을 만드는 것과 같다.
5.  `3 downTo 1`이나 `1..5 step 2`와 같은 문법은 이 등차수열 객체를 만드는 **중위 함수(infix function)** 호출일 뿐이다.

> **결론**: Kotlin의 전통적인 `for`문은 내부적으로 등차수열(Progression) 객체를 만들어 순회하는 방식이다.

---

## 4. while문

`while`문과 `do-while`문은 Java와 문법 및 동작 방식이 **동일하다.**

```kotlin
var i = 1
while (i <= 3) {
    println(i)
    i++
}
```

## ✨ 최종 정리

* **for-each문**: Java는 `:`(콜론)을, Kotlin은 `in`을 사용한다.
* **전통적인 for문**: Kotlin은 `..`, `downTo`, `step`과 같은 함수로 **등차수열**을 만들어 `in`과 함께 사용한다.
* **while문과 do-while문**은 Java와 **완전히 동일**하게 동작한다.