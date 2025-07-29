package org.example.lec01_variable_type

import lec01_variable_type.CommonPerson

/*
* 코틀린에서 Type을 다루는 방법
* 1. 기본 타입
* 2. 타입 캐스팅
* 3. Kotlin의 3가지 특이한 타입
* 4. 코틀린에서 String Interpolation, String indexing
*
* java는 기본 타입간의 변환은 암시적으로 이루어질 수 있고, Kotlin에서 기본 ㅌ ㅏ입간의 변환은 명시적으로 이루어져야 한다.
*
*  int number1 = 4;
* long number2 = number1;
* System.out.println(number1 + number2);
* Java에서는 위와 같이 int보다 long 타입이 더 크니까 암시적으로 변경되어 계산이 된다.
*
* 반대로 코틀린은 명시적으로 타입을 맞춰줘야한다.
* val number1 = 4
* val number2: Long = number1 // Type mismatch error
* println(number1 + number2)
* */

fun main() {
    val number1 = 3
    val number2: Long = number1.toLong() // 타입을 맞춰서 연산해줘야함
    println(number1 + number2)

    // 만약 null이 할당될수도있다면?
    val number3: Int? = 3
    val number4: Long = number3?.toLong() ?: 0L

}


// 기본 타입이 아닌 일반 타입이라면?
/*
* public static void printAgeIfPerson(Object obj) {
*   if (obj instanceof CommonPerson) {
*       CommonPerson person = (CommonPerson) obj;
*       System.out.println(person.getAge());
*   }
* }
* 위와 같은 Java 코드를 예시로 코틀린으로 변경한다면,
* */
fun printAgeIfPerson(obj: Any) {
    // is 는 instanceOf , (클래스) 대신 as를 사용한다.
    if (obj is CommonPerson) {
        // 변환하는 과정이 없어도 코틀린은 인식한다. 이것을 스마트 캐스트라고 한다.
        // val person = obj as CommonPerson
        //println(person.name)

        // 스마트 캐스트~
        println(obj.name)
    }
}

// 그렇다면 instanceOf의 반대는 ?
fun printAgeIfNotPerson(obj: Any) {
    if (obj !is CommonPerson) {
        println(obj)
    }
}

// obj에 null이 들어올수있다면?
fun printAgeIfPersonNull(obj: Any?) {
    // as? 는 만약 obj가 null이 아니라면 Person 타입으로 변화시키고, null이라면 전체가 null이 된다.
    val person = obj as? CommonPerson
    println(person?.name)
}
// 결론적으로 value is Type 은 value가 Type이면 true, type이 아니면 false를 반환하는것.
// value !is 는 value가 Type이면 false, value가 Type이 아니면 true
// value as Type은 value가 Type이면 Type으로 캐스팅, value가 Type이 아니면 예외가 발생한다.
// value as? Type은 value가 Type이면 Type으로 캐스팅, value가 null이면 null , value가 Type이 아니면 null 이다.


// 코틀린에만 존재하는 특이한 타입
// 1. Any
// 1-1) Java의 Object 역할 ( 모든 객체의 최상위 타입)
// 1-2) 모든 Primitive Type의 최상 타입도 Any
// 1-3) Any 자체로는 null을 포함할 수 없어 null을 포함하고 싶다면, Any?로 표현
// 1-4) Any에 equals / hashCode  / toString 존재

// 2. Unit
// 2-1) Unit은 Java의 void와 동일한 역할을한다.
// 2-2) void와 다르게 Unit은 그 자체로 타입 인자로 사용이 가능하다 --> ???
// 2-3) 함수형 프로그래밍에서 Unit은 단 하나의 인스턴스만 갖는 타입을 의미한다 즉, 코틀린의 Unit은 실제 존재하는 타입이라는 것을 표현한다.

// 3. Nothing
// 3-1) Nothing은 함수가 정상적으로 끝나지 않았다는 사실을 표현하는 역할을한다.
// 3-2) 무조건 예외를 반환하는 함수 / 무한 루프 함수 등등..

// 4. String interpolation / String indexing
val commonPerson = CommonPerson("기무태이루")
val log = "사람의 이름은 ${commonPerson.name} 입니다."

// 중괄호를 생략하고 변수만으로 사용도 가능하지만 중괄호를 포함하는게 가독성, 일괄적 변환, 정규식 활용 등에서 더 나을수있다.
val test = "테스트"
val stirngTest = "테스트 : $test"

// 문자열 만들기, Java는 보통 StringBuilder를 만들어서 append 한다. 코틀린에서는 따옴표로 가능하다.
val str =   """
    ABC
    EFG
    ${test}
""".trimIndent()

// 문자열의 특정 문자 가져오기, java에서는 charAt함수를 사용해서 가져오지만 코틀린은 배열처럼 특정 문자열을 가져올수있다.
val charAtt = "ABCDE"
val ch = charAtt[1]


/*
* 정리
* 1. 코틀린의 변수는 초기값을 보고 타입을 추론하며, 기본 타입들 간의 변환은 명시적으로 이루어진다.
* 2. 코틀린에서는 is !is as as? 를 이용해 타입을 확인하고 캐스팅한다.
* 3. 코틀린의 Any는 Java의 Object와 같은 최상위 타입이다.
* 4. 코틀린의 Unit은 Java의 void와 동일하다.
* 5. 코틀린에 있는 Nothing은 정상적으로 끝나지 않는 함수의 반환을 의미
* 6. 문자열을 가공할때 ${변수} 와 """ """ 을 사용하면 깔끔하게 문자열을 만들수있다.
* 7. 문자열에서 문자를 가져올때의 Java의 배열처럼 []를 사용한다.
*
* */