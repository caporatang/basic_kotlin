# 코틀린의 배열과 컬렉션 정리

코틀린에서 데이터를 그룹으로 관리하는 배열과 다양한 컬렉션(List, Set, Map)의 사용법을 Java와 비교하여 알아보자.

---

## 1. 배열 (Array)

배열은 실무에서 리스트보다 사용 빈도가 낮지만, 기본적인 사용법을 알아두는 것이 좋겠다.

### Java 예시

```java
public class JavaArray {
    public static void main(String[] args) {
        int[] array = {100, 200};
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%s %s%n", i, array[i]);
        }
    }
}
```

### Kotlin 예시

코틀린 배열은 `arrayOf()`를 통해 생성하며, 수많은 확장 함수와 유용한 반복문 문법을 제공한다.

```kotlin
fun main() {
    val array = arrayOf(100, 200)

    // 1. 인덱스를 이용한 반복
    for (i in array.indices) {
        println("$i ${array[i]}")
    }

    // 2. 인덱스와 값을 함께 이용한 반복
    for ((idx, value) in array.withIndex()) {
        println("$idx $value")
    }
    
    // 3. 배열에 요소 추가 (새로운 배열이 반환됨)
    val newArray = array.plus(300)
}
```

---

## 2. 컬렉션 (Collection)

코틀린의 컬렉션은 **불변(Immutable)** 과 **가변(Mutable)** 으로 명확하게 구분된다.

### 컬렉션 계층 구조

코틀린 컬렉션의 최상위에는 `Iterable` 인터페이스가 있다.  
그 아래에 `Collection` 인터페이스가 있고, `List`와 `Set`이 `Collection`을 상속받는다. `Map`은 `Collection` 계층에 속하지 않는 별도의 인터페이스다.    
각 인터페이스는 읽기 전용(불변)이며, 요소를 변경할 수 있는 가변 인터페이스(`MutableIterable`, `MutableCollection`, `MutableList` 등)가 이를 상속받는 구조로 되어 있다.

-   **가변(Mutable) 컬렉션**: 요소의 추가, 삭제가 가능하다. (`mutableListOf`, `mutableSetOf`, `mutableMapOf`)
-   **불변(Immutable) 컬렉션**: 생성 시점 이후에 요소의 추가, 삭제가 불가능하다. (`listOf`, `setOf`, `mapOf`)

> **규칙**: 우선 불변으로 만들고, 꼭 필요한 경우에만 가변 컬렉션으로 변경하는 것이 좋다!

### 2-1) 리스트 (List)

순서가 있는 데이터의 집합이며, 중복된 요소를 허용.

**Java 예시**
```java
// Java 8
List<Integer> numbers = Arrays.asList(100, 200);
```

**Kotlin 예시**
```kotlin
// 불변 리스트
val numbers = listOf(100, 200)

// 가변 리스트 (기본 구현체: ArrayList)
val mutableNumbers = mutableListOf(100, 200)
mutableNumbers.add(300)

// 요소 접근
println(numbers[0]) // .get(0) 대신 배열처럼 접근 가능

// 반복문
for (number in numbers) {
    println(number)
}

for ((idx, number) in numbers.withIndex()) {
    println("$idx $number")
}
```

### 2-2) 집합 (Set)

순서가 없고, 중복된 요소를 허용하지 않는 데이터의 집합.

**Kotlin 예시**
```kotlin
// 불변 Set
val numberSet = setOf(100, 200)

// 가변 Set (기본 구현체: LinkedHashSet)
val mutableSet = mutableSetOf(100, 200)
mutableSet.add(300)

// 반복문 (List와 동일)
for (number in numberSet) {
    println(number)
}
```

### 2-3) 맵 (Map)

Key-Value 쌍으로 이루어진 데이터의 집합.

**Java 예시**
```java
// Java 8 이전
Map<Integer, String> map = new HashMap<>();
map.put(1, "Monday");
map.put(2, "Tuesday");

// Java 9 이후
Map<Integer, String> mapOf = Map.of(1, "Monday", 2, "Tuesday");
```

**Kotlin 예시**
```kotlin
// 가변 Map
val mutableMap = mutableMapOf<Int, String>()
mutableMap[1] = "Monday" // .put() 대신 배열처럼 키에 직접 할당
mutableMap[2] = "Tuesday"

// 불변 Map (to 중위 호출 사용)
val dayMap = mapOf(1 to "Monday", 2 to "Tuesday")

// 반복문
for (key in mutableMap.keys) {
    println(key)
    println(mutableMap[key])
}

for ((key, value) in mutableMap.entries) {
    println("$key -> $value")
}
```

---

## 3. 컬렉션의 Null 가능성

`?` 연산자의 위치에 따라 컬렉션과 그 요소의 Null 허용 여부가 결정된다.

-   `List<Int?>`: 리스트 자체는 `null`일 수 없지만, 리스트의 **요소는 `null`일 수 있다.**
    -   `listOf(1, null, 3)` (O)
-   `List<Int>?`: 리스트의 요소는 `null`일 수 없지만, **리스트 자체가 `null`일 수 있다.**
    -   `val list: List<Int>? = null` (O)
-   `List<Int?>?`: **리스트 자체도, 그 요소도 `null`일 수 있다.**
    -   `val list: List<Int?>? = listOf(1, null)` (O)
    -   `val list2: List<Int?>? = null` (O)
