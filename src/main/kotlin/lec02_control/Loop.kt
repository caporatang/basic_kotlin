package org.example.lec02_control


// 코틀린에서 반복문 다루기
// 1. for-each (향상된 for문)
// 2. 전통적인 for문
// 3. Progression과 Range
// 4. while문
class Loop {
    fun main() {
        // 1. for-each문
        // java 예시
        //List<Long> numbers = Arrays.asList(1L, 2L, 3L);
        //for (long number : numbers)  {
        //    System.out.println(number);
        //}
        // 위와 같은 java 코드를 kotlin으로 변환하면
        val numbers = listOf(1L, 2L, 3L)
        for(number in numbers) {
            println(number)
        }
        // 사용하는 방법은 비슷하나 Java에서는 : 을 쓰고 코틀린에서는 in을 사용한다.
        // Java와 동일하게 Iterable이 구현된 타입이라면 모두 들어갈 수 있다.


        // 2. 전통적인 for문
        // java 예시
        //for (int i = 1; i<=3; i++) System.out.println(i);
        // kotlin 예시
        for (i in 1..3) {
            println(i)
        }

        // 반대로 숫자를 내려가면서 출력한다면?
        // Java예시
        //for (int i = 3; i >=1; i--) {
        //    System.out.println(i);
        //}

        // kotlin에서는 downTo를 사용하면 훨씬 깔끔하게 사용할 수 있다. -> downTo는 Iterable이 구현되어 있다.
        for (i in 3 downTo 1) {
            println(i)
        }

        // 추가적으로 2칸씩 올라가는 경우라면, Java에서는?
        //for (int i =1; i<=5; i+=2){
        //    System.out.println(i);
        //}
        // kotlin은 step을 사용해서 원하는 수만큼 올릴수있다.
        for (i in 1..5 step 2) {
            println(i)
        }

        // 3. Progression과 Range
        // 그래서 kotlin에서는 이게 왜 이렇게 동작하느냐
        // .. 연산자는 범위를 만들어내는 연산자이다, 1..3 은 1부터 3의 범위를 나타낸다
        // 여기서 범위는 kotlin 클래스 중 IntRange라는 실제 클래스가 있다. IntRange 클래스는 IntProgression 클래스를 상속받고 있는데, 이는 한국어로 바꾸면 등차수열이다.

        // 바꿔말하면 .. 연산자로 범위를 만든다는 것은 1부터 3까지의 Progression을 만드는것이다.
        // .. 연산자를 클릭해서 들어가보면 알수있다.
        // public operator fun rangeTo(other: Int): IntRange
        // public class IntRange(start: Int, endInclusive: Int) : IntProgression(start, endInclusive, 1), ClosedRange<Int>, OpenEndRange<Int> {

        // 결국 등차수열은 시작 값, 끝 값, 공차 세가지 값이 있어야 만들 수 있다.
        // 그리고 dwonTo, step 도 함수인데, 중위 함수로 호출하는 방식을 다르게 해서 호출하는 방법일 뿐이다.
        // 결론적으로 코틀린은 일반적인 for문을 사용하면 등차수열을 이용한다.

        // while문
        // while문과 do-while문은 java와 동일하게 동작한다.

        // 최종 정리
        // 1. for each문에서Java는 : Kotlin은 in을 사용한다.
        // 2. 전통적인 for문에서 Kotlin은 등차수열과 in을 사용한다.
        // 3. 그 외 for문 문법은 모두 동일하다.
        // 4. while문과 do while문은 java와 동일하게 동작한다.

    } // main
}