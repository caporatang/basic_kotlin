package org.example.lec04_FP


// 코틀린에서 배열과 컬렉션을 다루는 방법
// 1. 배열
// 2. 코틀린에서의 Collection - List, Set, Map
class ArrayCollection {

    // 1. 배열
    // 사실 실무하면서 배열...은 쓸일이 잘 없다. Effective Java에서도 배열보다 리스트를 사용하라고 강조하기도 한다.
    // 그래도 사용법은 알아야지.

    // java예시
    //public class JavaArray {
    //    public static void main(String[] args) {
    //        int[] array = {100, 200};
    //        for (int i = 0; i <array.length; i++) {
    //            System.out.printf("%s %s", i, array[i]);
    //        }
    //    }
    //}

    fun main() {
        val array = arrayOf(100, 200)
        // index range를 이용해서 출력
        for (i in array.indices) {
            println("$i ${array[i]}")
        }

        // index와 value 한번에 받기
        for ((idx, value) in array.withIndex()) {
            println("$idx $value")
        }
        // java에서는 배열에 새로운 값을 추가하려면 배열을 copy해서 여러가지 작업을 해야하지만 코틀린에서는 plus 메서드를 제공하기 떄문에 쉽게 가능함
        array.plus(10000)

        // 편리한 메서드가 여러가지 많이 제공된다.
    }

    // 2. collection
    // 코틀린에서 변수 선언할때 var와 val로 불변인지 가변인지 설정하는것처럼 컬렉션을 만들때도 동일하게 var와 val을 사용 해주어야 한다.

    // 코틀린 컬렉션 계층 구도에는 Iterable이 가장 최상위에 위치하며 그 아래로 Collection 그리고 그 아래로 List, Set, Map 이 있고, 그 아래로 MutableList, MutableSet, MutableMap이 있다.
    // 즉, 불변 컬렉션과 가변 컬렉션이 나뉘어져 있다.

    // 가변(Mutable) 컬렉션 : 컬렉션에 element를 추가, 삭제할 수 있다.
    // 불변 컬렉션 : 컬렉션에 element를 추가, 삭제할 수 없다.
    // 불변 컬렉션이라 하더라도 Reference Type인 Element의 필드는 바꿀 수 있다.

    // 2-1) Collection - List
    // java에서 리스트 만들기
    // final List<Integer> numbers = Arrays.asList(100, 200);

    // kotlin
    val numbers = listOf(100, 200) // 불변 리스트

    // 리스트 요소 가져오기, 반복문

    // 하나 가져오기
    //System.out.println(numbers.get(0));
    //
    //// For Each
    //for (int number : numbers ) {
    //    System.out.println(number);
    //}
    //
    //// 전통적인 for문
    //for (int i = 0; i < numbers.size(); i++) {
    //    System.out.printf("%s %s", i, numbers.get(i));
    //}

    fun main {
        // 가변 리스트로 만들고싶다면, mutableList를 사용하면 된다.
        val mutableNumbers = mutableListOf(100, 200)
        // mutableList는 기본 구현체가 ArrayList라서 자바에서 제공하는 라이브러리 기능들을 동일하게 사용할 수 있다..
        mutableNumbers.add(300)

        val numbers = listOf(100, 200)
        println(numbers[0])

        for (number in numbers) {
            println(number)
        }

        for ((idx, number) in numbers.withIndex()) {
            println("$idx $number")
        }

        // 사용할때는 우선 불변 리스트를 만들고, 꼭 필요한 경우 가변 리스트롤 바꾸면 좋다!
    }

    // 2-2) Collection - Set
    // 집합은 List와 다르게 순서가 없고, 같은 element는 하나만 존재할 수 있다.
    // 자료구조적 의미만 제외하면 모든 기능이 List와 비슷하다고 할 수 있다.
    fun main  {
        // 가변 set을 만들고 싶다면 mutableSetOf를 사용한다.
        // 기본 구현체는 LinkedHashSet이다.
        val mutableSet = mutableSetOf(100, 200)

        // 불변 set
        val numbers = setOf(100, 200)

        for (number in numbers) {
            println(number)
        }

        for((index, number) in numbers.withIndex()) {
            println("$index $number")
        }
    }

    // 2-3) Collection - Map

    // Java에서 map 만들기
    // JDK8까지
    //Map<Integer, String> map = new HashMap<>();
    //map.put(1, "asd");
    //map.put(2, "qwe");
    //
    // JDK9 부터
    //Map.of(1, "asd", 2, "qwe");

    // kotlin에서 map 만들기
    fun main {
        // java와 동일하게도 만들수있다.
        val oldMap = mutableMapOf<Int, String>()
        oldMap.put(1, "Monday")

        // put대신 키에 직접 접근해서 값을 넣을 수 있다. 이게 더 권장되는 방법임.
        oldMap[2] = "TuesDay"

        // 이런식으로 to 키워드를 사용해서 가독성 좋게 불변 Map을 만들수도있다.
        // 아래와 같은 방식은 중위 호출인데, Pair 클래스를 내부적으로 만들어서 받는 함수인것이다.
        mapOf(1 to "Monday", 2 to "TuesDay")

        // Map 활용
        // Java 예시
        //for (key: Int in map.keys) {
        //    println(key)
        //    println(map.get(key))
        //}
        //
        //for (entry: kotlin.collections.MutableMap.MutableEntry<Int?, kotlin.String?>? in map.entries) {
        //    println(entry.key)
        //    println(entry.value)
        //}

        for (key in oldMap.keys) {
            println(key)
            println(oldMap[key])
        }

        for ((key, value) in oldMap.entries) {
            println(key)
            println(value)
        }
    }

    // 3. 컬렉션의 null 가능성
    // kotlin 물음표 연산자의 위치에 따라 null 가능성 의미가 달라진다.
    // List<Int?> : 리스트에 null이 들어갈 수 있지만, 리스트는 절대 null이 아님
    // List<Int>? : 리스트에는 null이 들어갈 수 없지만, 리스트는 null일 수 있음
    // List<Int?>? : 리스트에 null이 들어갈 수도 있고, 리스트가 null일 수도 있음



}