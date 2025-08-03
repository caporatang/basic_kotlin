# 코틀린에서 타입을 다루는 방법

자바와 코틀린은 타입을 다루는 방식에 몇 가지 중요한 차이점이 있다.  
타입 변환, 캐스팅, 특수 타입, 그리고 문자열 처리 방법에 대해 알아보자.

---

## 1. 기본 타입과 명시적 변환

자바는 크기가 더 큰 타입으로 변환될 때 **암시적(Implicit) 타입 변환**을 허용한다.

```java
// Java: int -> long 암시적 변환
int number1 = 4;
long number2 = number1; // 문제 없이 long 타입으로 변환됨
System.out.println(number1 + number2);
```

코틀린은 타입 불일치로 인한 버그를 막기 위해 명시적(Explicit) 타입 변환을 원칙으로 한다.  
서로 다른 타입의 연산을 위해서는 to타입() 메소드를 사용해야 한다..

```kotlin
// Kotlin: 명시적 변환 필요
val number1 = 3
// val number2: Long = number1 // Type mismatch 에러 발생

val number2: Long = number1.toLong() // toLong()으로 명시적 변환
println(number1 + number2)

// 변환하려는 변수가 null일 가능성이 있다면 Safe Call과 Elvis 연산자를 활용.
val number3: Int? = 3
val number4: Long = number3?.toLong() ?: 0L
```

## 2. 타입 캐스팅

코틀린은 `is`, `!is`, `as`, `as?` 연산자를 사용해 타입을 확인하고 변환한다.


### 스마트 캐스트 (Smart Cast)

`is` 연산자로 타입을 확인하면, 해당 블록 내에서는 컴파일러가 자동으로 타입을 변환해준다. 이를 **스마트 캐스트**라고 한다.

```java
// Java: instanceof로 확인 후, 수동으로 캐스팅
public static void printAgeIfPerson(Object obj) {
  if (obj instanceof CommonPerson) {
      CommonPerson person = (CommonPerson) obj;
      System.out.println(person.getAge());
  }
}
```
```kotlin
// Kotlin: 'is'로 확인하면 자동으로 캐스팅됨
fun printAgeIfPerson(obj: Any) {
    if (obj is CommonPerson) {
    // val person = obj as CommonPerson // 'as'를 통한 캐스팅이 필요 없음
    println(obj.name) // 컴파일러가 obj를 CommonPerson으로 인식
    }
}

// !is는 is의 반대 경우를 확인한다.
fun printAgeIfNotPerson(obj: Any) {
    if (obj !is CommonPerson) {
        println(obj)
    }
}
```
### 안전한 타입 변환 (Safe Cast)
as? 연산자는 타입 변환이 실패할 경우 예외를 발생시키는 대신 null을 반환합니다. null이 될 수 있는 객체를 다룰 때 유용하다.

```kotlin
fun printAgeIfPersonNull(obj: Any?) {
    // obj가 Person 타입이면 변환, 아니거나 null이면 null을 반환
    val person = obj as? CommonPerson
    println(person?.name) // Safe Call과 함께 사용
}
```

### 캐스팅 연산자 요약
* `value is Type`: `value`가 `Type`이면 `true`, 아니면 `false`
* `value !is Type`: `value`가 `Type`이면 `false`, 아니면 `true`
* `value as Type`: `value`를 `Type`으로 캐스팅하며, 타입이 다르면 예외(`ClassCastException`)가 발생한다.
* `value as? Type`: `value`가 `Type`이면 캐스팅하며, `null`이거나 타입이 다르면 `null`을 반환한다..

## 3. 코틀린의 3가지 특이한 타입

코틀린에는 다른 언어에서 보기 힘든 몇 가지 특별한 타입이 있다.

### Any

* Java의 **`Object`**와 같은 모든 클래스의 최상위 타입.
* `Int`, `Long`과 같은 원시 타입(Primitive Type)의 최상위 타입이기도 하다.
* **`Any`** 자체는 `null`을 포함할 수 없으며, `null`을 포함하려면 **`Any?`**로 선언해야 한다.
* 모든 객체와 마찬가지로 `equals()`, `hashCode()`, `toString()` 메소드를 포함한다.

### Unit

* Java의 **`void`**와 동일한 역할을 하며, 함수의 반환 값이 없음을 의미한다.
* **`void`**와 달리 **`Unit`**은 그 자체로 타입 인자로 사용 가능한 실제 타입이다. 이는 함수형 프로그래밍에서 '단 하나의 인스턴스만 갖는 타입'이라는 개념과 연결된다.

### Nothing

* 함수가 **정상적으로 끝나지 않았음**을 명시적으로 표현하는 타입이다.
* 주로 무조건 예외를 반환하거나(`throw Exception()`), 무한 루프에 빠지는 함수 등의 반환 타입으로 사용되어 코드의 의도를 명확하게 한다.

## 문자열 다루기 (String Handling)
### String Interpolation (문자열 템플릿) 
- $ 기호와 중괄호{}를 사용해 문자열 안에 변수나 표현식을 깔끔하게 넣을 수 있다.
```kotlin
val commonPerson = CommonPerson("기무태이루")
// ${...}를 사용해 객체의 프로퍼티에 접근
val log = "사람의 이름은 ${commonPerson.name} 입니다."

val test = "테스트"
// 변수 하나만 사용할 때는 중괄호 생략 가능
val stringTest = "테스트 : $test"
```

### Multiline Strings (여러 줄 문자열)
- 큰따옴표 세 개(""")를 사용하면 개행 문자를 포함한 문자열을 쉽게 만들 수 있습니다. trimIndent()를 사용하면 앞쪽의 공통된 들여쓰기를 제거해준다.

```kotlin
val test = "치환"
val str =   """
    ABC
    EFG
    ${test}
""".trimIndent()
// 결과:
// ABC 
// EFG
// 치환
```

### String Indexing (문자열 인덱싱)
- Java의 charAt() 메소드 대신 배열처럼 대괄호([])를 사용하여 특정 위치의 문자에 접근할 수 있다.
```kotlin
val charAtt = "ABCDE"
val ch = charAtt[1] // 결과: 'B'
```

## 최종 정리

* **명시적 타입 변환**: 코틀린의 기본 타입 간 변환은 **`.to타입()`**처럼 명시적으로 이루어져야 한다.
* **타입 확인 및 캐스팅**: **`is`**, **`!is`**, **`as`**, **`as?`**를 사용하며, **스마트 캐스트**를 지원한다.
* **Any**: Java의 `Object`와 같은 코틀린의 최상위 타입이다.
* **Unit**: Java의 `void`와 같은 역할을 하는 실제 타입이다.
* **Nothing**: 함수가 정상적으로 끝나지 않음을 의미하는 특수 타입이다.
* **문자열 처리**: **`${변수}`**와 **`""" """`**를 사용하면 문자열을 간결하고 가독성 좋게 만들 수 있다.
* **문자열 인덱싱**: Java의 `charAt(i)` 대신 배열처럼 **`[i]`**로 문자열의 특정 문자에 접근할 수 있다.