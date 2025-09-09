# 코틀린에서 람다(Lambda) 다루기

코틀린에서 함수는 1급 시민(First-class citizen)으로, 변수에 할당하거나 파라미터로 전달하는 등 값처럼 다룰 수 있다.

---

## 1. 코틀린에서의 람다

람다는 이름이 없는 함수를 의미한다.  
코틀린에서는 함수가 그 자체로 값이 될 수 있어, 변수에 할당하거나 다른 함수의 인자로 넘길 수 있다.

### 람다의 정의와 호출

코틀린에서 람다를 만드는 방법은 크게 두 가지가 있다..

```kotlin
data class Fruit(val name: String, val price: Int)

fun main() {
    val fruits = listOf(
        Fruit("사과", 1_000),
        Fruit("바나나", 3_000),
        Fruit("수박", 10_000)
    )

    // 방법 1: 이름 없는 함수 `fun` 키워드 사용
    val isApple: (Fruit) -> Boolean = fun(fruit: Fruit): Boolean {
        return fruit.name == "사과"
    }

    // 방법 2: 중괄호와 화살표(`->`) 사용 (더 일반적)
    val isApple2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과" }

    // -- 람다 호출 --
    // 방법 1: 변수 이름을 함수처럼 사용
    println(isApple(fruits[0]))

    // 방법 2: invoke() 메소드 사용
    println(isApple2.invoke(fruits[0]))
}
```
함수의 타입은 `(파라미터 타입, ...) -> 반환 타입` 형식으로 표기한다.

### 람다를 파라미터로 사용하기

Java에서는 `Predicate`와 같은 함수형 인터페이스를 사용해야 하지만, 코틀린에서는 함수 자체를 파라미터로 직접 전달할 수 있다.

**Java 예시**
```java
 private List<Fruit> filterFruits(List<Fruit> fruits, Predicate<Fruit> filter) {
     List<Fruit> results = new ArrayList<>();
     for(Fruit fruit : fruits) {
         if (filter.test(fruit)) {
             results.add(fruit);
         }
     }
     return results;
 }
```

**Kotlin 예시**
```kotlin
private fun filterFruits(
    fruits: List<Fruit>, 
    filter: (Fruit) -> Boolean // 함수 자체를 파라미터로 받음
): List<Fruit> {
    val results = mutableListOf<Fruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            results.add(fruit)
        }
    }
    return results
}

// -- 사용 예시 --
// 1. 람다가 할당된 변수를 전달
filterFruits(fruits, isApple)

// 2. 람다 식을 직접 전달
filterFruits(fruits, { fruit: Fruit -> fruit.name == "사과" })
```

### 람다 사용을 위한 문법 편의 기능

코틀린은 람다를 더 간결하게 사용할 수 있도록 여러 문법을 지원한다.

```kotlin
// 1. Trailing Lambda: 함수의 마지막 파라미터가 람다인 경우, 소괄호 밖으로 뺄 수 있다.
filterFruits(fruits) { fruit: Fruit -> fruit.name == "사과" }

// 2. 타입 추론: filterFruits 함수가 Fruit 타입을 받는 것을 알기 때문에, 람다의 파라미터 타입을 생략할 수 있다.
filterFruits(fruits) { fruit -> fruit.name == "사과" }

// 3. it 키워드: 람다의 파라미터가 하나일 경우, `it`으로 파라미터를 암시적으로 참조할 수 있다.
filterFruits(fruits) { it.name == "사과" }
```

---

## 2. 클로저 (Closure)

코틀린의 람다는 자신이 선언된 주변 환경(Scope)의 변수를 '포획(capture)'하여 사용할 수 있다. 이 데이터 구조를 **클로저**라고 한다.

### Java와의 차이점

Java에서는 람다 내부에서 `final`이거나 `effectively final`(선언 후 변경되지 않는 변수)인 외부 변수만 참조할 수 있다.

**Java 예시 (컴파일 에러)**
```java
String targetFruitName = "바나나";
targetFruitName = "수박"; // 변수 값이 변경됨
filterFruits(fruits, (fruit) -> targetFruitName.equals(fruit.getName()));
// 에러: Variable used in lambda expression should be final or effectively final
```

### Kotlin의 클로저

반면, 코틀린에서는 람다가 외부의 non-final 변수도 참조하고 변경할 수 있다. 람다가 생성될 때 주변 변수들을 포획하여 람다와 함께 저장하기 때문이다.

```kotlin
var targetFruitName = "바나나"
targetFruitName = "수박" // 문제 없음

// 람다가 외부 변수 targetFruitName을 '포획'하여 사용
val filtered = filterFruits(fruits) { it.name == targetFruitName } 
```
이러한 클로저의 특성 덕분에 코틀린에서 함수를 1급 시민으로 다룰 수 있다.

---

## 최종 요약

1.  함수는 Java에서 2급 시민이지만, 코틀린에서는 **1급 시민**이다. 그래서 함수 자체를 변수에 넣거나 파라미터로 전달할 수 있다.
2.  코틀린에서 함수 타입은 **`(파라미터 타입, ...) -> 반환타입`**으로 표기한다.
3.  코틀린에서 람다는 두 가지 방법(`fun` 키워드, `{}`)으로 만들 수 있고, **`{ }` 방법**이 더 많이 사용된다.
4.  함수를 호출하며 마지막 파라미터인 람다를 쓸 때는 **소괄호 밖으로 뺄 수 있다 (Trailing Lambda).**
5.  람다의 파라미터가 한 개일 경우 **`it`**을 통해 직접 참조할 수 있다.
6.  람다에서 여러 줄의 코드를 작성할 수 있으며, 별도의 `return`문 없이 **마지막 표현식(expression)의 결과가 람다의 반환 값**이 된다.
7.  코틀린에서는 **클로저(Closure)**를 사용하여 `non-final` 변수도 람다에서 자유롭게 사용할 수 있다.
