package org.example.lec01_variable_type


/*
* java example
*
* long number1 = 10L;
* final long number2 = 10L;
*
* Long number3 = 1_000L;
* Person person = new Person("김태일");
*
* */
fun main() {
    // 코틀린은 가변 변수에는 var 키워드를 사용하고, 불변에는 val 키워드를 사용한다.
    var number1 = 10L
    val number2 = 10L

    // 초기값을 지정하지 않는 경우 ? (타입을 지정해줘야함)
    var number100: Long
    // 불변이여도 초기값을 할당하지 않았기 떄문에 따로 한번 값을 할당할수는 있다.
    val number101: Long

    /*
    * long number9 = 10L;
    * Long number10 = 1_000L;
    * 자바에서는 primitive type long, reference type Long은 구분되어 사용되지만 코틀린은 구분하지 않는다.
    * 자바에서는 연산할때 reference type은 사용하지 않는게 좋다. 왜냐하면 boxing unboxing이 일어나면서 불필요한 객체가 생성되기 때문이다.
    *
    * 코틀린은 내부적으로 연산같은 동작을 수행할때 primitive type으로 변경해서 연산이 진행된다.
    * 아래 코드를 자바코드로 바꿔보면 (디컴파일) 내부적으로 long으로 변경되어 사용된다
    *
    * 즉, 코틀린은 개발자가 boxing unboxing을 신경쓰지 않아도 자동으로 해결해준다
    */
    var number9 = 10L
    var number10: Long = 1_000L

    /*
    * 추가적으로 Java에서 Long이 갖는 의미는 null 이 할당될수도 있다, reference type이기 때문이다
    * 코틀린에서는 'null이 할당될수도 있다' 를 타입 뒤에 물음표(?) 를 통해서 지정해주어야한다.
    * */
    var number22: Long? = 1_000L
    number22 = null

    /*
    * 객체 인스턴스화의 차이
    * Person person = new Person("김태일");
    * */
    var person = Person("김태일")


    /*
    * 코틀린에서 변수 다루기
    * 모든 변수는 var / val 키워드를 사용한다. 가변(var), 불변(val)
    * 타입을 명시적으로 작성하지 않아도, 타입 추론이 된다.
    * Primitive Type, Reference Type을 구분하지 않아도 된다.
    * Null이 들어갈 수 있는 변수는 타입 뒤에 ? 를 붙여주어야 한다. -> 아예 다른 타입으로 간주된다.
    * 객체를 인스턴스화 할 때 new를 사용하지 않는다.
    * */
}

class Person(s: String) {
    /* do nothing.. */
}
