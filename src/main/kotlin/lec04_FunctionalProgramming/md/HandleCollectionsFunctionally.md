# 컬렉션을 함수형으로 다루는 방법

코틀린은 다양한 컬렉션 API를 제공하여 `for` 루프 없이도 데이터를 선언적으로 처리할 수 있게 해준다.  
다양한 함수를 활용하여 컬렉션을 효율적으로 다루는 방법을 알아보자.

### 예제용 데이터 클래스

```kotlin
data class Fruit(
    val id: Long,
    val name: String,
    val factoryPrice: Long,
    val currentPrice: Long
) {
    // 출고가와 현재가가 동일한지 확인하는 프로퍼티
    val isSamePrice: Boolean
        get() = factoryPrice == currentPrice
}
```

---

## 1. 필터와 맵

컬렉션에서 원하는 데이터만 뽑아내거나(필터링), 특정 형태로 변환(매핑)하는 가장 기본적인 기능이다.

-   **`filter`**: 주어진 조건을 만족하는 element만 남긴다.
-   **`filterIndexed`**: 인덱스 정보와 함께 필터링한다.
-   **`map`**: 각 element를 주어진 람다를 적용한 결과로 변환한다.
-   **`mapIndexed`**: 인덱스 정보와 함께 매핑한다.
-   **`mapNotNull`**: 매핑 결과가 `null`이 아닌 값들만 모아서 리스트로 만든다.

```kotlin
val fruits = listOf(
    Fruit(1, "사과", 1_000, 2_000),
    Fruit(2, "사과", 1_300, 2_500),
    Fruit(3, "바나나", 3_000, 4_000)
)

// "사과"라는 이름을 가진 과일만 필터링
val apples = fruits.filter { it.name == "사과" }

// 인덱스와 함께 필터링
val applesIndex = fruits.filterIndexed { index, fruit ->
    println(index) // 0, 1, 2
    fruit.name == "사과"
}

// 사과의 현재 가격들만 모으기
val applePrices = fruits.filter { it.name == "사과" }
    .map { it.currentPrice } // 결과: [2000, 2500]

// 인덱스와 함께 가격 매핑
val applePricesMapIndex = fruits.filter { it.name == "사과" }
    .mapIndexed { idx, fruit ->
        println(idx) // 0, 1
        fruit.currentPrice
    }

// 매핑 결과가 null이 아닌 것만 가져오기
val values = fruits.mapNotNull { it.currentPrice }
```

---

## 2. 다양한 컬렉션 처리 기능

### 조건 확인

-   **`all`**: 모든 element가 조건을 만족하면 `true`를 반환한다.
-   **`none`**: 모든 element가 조건을 만족하지 않으면 `true`를 반환한다.
-   **`any`**: 하나라도 조건을 만족하는 element가 있으면 `true`를 반환한다.

```kotlin
// 모든 과일이 사과인가? -> false
val isAllApple = fruits.all { it.name == "사과" }

// 사과가 하나도 없는가? -> false
val isNoApple = fruits.none { it.name == "사과" }

// 공장가가 10,000원 이상인 과일이 하나라도 있는가? -> false
val isAnyApple = fruits.any { it.factoryPrice >= 10_000 }
```

### 기타 유용한 기능

-   **`count`**: 컬렉션의 element 개수를 반환한다. (`size`와 동일)
-   **`sortedBy` / `sortedByDescending`**: 특정 값을 기준으로 오름차순/내림차순 정렬한다.
-   **`distinctBy`**: 특정 값을 기준으로 중복을 제거한다.
-   **`first` / `firstOrNull`**: 첫 번째 element를 반환한다. (리스트가 비어있으면 `first`는 예외 발생, `firstOrNull`은 `null` 반환)
-   **`last` / `lastOrNull`**: 마지막 element를 반환한다. (리스트가 비어있으면 `last`는 예외 발생, `lastOrNull`은 `null` 반환)

```kotlin
// 과일의 총 개수
val fruitCount = fruits.count() // 3

// 현재가 기준 오름차순 정렬
val sortedFruits = fruits.sortedBy { it.currentPrice }

// 이름 기준 중복 제거 후 이름만 매핑
val distinctNames = fruits.distinctBy { it.name }.map { it.name } // ["사과", "바나나"]

// 첫 번째 과일 가져오기
val firstFruit = fruits.first()
val firstFruitOrNull = fruits.firstOrNull()
```

---

## 3. List를 Map으로 변환하기

-   **`groupBy`**: 주어진 기준에 따라 element들을 그룹화하여 `Map<K, List<V>>`를 만든다.
-   **`associateBy`**: 각 element에서 고유한 키(key)를 추출하여 `Map<K, V>`를 만든다. 중복된 키가 있으면 마지막 element가 값을 덮어쓴다.

```kotlin
// 과일 이름을 Key로, 과일 리스트를 Value로 하는 Map 생성
val mapByName: Map<String, List<Fruit>> = fruits.groupBy { it.name }

// 과일 ID를 Key로, 과일 객체를 Value로 하는 Map 생성
val mapById: Map<Long, Fruit> = fruits.associateBy { it.id }

// Key와 Value를 직접 지정하여 그룹화
// 과일 이름을 Key로, 공장가 리스트를 Value로 하는 Map 생성
val priceMap: Map<String, List<Long>> = fruits.groupBy(
    { it.name }, 
    { it.factoryPrice }
)
```

---

## 4. 중첩된 컬렉션 처리

-   **`flatMap`**: 중첩된 컬렉션의 각 element에 대해 주어진 변환을 적용한 후, 모든 결과를 하나의 리스트로 평탄화(flatten)한다.
-   **`flatten`**: `List<List<T>>`와 같은 중첩된 컬렉션을 `List<T>`로 평탄화한다.

```kotlin
val fruitsInList: List<List<Fruit>> = listOf(
    listOf(Fruit(1L, "사과", 1000, 1000), Fruit(2L, "사과", 1200, 1300)),
    listOf(Fruit(3L, "바나나", 3000, 3000)),
    listOf(Fruit(4L, "수박", 10000, 11000))
)

// flatMap을 사용하여 출고가와 현재가가 동일한 과일만 하나의 리스트로 추출
val samePriceFruits = fruitsInList.flatMap { list ->
    list.filter { it.isSamePrice }
}

// flatten을 사용하여 단순히 중첩 리스트를 단일 리스트로 변환
val allFruits: List<Fruit> = fruitsInList.flatten()
