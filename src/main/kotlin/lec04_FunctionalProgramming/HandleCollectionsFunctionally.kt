package org.example.lec04_FunctionalProgramming


// 컬렉션을 함수형으로 다루는 방법
// 1. 필터와 맵
// 2. 다양한 컬렉션 처리 기능
// 3. List를 Map 으로
// 4. 중첩된 컬렉션 처리
class HandleCollectionsFunctionally {




}

data class Fruit(
    val id: Long,
    val name: String,
    val factoryPrice: Long,
    val currentPrice: Long
) {
    val isSamePrice: Boolean
        get() = factoryPrice == currentPrice
}

private const val l = 3500

fun main() {
    val fruits = listOf(Fruit(1, "사과", 1_000, 2_0),
                        Fruit(2, "사과", 1_300, 2_5),
                        Fruit(3, "사과", 1_400, 3_5),
                        Fruit(4, "사과", 1_500, 4_5),
                        Fruit(5, "사과", 1_600, 5_5),
                        Fruit(6, "사과", 1_700, 6_5),
    )

    // 1. 필터와 맵
    // 이름이 사과인 애들 뽑기
    val apples = fruits.filter { fruit -> fruit.name == "사과"}

    // 뽑긴 뽑는데, 인덱스가 필요한 경우 ?
    val applesIndex = fruits.filterIndexed { index, fruit ->
        println(index)
        fruit.name == "사과"
    }

    // 사과의 가격들을 알려주세요 -> 맨 처음 filter라는 기능을 통해서 사과만 필터링을 먼저 하고 map을 사용해서 사과 인스턴스에서 가격들을 꺼내서 모아줄수도있다.
    val applePrices = fruits.filter {fruit -> fruit.name == "사과" }
        .map { fruit -> fruit.currentPrice }

    // 맵에서 인덱스가 필요하면?
    val applePricesMapIndex = fruits.filter { fruit -> fruit.name == "사과" }
        .mapIndexed {idx, fruit ->
            println(idx)
            fruit.currentPrice
        }

    // Mapping의 결과가 null이 아닌 것만 가져오고 싶다?
    val values = fruits.filter {  fruit -> fruit.name == "사과" }
        .mapNotNull { it.currentPrice }

    // 2. 다양한 컬렉션 처리

    //혹시 가격이 10000원 이상의 과일이 하나라도 있음?
    // all: 조건을 모두 만족하면 true, 그렇지 않으면 false
    val isAllApple = fruits.all { fruit -> fruit.name =="사과" }

    // none: 조건을 모두 불만족하면 true, 그렇지 않으면 false
    val isNoApple = fruits.none { fruit -> fruit.name =="사과" }

    // any: 조건을 하나라도 만족하면 true 그렇지 않으면 false
    val isAnyApple = fruits.any { fruit -> fruit.factoryPrice >= 10_000 }

    // 총 과일 갯수가 몇개임? -> java List.size()
    val fruitCount = fruits.count()

    // sortedBy (오름차순) 정렬을 한다
    val fruitSortedBy = fruits.sortedBy { fruit -> fruit.currentPrice }

    // sortedByDescending (내림차순) 정렬을 한다
    val fruitSortedByDescending = fruits.sortedByDescending { fruit -> fruit.currentPrice }

    // distinctBy: 변형된 값을 기준으로 중복을 제거
    val distinctByFruitNames = fruits.distinctBy { fruit -> fruit.name }
        .map {fruit -> fruit.name }

    // 첫번째 과일만 주세요. 마지막 과일만 주쇼
    // first: 첫번째 값을 가져온다 (무조건 null이 아니여야한다.)
    // firstOrNull: 첫번째 값 또는 Null을 가져온다
    val firstFruit = fruits.first()
    val lastFruit = fruits.firstOrNull()

    // last: 마지막 값을 가져온다 (무조건 not Null)
    // lastOrNull: 첫번째 값 또는 null을 가져온다
    fruits.last()
    fruits.lastOrNull()

    // 3. List를 Map으로

    // 과일 이름 -> List<과일> Map이 필요함!
    val map: Map<String, List<Fruit>> = fruits.groupBy { fruit -> fruit.name }

    // id가 key, 과일이 value인 맵이 필요함!
    // associateBy를 사용하면 value쪽에 단일 객체가 들어가게 된다. 즉, 중복되지 않는 키를 가지고 map을 만들떄 사용함
    val map2: Map<Long, Fruit> = fruits.associateBy { fruit -> fruit.id }

    // key, value를 동시에 처리하기
    val map3: Map<String, List<Long>> = fruits.groupBy({fruit -> fruit.name}, {fruit -> fruit.factoryPrice})

    // Map 에 대해서도 앞에서 봤던 다양한 컬렉션 처리 방법들을 사용할 수 있다.

    // 4. 중첩된 컬렉션 처리
    val fruitsInList: List<List<Fruit>> = listOf(
        listOf(Fruit(1L, "사과", 1_000, 2_0),
                Fruit(2L, "사과", 1_300, 2_5),
                Fruit(3L, "사과", 1_400, 3_5),
                Fruit(4L, "사과", 1_500, 4_5),
                Fruit(5L, "사과", 1_600, 5_5),
                Fruit(6L, "사과", 1_700, 6_5)
        ),
        listOf(
            Fruit(7L, "바나나", 3000, 3500),
            Fruit(8L, "바나나", 3000, 3500),
            Fruit(9L, "바나나", 3000, 3500),
        ),
        listOf(
            Fruit(10L, "수박", 9800, 9900),
        )
    )

    // 출고가와 현재가가 동일한 과일을 골라줘
    // flatMap은 자바와 동일하다. flatMap을 사용하면 단일리스트로 바뀌게 된다.(평탄화?)
    val samePriceFruits = fruitsInList.flatMap {
        list -> list.filter { fruit -> fruit.factoryPrice == fruit.currentPrice}
    }

    // 위와 같은 경우 람다가 중첩되어 있어서 가독성이 떨어질 수 있다. Fruit 클래스에 함수로 가격을 비교하는 함수를 만들고
    // samePriceFilter 확장 함수를 만들어서 하나의 람다를 사용하는 것처럼 사용도 가능하다.
    val samePriceFruits2 = fruitsInList.flatMap { list -> list.samePriceFilter }

    // 위와 같은 방법도 있지만 만약 써야한다면, List<List<Fruit>를 List<Fruit>로 그냥 바꾸는게 더 좋은 방법이 될 수 있다.
    // flatten 함수를 사용하면 평탄화 작업이 돼서 중첩되지 않은 객체로 바꿀수있다.
    fruitsInList.flatten()
}

val List<Fruit>.samePriceFilter: List<Fruit>
    get() = this.filter(Fruit::isSamePrice)

private fun filterFruits (
    fruits: List<Fruit>, filter: (Fruit) -> Boolean
): List<Fruit> {
    return fruits.filter(filter)
}