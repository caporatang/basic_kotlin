# ☕️ Kotlin의 제어문: if와 when

## 1. if문

Kotlin의 `if`문은 기본적인 사용법에서 Java와 유사하다.

* **Java 예시**
    ```java
    private void validateScoreIsNotNegative(int score) {
        if (score < 0) {
            throw new IllegalArgumentException(String.format("%s는 0보다 작을 수 없습니다.", score));
        }
    }
    ```
* **Kotlin 예시**
    ```kotlin
    fun validateScoreIsNotNegative(score: Int) {
        if (score < 0) {
            throw IllegalArgumentException("${score}는 0보다 작을 수 없습니다.")
        }
    }
    ```

> **차이점**: Kotlin에서는 `void`에 해당하는 `Unit`이 생략 가능하고, 예외(Exception)를 생성할 때 `new` 키워드를 사용하지 않는다. 기본적인 `if`문의 구조는 동일하다.

---

## 2. Expression과 Statement

`if-else`를 사용하는 방법은 같지만, Java와 Kotlin의 차이가 있다.

* **Statement**: 프로그램의 완전한 하나의 문장, 그 자체로 **하나의 값으로 도출되지 않을 수 있다.**
* **Expression**: 하나의 **값으로 도출되는** 문장이다.   
* 즉, 모든 Expression은 Statement이지만, 모든 Statement가 Expression인 것은 아니다.

### Java의 if-else (Statement)

Java에서 `if-else`는 **Statement**이므로, 그 결과를 변수에 직접 할당할 수 없다.

```java
// 아래 코드는 컴파일 에러가 발생
String grade = if(score >= 50) { ... }

//이러한 상황을 위해 Java는 삼항 연산자를 제공하며, 이것은 Expression이다.
// Java에서는 삼항 연산자를 사용해야 한다.
String grade = score >= 50 ? "P" : "F";
```
### Kotlin의 if-else (Expression)

Kotlin에서 `if-else`는 **Expression**이므로, 그 자체로 값을 반환할 수 있다. 따라서 **삼항 연산자가 필요 없다.**

```kotlin
fun getPassOrFail(score: Int): String {
    // if-else 자체가 값을 반환하므로 바로 return 할 수 있다.
    return if (score >= 50) {
        "P"
    } else {
        "F"
    }
}
```
### 범위(Range)를 활용한 조건 검사
Kotlin에서는 `in` 연산자와 범위(`..`)를 사용하여 더 깔끔하게 조건을 표현할 수 있다.

* **Java 방식**
```java
    if (0 <= score && score <= 100) {
      // ...
    }
```

* **Kotlin 방식**
```kotlin
    fun boxNotBox(score: Int) {
        // !in을 사용하여 범위에 포함되지 않는 경우를 간결하게 체크
        if (score !in 0..100) {
            throw IllegalArgumentException("score의 범위는 0부터 100임")
        }
    }

    fun validateScore(score: Int) {
        // in을 사용하여 범위 포함 여부 체크
        if (score in 0..100) {
            println("0과 100사이 어디인가~ 있다~")
        }
    }
```

## 3. switch와 when

Kotlin에서는 `switch` 문이 사라지고, `when`을 사용한다.

### 기본 사용법

* **Java `switch` 예시**
```java
    private String getGradeWithSwitch(int score) {
        switch (score / 10) {
            case 9:
                return "A";
            case 8:
                return "B";
            case 7:
                return "C";
            default:
                return "D";
        }
    }
```

* **Kotlin `when` 예시**
```kotlin
    fun getGradeWithSwitch(score: Int): String {
        // when도 Expression이므로 바로 return 가능
        return when (score / 10) {
            9 -> "A"
            8 -> "B"
            7 -> "C"
            else -> "D"
        }
    }
```

### when의 다양한 활용법

`when`은 단순 값 비교 이외에 여러가지 방법으로 활용할 수 있다.

1.  **범위(in)와 함께 사용**
```kotlin
    fun getGradeWithSwitch2(score: Int): String {
        return when (score) {
            in 90..100 -> "A" // 90..99가 더 정확할 수 있음
            in 80..89 -> "B"
            in 70..79 -> "C"
            else -> "D"
        }
    }
```

2.  **여러 조건을 동시에 검사**
```kotlin
    fun judgeNumber(number: Int) {
        when (number) {
            1, 0, -1 -> println("어디서 많이 본 숫자입니다.")
            else -> println("1, 0, -1이 아닙니다")
        }
    }
```

3.  **인자 없는 when 사용**
    `when`에 인자(값) 없이 사용하면, Java의 `if-else if-else` 체인과 유사하게 동작한다.
```kotlin
    fun judgeNumber2(number: Int) {
        when {
            number == 0 -> println("주어진 숫자는 0")
            number % 2 == 0 -> println("주어진 숫자는 짝수")
            else -> println("주어진 숫자는 홀수입니다.")
        }
    }
```

**when의 구조**: `when (값)`의 조건부에는 `is Type`, `in Range` 등 어떠한 Expression이라도 들어갈 수 있어 유연하게 활용할 수 있다.

## ✨ 최종 정리

* `if` / `if-else` / `if-else if` 문법은 Java와 **동일하다.**
* 단, Kotlin에서는 `if`문이 **Expression**으로 취급되므로, 그 결과를 바로 반환하거나 변수에 담을 수 있다. 이 때문에 **삼항 연산자가 없습니다.**
* Java의 `switch`는 Kotlin에서 `when`으로 대체되었으며, `when`은 범위, 타입, 다중 조건 등 훨씬 유연하게 활용할 수 있다.