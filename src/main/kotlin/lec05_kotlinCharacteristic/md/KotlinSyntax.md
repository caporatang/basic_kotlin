# 코틀린의 여러가지 문법

---

## 1. Type Alias와 as import

### Type Alias (타입 별칭)

`typealias`는 긴 이름의 클래스나 복잡한 함수 타입을 짧고 의미 있는 이름으로 축약하여 사용할 수 있게 해주는 기능이다.

**사용법**
`import` 문과 같은 위치(파일의 최상단)에 `typealias 새이름 = 원래타입` 형식으로 선언한다.

**예시 1: 함수 타입 별칭**
```kotlin
// (Fruit) -> Boolean 이라는 함수 타입을 FruitFilter 라는 이름으로 정의
typealias FruitFilter = (Fruit) -> Boolean

// 기존 코드
fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) { /*...*/ }

// typealias 적용 후
fun filterFruitsWithTypeAlias(fruits: List<Fruit>, filter: FruitFilter) { /*...*/ }
```

**예시 2: 복잡한 제네릭 타입 별칭**
```kotlin
data class UltraSuperGuardianTribe(val name: String)

// Map<String, UltraSuperGuardianTribe> 라는 긴 타입을 USGTMap 으로 정의
typealias USGTMap = Map<String, UltraSuperGuardianTribe>

fun processMap(map: USGTMap) { /*...*/ }
```

### as import

`as import`는 다른 패키지에 있는 이름이 같은 클래스나 함수를 동시에 가져올 때, 충돌을 피하기 위해 임포트 시점에 이름을 바꿔주는 기능이다.

```kotlin
// 서로 다른 패키지에 printHelloWorld 라는 이름의 함수가 있을 경우
import com.study.abc.a.printHelloWorld as printHelloWorldA
import com.study.abc.b.printHelloWorld as printHelloWorldB

fun main() {
    printHelloWorldA() // "A" 출력
    printHelloWorldB() // "B" 출력
}
```

---

## 2. 구조 분해 (Destructuring)와 componentN 함수

**구조 분해**는 `data class`와 같은 복합적인 객체의 값을 분해하여 여러 변수에 한 번에 초기화하는 기능이다.

```kotlin
data class Person(val name: String, val age: Int)

fun main() {
    val person = Person("김태일", 100)

    // 구조 분해를 통해 person 객체의 name과 age를 각각 변수에 할당
    val (name, age) = person

    println("이름: $name, 나이: $age") // 출력: 이름: 김태일, 나이: 100
}
```

### 동작 원리: `componentN` 함수

구조 분해는 내부적으로 `componentN`이라는 함수를 호출하는 방식으로 동작한다. `data class`는 프로퍼티 순서에 따라 `component1()`, `component2()`, ... 함수를 자동으로 생성한다.

```kotlin
// val (name, age) = person 코드는 아래와 동일하게 동작합니다.
val name = person.component1()
val age = person.component2()
```

`data class`가 아니더라도 `operator` 키워드와 함께 `componentN` 함수를 직접 구현하면 구조 분해를 사용할 수 있다.

```kotlin
class Person2(
    val name: String,
    val age: Int
) {
    // 연산자(operator)로 component1 함수를 직접 구현
    operator fun component1(): String {
        return this.name
    }

    operator fun component2(): Int {
        return this.age
    }
}
```

---

## 3. Jump와 Label

### Jump (return, break, continue)

-   **`return`**: 가장 가까운 `fun` 키워드를 가진 함수나 익명 함수를 빠져나간다.
-   **`break`**: 가장 가까운 반복문을 중단한다.
-   **`continue`**: 가장 가까운 반복문의 다음 step으로 넘어간다.

코틀린의 `forEach`에서는 `break`와 `continue`를 직접 사용할 수 없다. 사용하려면 라벨과 함께 `return`을 활용해야 한다.

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

// forEach에서 break 처럼 사용하기 (run 블록 활용)
println("--- break 예시 ---")
run {
    numbers.forEach { number ->
        if (number == 3) {
            return@run // run 블록을 빠져나감
        }
        println(number)
    }
}

// forEach에서 continue 처럼 사용하기
println("--- continue 예시 ---")
numbers.forEach { number ->
    if (number == 3) {
        return@forEach // 현재 람다(forEach)의 step만 종료하고 다음으로 넘어감
    }
    println(number)
}
```
> **참고**: `forEach`에서 `break`/`continue`를 쓰는 것보다 일반 `for`문을 사용하는 것이 더 직관적이다.

### Label

라벨은 특정 표현식(주로 반복문)에 이름을 붙여 `break`, `continue`, `return`의 대상을 명시적으로 지정하는 기능이다.

```kotlin
// abc 라는 라벨을 바깥 for문에 붙임
abc@ for (i in 1..3) {
    for (j in 1..3) {
        if (j == 2) {
            break@abc // abc 라벨이 붙은 바깥 for문을 중단시킴
        }
        println("i: $i, j: $j")
    }
}
```
> **주의**: 라벨을 사용한 Jump는 코드의 흐름을 복잡하게 만들어 가독성과 유지보수성을 해칠 수 있으므로 사용을 지양하는 것이 좋겠다..~

---

## 4. takeIf와 takeUnless

메서드 체이닝을 용이하게 하고, 특정 조건에 따라 객체 자신 또는 `null`을 반환하는 유용한 함수다.

-   **`takeIf`**: 주어진 람다(조건)를 만족하면 객체 자신을, 그렇지 않으면 `null`을 반환한다.
-   **`takeUnless`**: 주어진 람다(조건)를 만족하지 않으면 객체 자신을, 그렇지 않으면 `null`을 반환한다.

```kotlin
fun getNumberOrNull(number: Int): Int? {
    return if (number <= 0) {
        null
    } else {
        number
    }
}

// takeIf 활용
fun getNumberOrNullV2(number: Int): Int? {
    // 숫자가 0보다 크면 그 숫자(number)를, 아니면 null을 반환
    return number.takeIf { it > 0 }
}

// takeUnless 활용
fun getNumberOrNullV3(number: Int): Int? {
    // 숫자가 0 이하가 아니면 그 숫자(number)를, 아니면 null을 반환
    return number.takeUnless { it <= 0 }
}
```

---

## 최종 요약

1.  **`typealias`** 키워드로 복잡한 타입에 별칭을 붙여 가독성을 높일 수 있다.
2.  **`as import`** 기능으로 이름이 같은 클래스나 함수를 충돌 없이 가져올 수 있다.
3.  **구조 분해**는 `componentN` 함수를 이용하여 객체의 프로퍼티를 여러 변수에 한 번에 할당하는 기능이다.
4.  `for`, `while`문과 달리 `forEach`에서는 `break`와 `continue`를 직접 사용할 수 없고, 라벨과 함께 `return`을 사용해야 한다.
5.  **`takeIf`**와 **`takeUnless`**를 활용해 조건에 따른 값 반환 로직을 간결하게 만들고 메서드 체이닝을 활용할 수 있다.
