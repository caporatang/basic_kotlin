package org.example.lec03_oop


// 코틀린에서 접근 제어 다루기
// 1. 자바와 코틀린의 가시성 제어
// 2. 코틀린 파일의 접근 제어
// 3. 다양한 구성요소의 접근 제어
class KotlinAccessControl {
    // 1. Java와 코틀린의 가시성 제어
    // java는
    // 1. public : 모든 곳에서 접근 가능
    // 2. protected : 같은 패키지 또는 하위 클래스에서만 접근 가능
    // 3. default : 같은 패키지에서만 접근 가능
    // 4. private : 선언된 클래스 내에서만 접근 가능

    // kotlin은
    // 1. public : 모든 곳에서 접근 가능
    // 2. protected : 선언된 클래스 또는 하위 클래스에서만 접근 가능
    //      -> 자바와 다른 이유는 kotlin에서는 패키지를 namespace를 관리하기 위한 용도로만 사용되고 가시성 제어로는 사용되지 않기 떄문
    // 3. default는 internal로 대체되어, 같은 모듈에서만 접근 가능하다.
    //      -> 모듈은 한 번에 컴파일 되는 Kotlin 코드 기준이다
    // 4. private : 선언된 클래스 내에서만 접근 가능
    // Java의 기본 접근 지시어는 default, Kotlin의 기본 접근 지시어는 public 이다.

    // 2. 코틀린 파일의 접근 제어
    // 코틀린은 .kt 파일에 변수, 함수, 클래스 여러개를 바로 만들 수 있다.
    // .kt에서 사용할 수 있는 접근 지시어는
    // 1. public : 기본값으로 어디서든 접근할 수 있다.
    // 2. protected : 파일(최상단)에는 사용할 수 없다.
    // 3. internal : 같은 모듈에서만 접근 가능하다.
    // 4. private : 같은 파일 내에서만 접근할 수 있다.

    // 3. 다양한 구성요소의 접근 제어
    // 클래스 안의 멤버의 접근 지시어
    // 1. public : 모든 곳에서 접근 가능
    // 2. 선언된 클래스 또는 하위 클래스에서만 접근 가능
    // 3. internal : 같은 모듈에서만 접근 가능
    // 4. private : 선언된 클래스 내에서만 접근 가능

    // 생성자
    // 생성자도 멤버의 접근 지시어와 동일하다. 단, 생성자에 접근 지시어를 붙이려면 constructor를 사용해야 사용할 수 있다.
    class Mem internal constructor() { }

    // 프로퍼티의 가시성 범위는 동일한데, 방법은 두가지가 있다.
    // 1. getter, setter 한 번에 접근 지시어를 정하거나 val 혹은 var 앞에 internal, public 등을 붙이는 방법이 있고
    // customGetter나 customSetter에 사용하는 방법이 있다.
    class Car (
        val name: String,
        var owner: String,
       _price: Int
    ){
        var price = _price
            private set
    }

    // 최종 정리
    // 1. Kotlin에서 패키지는 namespace 관리용이기 때문에 protected는 의미가 달라졌다.
    // 2. Kotlin에서는 default가 사라지고, 모듈간의 접근을 통제하는 internal이 새로 생겼다.
    // 3. 생성자에 접근 지시어를 붙일 때는 constructor를 명시적으로 써주어야 한다.
    // 4. 유틸성 함수를 만들 때는 파일 최상단을 이용하면 편리하다.
    // 5. 프로퍼티의 custom setter에 접근 지시어를 붙일 수 있다.
}