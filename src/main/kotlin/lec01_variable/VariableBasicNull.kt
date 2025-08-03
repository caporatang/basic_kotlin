package org.example.lec01_variable_type


/*
* 코틀린에서 null 다루기
* 1. null 체크
* 2. Safe Call 과 Elvis 연산자
* 3. null이 아님을 단언!
* 4. 플랫폼 타입?
*
* Java에서 아래와 같은 코드는 안전한 코드가 아니다. Null에 대한 방어로직이 필요하다.
* public boolean startsWithA1(String str) {
*   return str.startsWith("A");
* }
*
* 아래와 같은 방어 로직이 필요하다 또는,
* public Boolean startsWithA2(String str) {
*   if(str == null) {
*       return null;
*   }
*   return str.startsWith("A");
* }
*
* 이런식이 될수도 있다.
* public boolean startsWithA3(String str) {
*   if(str == null) {
*       return false;
*   }
*   return str.startsWith("A");
* }
*
*
* 이 3가지 케이스를 코틀린으로 어떻게 변환해서 작성할수있는지 살펴보자.
* */


// Kotlin에서는 null이 가능한 타입을 완전히 다르게 취급한다.
// null이 들어갈수있는 ? 를 붙이냐 안붙이냐에 따라 null체크를 해야할수도 안해도 될수도 있다. 자동으로 컴파일러가 체크를 하기 떄문이다.
// ? 가 안붙어있는 경우 바로 String startsWith 함수를 호출할 수 있지만, ? 붙어있으면 null 체크 하라고 에러가 발생한다.
fun startsWithA1(str: String?): Boolean {
    if (str == null) {
        throw IllegalArgumentException("null값이 들어왔어요.")
    }
    return str.startsWith("A")
}

fun startsWithA2(str: String?): Boolean? {
    if (str == null) {
        return null
    }
    return str.startsWith("A")
}

fun startsWithA3(str: String?): Boolean {
    if (str == null) {
        return false
    }
    return str.startsWith("A")
}

fun main() {
    //null이 가능한 타입만을 위한 기능은 없는가? 있다. 그게 safecall 이며,
    // null이 아니면 실행하고, null이면 실행하지 않는다. (그대로 null로 처리한다.)
    val str: String? = "피곤해"
    //str.length -> null이 할당될수도있는데, 바로 length를 쓰려고 하니 에러 발생
    str?.length // 바로 사용 가능

    // 추가적으로 safe call과 같이 Elvis 연산자를 사용할 수 있다.
    val str2: String? ="왕피곤해"
    // java에서 삼항 연산자와 비슷하다., 앞에 str2가 null이면 뒤의 값을 사용한다. 즉, 지금은 null이 아닌 "왕피곤해" 라는 문자열이 할당되어 있으니 출력해보면 결과는 4가 나온다.
    str2?.length ?: 0
    println(str2?.length ?: 0);

}

// 그래서 위에서 작성한 startsWithA1-A3 까지의 코틀린 코드를 더 코틀린스럽게 바꿔보면
fun startsWithA1Ko(str: String?): Boolean {
    // elvis 연산자를 사용
    return str?.startsWith("A")
        ?: throw IllegalArgumentException("null 들어왔음")
}

fun startsWithA2Ko(str: String?): Boolean? {
    // safe call은 변수가 null이면 결과가 그대로 null이 되기 때문에 그대로 반환한다.
    return str?.startsWith("A")
}

fun startsWithA3Ko(str: String?): Boolean {
    return str?.startsWith("A") ?: false
}

// 추가적으로 Elvis 연산은 early return 에도 사용할수있다.
/*
* public long calculate(Long number) {
*   if (number == null) {
*       return 0;
*   }
*   /* do something...*/
* }
* */
// 위와 같은 Java 코드를 많이 사용하는데, 이걸 코틀린 엘비스 연산자로 대체해서 표현하면 아래와 같이 표현할수있다.
// fun calculate(number: Long?): Long {
//     number ?: return 0
// }

// 다음으로 null 아님을 단언하는것.
// nullable type이지만, 아무리 생각해도 null이 될 수 없는 경우에 사용하는 방법이다.
fun startsWith(str: String?): Boolean {
    // null일수도 있는 필드가 들어오지만, 어떤 경우에도 null일수가 없다.
    // 단, 혹시 이렇게 사용했는데 null이 들어오면 NPE가 발생한다. 주의가 필요함
    return str!!.startsWith("A")
}

// 다음으로 플랫폼 타입이다.
// Kotlin에서 Java 코드를 가져다 사용할 때 어떻게 처리해야할까에 대한 방법이다.
// 아래와 같은 자바 클래스가 있을떄,
/*
public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
*/
fun main2() {
    val person = Person("공부하는 개발자")
    // 아래 startsWithJava에 위에서 생성한 name을 넣으면 에러가 생긴다.
    // 이유는 @Nullable 어노테이션 때문인데, 저 어노테이션 자체를 @NotNull로 변경하게 되면 에러가 사라진다 즉, 코틀린은 자바코드의 어노테이션 내용을 이해한다.
    // startsWithJava(person.name)

    // 그런데 문제는, Nullable 혹은 NotNull 둘 다 없는 경우라면 코틀린에서는 해당 변수가 nullable 인지 non-nullable인지 알 수가 없다.
    // 즉, 코틀린이 null 관련 정보를 알 수 없는 타입을 플랫폼 타입이라하며 Runtime시 Exception이 발생할 수 있다. -> 많은 주의가 필요하다.

}
fun startsWithJava(str: String): Boolean {
    return str.startsWith("A")
}

// 정리
// 1. 코틀린에서 null이 들억갈 수 있는 타입은 완전히 다르게 간주된다. 한번 null 검사를 하면 non-null임을 컴파일러가 알 수 있다.
// 2. null이 아닌 경우에만 호출되는 Safe Call (?.) 이 있다.
// 3. null인 경우에만 호출되는 Elvis 연산자 (?: 가 있다.)
// 4. null이 절대 아닐때 사용할 수 있는 null 아님 단언 (!!) 이 있다.
// 5. Kotlin 에서 Java 코드를 사용할 때 플랫폼 타입 사용에 유의해야 하며, Java 코드를 읽으며 null 가능성을 확인하고 가능하다면 Kotlin으로 wrapping 하는게 좋다.




