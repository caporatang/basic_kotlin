package org.example.lec04_FunctionalProgramming

import lec04_FunctionalProgramming.helper.KotlinLambda.Fruit
import java.io.BufferedReader
import java.io.FileReader

// 코틀린에서 람다 다루기
// 1. 코틀린에서의 람다
// 2. Closure
class KotlinLambda {
    // 1. 코틀린에서의 람다
    // kotlin의 람다는 java와 근본적으로 다른 한 가지가 있다.
    // 코틀린에서는 함수가 그 자체로 값이 될 수 있다. 그리고 변수에 할당할수도 있고, 파라미터로 넘길 수도 있다.

    fun main() {
        val fruits = listOf(
            Fruit("사과", 1_000),
            Fruit("사과", 1_200),
            Fruit("사과", 1_200),
            Fruit("사과", 1_500),
            Fruit("바나나", 3_000),
            Fruit("바나나", 3_000),
            Fruit("바나나", 2_500),
            Fruit("수박", 1_0000)
        )

        // 이 과일이 사과야? 를 묻는 함수
        // 첫번째 방법 : fun 뒤에 함수 이름이 없다. 즉, 이름 없는 함수. 람다
        val isApple: (Fruit) -> Boolean = fun (fruit: Fruit): Boolean {
            return fruit.name == "사과"
        }

        // 두번째 방법
        // 중괄호와 화살표를 사용하는 익명 함수
        val isApple2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과"}

        // 공통적으로 함수에도 반환하는 타입이 존재하는데, 함수의 타입을 표기하기 위해서는
        // 소괄호 안에 파라미터 타입들을 적어주고 -> 반환 타입 으로 정의한다.

        // 이 함수는 변수에 할당되어 있는데, 변수에 할당되어 있는 함수를 부르는 방법도 두가지가 있다.
        // 첫번째 방법.
        isApple(fruits[0])

        // 두번째 방법
        // invoke를 직접적으로 호출
        isApple.invoke(fruits[0])

    }

    // java예시
    //private List<Fruit> filterFruits(List<Fruit> fruits, Predicate<Fruit> filter) {
    //    List<Fruit> results = new ArrayList<>();
    //    for(Fruit fruit : fruits) {
    //        if (filter.test(fruit)) {
    //            results.add(fruit);
    //        }
    //    }
    //    return results;
    //}

    // 위와같은 자바에서 Predicate 같은 함수 인터페이스를 사용하는 함수를 코틀린으로 바꾸면 아래와 같다.
    // 코틀린에서는 함수 자체를 파라미터로 받아서 과일을 필터링 해줄 수 있다.
    private fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) : List<Fruit> {
        val results = mutableListOf<Fruit>()
        for (fruit in fruits) {
            if (filter(fruit)) {
                results.add(fruit)
            }
        }
        return results
    }

    fun main() {
        val fruits = listOf(
            Fruit("사과", 1_000),
            Fruit("사과", 1_200),
            Fruit("사과", 1_200),
            Fruit("사과", 1_500),
            Fruit("바나나", 3_000),
            Fruit("바나나", 3_000),
            Fruit("바나나", 2_500),
            Fruit("수박", 1_0000)
        )

        // 이 과일이 사과야? 를 묻는 함수
        // 첫번째 방법 : fun 뒤에 함수 이름이 없다. 즉, 이름 없는 함수. 람다
        val isApple: (Fruit) -> Boolean = fun (fruit: Fruit): Boolean {
            return fruit.name == "사과"
        }

        // 두번째 방법
        // 중괄호와 화살표를 사용하는 익명 함수
        val isApple2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과"}

        // filterFruits같은 함수는 이와 같이 활용할 수 있다.
        // isApple도 변수지만 함수가 할당되어 있기 떄문이다.
        filterFruits(fruits, isApple)
        filterFruits(fruits, isApple2)

        // 변수에 할당한 연산 자체를 바로 파라미터로 넘길수도있다.
        filterFruits(fruits, fun (fruit: Fruit): Boolean { return fruit.name == "사과" })
        filterFruits(fruits, { fruit: Fruit -> fruit.name == "사과"})

        // 중괄호와 화살표를 활용하는 형태를 함수에 넣을 때, 함수에서 받는 함수 파라미터가 마지막에 위치해 있으면
        // 소괄호 안에 중괄호가 들어가게 되면 사용하는 쪽에서 어색할 수 있기 때문에 소괄호 밖으로 중괄호를 뺄 수 있다.
        // 바로 위와 동일한 의미를 갖는 함수이다.
        filterFruits(fruits) {fruit:Fruit -> fruit.name == "사과"}

        // 그리고 함수를 보고 들어가야 할 파라미터 타입을 추론할 수 있기 떄문에 :Fruit 도 생략 가능하다.
        filterFruits(fruits) {fruit -> fruit.name == "사과"}

        // fruit도 fruit 타입인걸 알수있고, 함수에서 받는 파라미터가 한개인 경우 it 으로 직접 참조할 수 있다.
        filterFruits(fruits) { it.name == "사과"}

        // 정리하자면 Kotlin에서는 함수가 1급 시민이다. (Java에서는 2급 시민이다.)
    }


    // 2. Closure

    // java 예시
    //String targetFruitName = "바나나";
    //targetFruitName = "수박";
    //filterFruits(fruits, (fruit) -> targetFruitName.equals(fruit.getName()));

    // java에서는 위와 같이 처음에 바나나라는 문자열을 할당한 변수에 다시 수박 이라는 문자열을 할당한 후
    // filterFruits 함수 안에서 람다를 사용하면 에러가 발생한다.
    // Variable used in lambda expression should be final or effectively final
    // Java에서는 람다를 쓸 때 사용할 수 있는 변수에 제약이 있다.

    // 반면 코틀린에서는 문제 없이 사용할 수 있다.
    // 코틀린은 람다가 시작하는 지점에 참조하고 있는 변수들을 모두 '포획'해서 그 정보를 가지고 있다.
    // 이렇게 해야만, 람다를 일급 시민으로 간주할 수 있다. 이런 데이터 구조를 Closure 라고 부른다.

    // 최종 정리
    // 1. 함수는 Java에서 2급 시민이지만, 코틀린에서는 1급 시민이다. 그래서 함수 자체를 변수에 넣을 수도 있고 파라미터로 전달할 수도 있다.
    // 2. 코틀린에서 함수 타입은 (파라미터 타입, ...) -> 반환타입이다.
    // 3. 코틀린에서 람다는 두 가지 방법으로 만들 수 있고, { } 방법이 더 많이 사용된다.
    // 4. 함수를 호출하며, 마지막 파라미터인 람다를 쓸 때는 소괄호 밖으로 뺄 수 있다.
    // 5. 파라미터가 한개인 경우 it을 통해 직접 참조할 수 있다.
    // 6. 람다를 사용할때 여러줄을 쓸수있지만, 리턴을 작성하지 않아도 마지막 expression 결과는 람다의 반환 값이다.
    // 7. 코틀린에서는 Closure를 사용하여 non-final 변수도 람다에서 사용할 수 있다.
}