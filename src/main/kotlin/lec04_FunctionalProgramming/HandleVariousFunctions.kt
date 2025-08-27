//package org.example.lec04_FunctionalProgramming
//
//
//// 코틀린에서 다양한 함수 다루기
//// 1. 확장함수
//// 2. infix 함수 (중위 함수)
//// 3. inline 함수
//// 4. 지역 함수
//class HandleVariousFunctions {
//    // 1. 확장 함수
//    // 코틀린은 Java와 100% 호환하는 것을 목표로 개발되었다. 그렇다 보니 자연스럽게 고민이 생기는데
//    // 기존 Java 코드 위에 자연스럽게 코틀린 코드를 추가할 수 없을까 하는 고민이다.
//
//    // 이런 고민을 해결할 수 있는 방법으로 나오게 된게 어떤 클래스 안에 있는 메소드처럼 호출할 수 있지만, 함수는 밖에 만들 수 있게 하자 라는 결론으로 중위함수가 만들어졌다.
//
//    // 코틀린에서 기본적으로 제공되는 String 클래스 안에 있는 것 같은 확장함수를 만든다면????
//    // 문자열의 가장 끝에 있는 문자를 가져오는 함수를 만들어 보자
//    fun String.lastChar(): Char {
//        // 본인 자신 즉, this를 통해서 함수가 불려진 Instance를 가져온다.
//        return this[this.length - 1]
//    }
//    // 함수이기 때문에 fun으로 시작하고 확장할 대상함수.함수이름 으로 정의하면 된다.
//    // this를 통해서 실제 클래스 안의 값에 접근하는데, 이때 this를 수신객체 라고 부른다.
//    // 확장하려는클래스 즉, this의 타입은 수신객체 타입이라고 부른다.
//
//    fun main() {
//        // 사용할때는 확장함수의 맴버함수인것처럼 호출해서 사용하면 된다.
//        val str = "ABC"
//        str.lastChar()
//    }
//    // 그런데 확장함수가 public이고, 확장함수에서 수신객체클래스의 private 함수를 가져오면 캡슐화가 깨지는건 아닌가?
//    // 이런 문제점을 방지하기 위해 확장함수는 클래스에 있는 private 또는 protected 멤버를 가져올 수 없다.
//    // 그리고 멤버함수와 확장함수의 시그니처가 같은 경우라면 멤버함수가 우선적으로 호출되며, 확장함수를 만들었지만, 다른 기능의 똑같은 멤버함수가 생긴다면 오류가 발생할 수 있어서 주의가 필요하다.
//    // 확장 함수가 오버라이드 되는 경우라면 해당 변수의 현재 타입 즉, 정적인 타입에 의해 어떤 확장함수가 호출될지 결정된다.
//
//    // 확장함수 중간정리
//    // 1. 확장함수는 원본 클래스의 private, protected 멤버 접근이 안된다.
//    // 2. 멤버함수, 확장함수 중 멤버함수에 우선권이 있다.
//    // 3. 확장함수는 현재 타입을 기준으로 호출된다.
//
//    // 2. infix(중위) 함수
//    // 중위함수란 새로운 함수 종류가 아니고 함수를 호출하는 방법중에 하나다.
//    // 예를들어 코틀린에서 사용되는 downTo, step도 중위 호출 함수다.
//    // 변수.함수이름(argument) 대신 '변수' 함수이름 argument
//
//    fun Int.add(other: Int): Int {
//        return this + other
//    }
//
//    // 중위 함수로 정의할땐 infix 키워드를 붙인다.
//    infix fun Int.add2(other: Int): Int {
//        return this + other
//    }
//
//    fun main() {
//        // 일반 함수를 사용하듯 사용도 가능하지만
//        3.add(4)
//        3.add2(4)
//
//        // 중위함수 처럼도 사용이 가능해진다.
//        3 add2 4
//    }
//    // 결국 중위함수는 함수를 부르는 방법이며, infix라는 것은 멤버함수에도 붙일 수 있다.
//
//    // 3. inline 함수
//    // 함수가 호출되는 대신, 함수를 호출한 지점에 함수 본문을 그대로 복붙하고 싶은 경우에 사용한다.
//    inline fun Int.add4(other: Int): Int {
//        return this + other
//    }
//
//    fun main() {
//        3.add4(4)
//    }
//    // 위와 같은 코드를 바이트코드로 변경하면 아래와 같다.
//    //public static final void main() {
//    //    byte $this$add$i = 3;
//    //    int other$iv = 4;
//    //    int $i$f$add = false;
//    //    int var10000 = $this$add$iv + other$iv;
//    //}
//    // add4 라는 함수의 덧셈하는 로직 자체가 그 함수를 부르는 쪽에 복사 붙여넣기 된것이다.
//    // 똑같아 보이는데 사용하는 이유는 함수를 파라미터로 전달할 때에 오버헤드를 줄일 수 있기 때문이다.
//    // inline 함수의 사용은 성능 측정과 함께 신중하게 사용하는것이 권장된다.
//
//    // 4. 지역함수
//    // 지역함수는 함수 안에 함수를 선언할 수 있는 걸 지역함수라 부른다.
//    fun createPerson(firstName: String, lastName: String) : Person {
//        // 이 함수 안에는 if문이 중복되어 있다. 그래서 지역함수를 통해서 중복을 없앨 수 있다.
//        if (firstName.isEmpty()) {
//            throw IllegalArgumentException("firstName은 비어있을 수 없습니다! 현재 값 : $firstName")
//        }
//
//        if (lastName.isEmpty()) {
//            throw IllegalArgumentException("LastName은 비어있을 수 없습니다! 현재 값 : $lastName")
//        }
//        return Person(firstName, lastName, 1)
//    }
//
//    fun createPerson2(firstName: String, lastName: String): Person {
//        fun validateName(name: String, fieldName: String) {
//            // 지역함수
//            if (name.isEmpty()) throw IllegalArgumentException("${fieldName}은 비어있을 수 없습니다. 현재 값 : $name")
//        }
//        validateName(firstName, "firstname")
//        validateName(lastName, "lastName")
//
//        return Person(firstName, lastName, 1)
//    }
//    //그래서 지역함수는 함수로 추출하면 좋을 것 같은데, 이 함수를 지금 함수 내에서만 사용하고 싶을 때 사용한다.
//    // 다만, depth가 깊어지기도 하고 코드가 좀 깔끔하지 않아서 딱히 사용할 일이 있을까 싶다.?
//
//
//    // 최종 정리
//    // 1. Java 코드가 있는 상황에서, Kotlin 코드로 추가 기능 개발을 하기 위해 확장함수와 확장 프로퍼티가 등장
//    // 2. 확장함수는 원본 클래스의 private, protected 멤버 접근이 안된다.
//    // 3. 멤버함수, 확장함수 중 멤버함수에 우선권이 있다.
//    // 4. 확장함수는 현재 타입을 기준으로 호출된다.
//    // 5. Java에서는 static 함수를 쓰는것처럼 Kotlin의 확장함수를 쓸 수 있다.
//    // 6. 함수 호출 방식을 바꿔주는 infix 함수가 있다.
//    // 7. 함수를 복사-붙여넣기 하는 Inline 함수가 있다.
//    // 8. kotlin에는 함수 안에 함수를 선언할 수 있고, 이를 지역함수라고 부른다.
//}