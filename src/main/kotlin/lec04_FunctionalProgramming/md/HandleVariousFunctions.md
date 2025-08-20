# 코틀린의 다양한 함수 다루기

코틀린은 함수형 프로그래밍 패러다임을 지원하며, 코드의 재사용성과 가독성을 높이는 여러 종류의 함수를 제공한다.

---

## 1. 확장 함수 (Extension Functions)

**확장 함수**는 기존 클래스의 코드를 직접 수정하지 않고, 클래스 외부에서 새로운 함수를 추가할 수 있게 해주는 기능이다.

### 사용법

`fun 확장할클래스.함수이름() { ... }` 형태로 정의한다. 함수 내부에서는 `this` 키워드를 통해 함수를 호출한 인스턴스에 접근할 수 있으며, 이 `this`를 **수신 객체**라고 부흔다.

```kotlin
// String 클래스에 lastChar 확장 함수 추가
fun String.lastChar(): Char {
    return this[this.length - 1]
}

fun main() {
    val str = "ABC"
    // String의 멤버 함수처럼 호출 가능
    println(str.lastChar()) // 출력: C
}
```

### 주요 특징 및 주의사항

1.  **캡슐화 유지**: 확장 함수는 수신 객체 클래스의 `private`이나 `protected` 멤버에 접근할 수 없다.
2.  **멤버 함수 우선**: 만약 멤버 함수와 확장 함수의 시그니처가 동일하다면, 항상 **멤버 함수가 우선적으로 호출**된다. 이로 인해 기존 멤버 함수와 동일한 확장 함수를 만들면 의도치 않은 동작이 발생할 수 있어 주의가 필요하다.
3.  **정적 디스패치**: 확장 함수는 변수의 정적 타입(컴파일 시점에 선언된 타입)에 의해 어떤 함수가 호출될지 결정된다. 즉, 오버라이드되지 않는다.

---

## 2. Infix 함수 (중위 함수)

**Infix 함수**는 새로운 종류의 함수가 아니라, 함수를 `.`이나 `()` 없이 마치 연산자처럼 호출할 수 있게 해주는 **호출 방식**이다. `downTo`, `step`과 같은 코틀린 표준 라이브러리 함수들도 중위 함수다.

### 사용법

함수 선언 앞에 `infix` 키워드를 붙인다. Infix 함수는 다음 조건을 만족해야 한다.

-   멤버 함수이거나 확장 함수여야 한다.
-   파라미터가 하나여야 한다.

```kotlin
// Int에 중위 함수 add를 확장 함수로 추가
infix fun Int.add(other: Int): Int {
    return this + other
}

fun main() {
    // 일반적인 함수 호출
    val sum1 = 3.add(4)

    // Infix 표기법을 사용한 중위 호출
    val sum2 = 3 add 4

    println(sum1) // 출력: 7
    println(sum2) // 출력: 7
}
```

---

## 3. Inline 함수

**Inline 함수**는 함수가 호출될 때마다 스택 프레임을 생성하는 대신, 함수 본문의 코드를 호출 지점에 그대로 복사해 넣는 함수다.

### 사용법

함수 선언 앞에 `inline` 키워드를 붙인다.

```kotlin
inline fun Int.add(other: Int): Int {
    return this + other
}

fun main() {
    val result = 3.add(4)
}
```
위 코드는 컴파일 시 아래와 같이 변환된다. `add` 함수 호출 없이, 본문 내용(`3 + 4`)이 바로 `main` 함수 안에 삽입된다.

```java
// Decompiled Java Code
public static final void main() {
   int $this$add$iv = 3;
   int other$iv = 4;
   int var10000 = $this$add$iv + other$iv;
}
```
주로 람다를 파라미터로 받는 고차 함수에서 발생하는 오버헤드를 줄이기 위해 사용되며, 성능에 미치는 영향을 고려하여 신중하게 사용해야 한다.

---

## 4. 지역 함수 (Local Functions)

**지역 함수**는 이름 그대로, 함수 내부에 선언된 함수로, 특정 함수의 내부에서만 사용되는 로직을 캡슐화하고 중복을 제거할 때 유용하다.

### 사용법

함수 내부에 일반 함수처럼 `fun` 키워드로 선언한다. 지역 함수는 자신이 선언된 바깥 함수의 변수나 파라미터에 접근할 수 있다.

```kotlin
class Person(val firstName: String, val lastName: String, val age: Int)

fun createPerson(firstName: String, lastName: String): Person {
    // 'validateName' 이라는 지역 함수 선언
    fun validateName(name: String, fieldName: String) {
        if (name.isEmpty()) {
            throw IllegalArgumentException("$fieldName 은 비어있을 수 없습니다. 현재 값: $name")
        }
    }

    // 지역 함수를 사용하여 로직 중복 제거
    validateName(firstName, "firstName")
    validateName(lastName, "lastName")

    return Person(firstName, lastName, 1)
}
```
다만, 함수 안에 함수가 있어 코드의 깊이(depth)가 깊어지고 가독성이 떨어질 수 있다.

---

## 최종 요약

1.  **확장 함수**는 Java 코드가 있는 상황에서 Kotlin 코드로 새 기능을 개발하기 위해 등장했다.
2.  확장 함수는 원본 클래스의 **`private`, `protected` 멤버에 접근할 수 없다.**
3.  멤버 함수와 확장 함수의 시그니처가 같다면 **멤버 함수가 우선권**을 갖는다.
4.  확장 함수는 변수의 **정적 타입(현재 타입)을 기준**으로 호출된다.
5.  Java의 `static` 유틸리티 메소드처럼 코틀린의 확장 함수를 활용할 수 있다.
6.  **Infix 함수**는 `변수 함수이름 인자` 형태로 호출 방식을 바꿔주는 기능이다.
7.  **Inline 함수**는 성능 오버헤드를 줄이기 위해 함수 본문을 호출 지점에 복사-붙여넣기 한다.
8.  코틀린에서는 함수 안에 함수를 선언할 수 있으며, 이를 **지역 함수**라고 부른다.
