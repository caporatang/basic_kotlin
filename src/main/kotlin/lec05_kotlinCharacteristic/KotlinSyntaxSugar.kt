package org.example.lec05_kotlinCharacteristic


import org.example.lec04_FunctionalProgramming.Fruit
import org.example.lec05_kotlinCharacteristic.KotlinSyntaxSugar.UltraSuperGuradianTribe

typealias FruitFilter = (Fruit) -> Boolean
typealias USGTMap = Map<String, UltraSuperGuradianTribe>

// 1. Type Alias와 as import
// 2. 구조분해와 componentN 함수
// 3. Jump와 Label
// 4. TakeIf와 TakeUnless
class KotlinSyntaxSugar {
    // 1.Type Alias와 as import
    // 긴 이름의 클래스 혹은 함수 타입이 있을때 축약하거나 더 좋은 이름을 쓰고 싶은 경우에 사용할 수 있다.
    fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) {
        // 이런 함수가 있을떄 Boolean을 반환하는 부분이 너무 길고, 만약 여기서 파라미터가 더 많아지는 경우가 있을 수 있다.
    }

    // typealias 키워드를 사용해서 아래와 같이 사용할 수 있다. import와 같은 위치에 위치시켜야한다.
    // typealias FruitFilter = (Fruit) -> Boolean
    // fun filterFruits(fruits: List<Fruit>, filter: FruitFilter) { }

    // 이름이 긴 클래스를 컬렉션에 사용할 때도 줄여서 사용할 수 있다.
    data class UltraSuperGuradianTribe(
        val name: String
    )
    // typealias USGTMap = Map<String, UltraSuperGuradianTribe>

    // as import
    // 어떤 클래스나 함수를 임포트 할 때 이름을 바꾸는 기능이다.
    // 다른 패키지의 같은 이름 함수를 동시에 가져오고 싶을때 사용한다.
    // A 라는 패키지에 "A"를 출력하는 함수, B라는 패키지에 "B" 라는 함수를 출력하는 함수가 있다고 가정해본다.
    // 이때 함수명은 동일하다. 내용만 다를뿐.

    // import com.study.abc.a.printHelloWorld as printHelloWorld as printHelloWorldA
    // import com.study.abc.b.printHelloWorld as printHelloWorld as printHelloWorldB

    // import와 동시에 이름을 바꿔서 헷갈리지 않게 코딩할수있는 기능이다.


    // 2. 구조분해와 componentN 함수
    // 구조분해란 복합적인 값을 분해하여 여러 변수를 한 번에 초기화하는 것을 뜻한다.
    fun main() {
        val person = Person("김태일", 100)
        //val (name, age) = person
        // 분해된 값 확인
        // println("이름: $name, 나이: $age") // 출력: 이름: 김태일, 나이: 100

        // 구조분해의 동작원리
        // Data Class는 componentN이란 함수도 자동으로 만들어준다.
        // val (name, age) = person 이 한줄은 아래와 같은 동작을 동일하게 수행해준다.
        val name = person.component1()
        val age = person.component2()

        // 즉, component1...2...3.. 이 의미하는 바는 데이터 클래스가 기본적으로 자기가 가지고 있는 filed에 대해서 component 함수를 만들어준다.
        // property를 만들어준다는 의미이다.
        // 결론적으로 구조분해 문법을 쓴다는 것은 componentN 함수를 호출한다는 뜻이다.

        // data Class가 아닌데 구조분해를 사용하고 싶다면, componentN 함수를 직접 구현해서 사용하면 된다.

        class Person2(
            val name: String,
            val age: Int
        ) {
            operator fun component1(): String {
                return this.name
            }

            operator fun component2(): Int {
                return this.age
            }
        }

        // 직접 정의해서 사용하는 경우 주의해야할 점은 compoenentN 함수는 연산자의 속성을 가지고 있기 떄문에 연산자 오버로딩을 하는 것처럼 간주해야 한다.
        // 그래서 앞에 operator라는 키워드를 붙여 사용해야 한다.

        // 3. Jump와 Label
        // 보통 코드의 '흐름'을 제어할 때는 세가지 키워드를 사용한다. return / break / continue
        // 1. return: 기본적으로 가장 가까운 enclosing function 또는 익명 함수로 값이 반환된다.
        // 2. break: 가장 가까운 루프가 제거된다.
        // 3. continue: 가장 가까운 루프를 다음 step으로 보낸다.

        // 코틀린의 for문 while문에서 break문은 java와 동일하게 동작한다. 다만, foreach 구문에서는 continue, break는 사용할 수 없다.
        // 그래서 kotlin에서는 forEach문과 함께 break 또는 continue를 꼭 쓰고 싶다면 break는 run이라는 블록으로 한번 감싸주어야 한다.
        fun main() {
            val numbers = listOf(1,2,3,4,5)
            run {
                numbers.forEach { number ->
                    if (number == 3) {
                        return@run
                    }
                    println(number)
                }
            }

            // continue는 @forEach를 사용해주어야한다.
            val number2 = listOf(1,2,3,4,5)
            run {
                numbers.forEach { number2 ->
                    if (number2 == 3) {
                        return@forEach
                    }
                    println(number2)
                }
            }
            // 근데 사실, forEach 쓰면서 break, continue를 사용할 일이 있을까 싶다. 차라리 그냥 for문 쓰지

            // 라벨
            // 코틀린에는 라벨이라는 기능이 있다.
            // 라벨이란 특정 expression에 라벨이름@ 을 붙여 하나의 라벨로 간주하고 break, continue, return등을 사용하는 기능이다.

            // 아래처럼 abc@를 정의하고 if문 안에서 @abc를 붙이게 되면
            // break가 가장 가까운 for문이 멈추는게 아니라 abc@ 를 가장 밖에 for문에 붙였기 때문에, 특정 라벨이 break가 걸린다고 생각해야 한다. -> 가장 밖에 for문이 break 걸린다.
            abc@ for(i in 1..100) {
                // 이 for문이 멈춘다.
                for (j in 1..100) {
                    // 이 for문이 멈추는게 아니라
                    if (j ==2) {
                        break@abc
                    }
                    print("$i $j")
                }
            }

        // 하지만 라벨을 사용한 Jump는 사용하지 않는게 좋다.
        // 코드에 흐름이 위아래로 움직임이 많아질수록 복잡도가 크게 증가되고 유지보수가 힘들어지기 때문이다.

        // 4. TakeIf TakeUnless
        val number: Int = numbers[0]
        fun getNumberOrNull(): Int? {
            return if (number <= 0) {
                null
            } else {
                number
            }
        }

        // 코틀린에서는 method chaning을 위한 특이한 함수들을 제공한다.
        // getNumberOrNull 이라는 함수를 TakeIf를 사용하면 아래와 같이 만들수있다. 주어진 조건을 만족하면 그 값이, 그렇지 않으면 null이 반환된다.
        fun getNumberOrNullV2(): Int? {
            return number.takeIf { it > 0 }
        }

        // takeUnless는 주어진 조건을 만족하지 않으면 그 값이, 그렇지 않으면 null이 반환된다.
        fun getNumberOrNullV3(): Int? {
            return number.takeUnless { it <= 0 }
        }

        } // main end
    }
} // class end



class Person2(
    val name: String,
    val age: Int
) {
    fun component1(): String {
        return this.name
    }

    fun component2(): Int {
        return this.age
    }
}



data class Person(
    val name: String,
    val age: Int
) {

}

// 최종 정리
// 1. 타입에 대한 별칭을 줄 수 있는 typealias 라는 키워드가 있다.
// 2. import 당시 이름을 바꿀 수 있는 as import 기능이 있다.
// 3. 변수를 한 번에 선언할 수 있는 구조분해 기능이 있으며 componentN 함수를 사용한다.
// 4. for문, while문과 달리 forEach에는 break와 continue를 사용할 수 없다.
// 5. takeIf와 takeUnless를 활용해 코드양을 줄이고 method chaning을 활용할 수 있다


