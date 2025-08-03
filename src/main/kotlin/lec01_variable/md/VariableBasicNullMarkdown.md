# 코틀린에서 null 다루기

코틀린의 타입 시스템은 `null`을 가질 수 있는 참조(nullable types)와 가질 수 없는 참조(non-null types)를 명확하게 구분한다.  
이 덕분에 컴파일 시점에 잠재적인 `NullPointerException`을 잡아낼 수 있어 비교적 안전하게 사용할 수 있다.



## 1. Nullable 타입(`?`)과 명시적 검사

변수가 `null`을 가질 수 있도록 선언하려면 타입 뒤에 `?`를 붙인다. 코틀린 컴파일러는 nullable 변수의 프로퍼티나 메소드에 접근하기 전에 `null` 검사를 하도록 강제한다.

```kotlin
// 'String?' 타입은 'str'이 null일 수 있음을 의미하며, 컴파일러는 null 체크 없이는 'startsWith'를 바로 호출하는 것을 허용하지 않는다.

// 방법 1: null이면 예외 발생시키기
fun startsWithA1(str: String?): Boolean {
    if (str == null) {
        throw IllegalArgumentException("null값이 들어왔어요.")
    }
    // 이 블록 안에서 컴파일러는 'str'을 non-null로 간주합니다.
    return str.startsWith("A") 
}

// 방법 2: 입력이 null이면 null 반환하기
fun startsWithA2(str: String?): Boolean? {
    if (str == null) {
        return null
    }
    return str.startsWith("A")
}

// 방법 3: null이면 기본값(false) 반환하기
fun startsWithA3(str: String?): Boolean {
    if (str == null) {
        return false
    }
    return str.startsWith("A")
}

```

## 2. Safe Call (`?.`)과 Elvis 연산자 (`?:`)

코틀린은 nullable 타입을 더 간결하게 다룰 수 있는 연산자를 제공한다.

* **Safe Call (`?.`)**: 변수가 `null`이 아닐 때만 연산을 실행, 만약 변수가 `null`이면, 표현식 전체가 `null`이 된다.
* **Elvis 연산자 (`?:`)**: 왼쪽의 표현식이 `null`일 경우, 대체할 값이나 실행할 로직을 지정

```kotlin
// Safe Call과 Elvis 연산자를 사용하면 위 함수들을 더 간결하게 바꿀 수 있다.

fun startsWithA1Ko(str: String?): Boolean {
    return str?.startsWith("A")
        ?: throw IllegalArgumentException("null 들어왔음")
}

fun startsWithA2Ko(str: String?): Boolean? {
    return str?.startsWith("A")
}

fun startsWithA3Ko(str: String?): Boolean {
    return str?.startsWith("A") ?: false
}

```

## 3. null 아님 단언 (`!!`)

**null 아님 단언 연산자**(`!!`)는 어떤 값이든 non-null 타입으로 강제 변환하며, 만약 실행 시점에 해당 값이 `null`이면 `NullPointerException`이 발생한다.

**⚠️ 이 연산자는 값이 절대 `null`이 아님을 100% 확신할 때만 주의해서 사용해야 한다**

```kotlin
fun startsWith(str: String?): Boolean {
    // 'str'이 절대 null이 아니라고 단언하며, 만약 str이 실제로 null이면 여기서 NPE가 발생된다.
    return str!!.startsWith("A")
}
```
## 4. 플랫폼 타입 (Platform Types)

코틀린에서 자바 코드를 가져와 사용할 때 **플랫폼 타입**이 등장할 수 있다.   
이는 자바 코드의 타입에 `@Nullable`이나 `@NotNull` 같은 어노테이션으로 null 관련 정보가 명시되지 않았을 때 발생한다.

코틀린은 이 타입이 nullable인지 non-nullable인지 알 수 없으므로, null 검사를 강제하지 않는다.  
이 때문에 non-null처럼 다룰 수 있지만, 만약 자바에서 넘어온 실제 값이 `null`이라면 실행 시점에 예외가 발생할 수 있어 주의가 필요하다.

```java
// Java 코드 예시
public class Person {
    private final String name;
    // @Nullable이나 @NotNull 어노테이션이 없다면...
    public String getName() { return name; }
}
// 코틀린에서 사용할 때
fun main() {
    val person = Person("개발자")
    // person.name은 플랫폼 타입(String!)이 된다. 컴파일은 되지만, 실행 시점에 person.name이 null이면 에러가 발생할 수 있다.
    startsWithJava(person.name) 
}

fun startsWithJava(str: String): Boolean {
    return str.startsWith("A")
}

```
## 최종 정리
* **Null 구분**: 코틀린은 `null`이 가능한 타입(`String?`)과 불가능한 타입(`String`)을 엄격히 구분한다.
* **Safe Call (`?.`)**: `null`이 아닐 때만 안전하게 변수에 접근한다.
* **Elvis 연산자 (`?:`)**: `null`일 경우 사용할 기본값을 지정한다.
* **null 아님 단언 (`!!`)**: `null`이 아님을 강하게 단언하지만, `NPE` 위험이 있다.
* **플랫폼 타입**: 자바 코드를 사용할 때는 null 가능성을 직접 확인하고 코틀린에서 안전하게 처리해야한다.
