package org.example.lec03_oop


// 코틀린에서 다양한 클래스를 다루는 방법
// 1. Data
// 2. Enum
// 3. Sealed Class , Sealed Interface
class VariousClass {

    // 1. Data 클래스
    // Data 클래스는 Java로 치면 계층간의 데이터를 전달하기 위한 DTO(Data Transfer Object)다.

    //public class JavaPersonDto {
    //    private final String name;
    //    private final int age;
    //
    //    public JavaPersonDto(String name, int age) {
    //        this.name = name;
    //        this.age = age;
    //    }
    //
    //    public String getName() {
    //        return name;
    //    }
    //
    //    public int getAge() {
    //        return age;
    //    }
    //
    //    @Override
    //    public int hashCode() {
    //        return super.hashCode();
    //    }
    //
    //    @Override
    //    public boolean equals(Object obj) {
    //        return super.equals(obj);
    //    }
    //
    //    @Override
    //    public String toString() {
    //        return super.toString();
    //    }

    // lombok을 사용하면 보통 더 짧아지긴하지만, 클래스가 장황해지거나 클래스 생성 이후 추가적인 처리를 해주어야 하는 단점이 존재한다.

    // 코틀린으로 변환하면 아래와 같이 되는데 data 키워드를 붙여주면 equals, hashCode, toString을 자동으로 만들어준다.
    data class PersonDto(
        val name: String,
        val age: Int
    )

    // 추가적으로 Java에서도 JDK16부터 record class를 도입하여 제공하고 있다.

    // 2. Enum Class
    // Enum 클래스의 특징은 추가적인 클래스를 상속받을 수 없고 인터페이스는 구현할 수 있으며, 각 코드가 싱글톤이다.
    // Java 예시
    //public enum JavaCountry {
    //
    //    KOREA("KO"),
    //    AMERICA("US");
    //    private final String code;
    //
    //    JavaCountry(String code) {this.code = code;}
    //
    //    public String getCode() {return code;}
    //}

    // 코틀린 예시
    enum class Country(
        private val code: String
    ){
        KOREA("KO"),
        AMERICA("US")
    }
    // 자바 예시와 코틀린의 예시는 별로 다른게 없어보인다.
    // 하지만 코틀린에서는 when 혹은 Sealed Class와 함께 사용하면 더욱 편리하게 사용할 수 있다.

    // Java에서는 특정 enum에 따라 값을 비교해서 처리할려면 if-else 를 해야하고 else에 대한 처리가 애매해진다.
    // 코드가 많아지고 관리할게 많아진다면 상대적으로 가독성이 떨어질수도있다.
    //private static void handleCountry(JavaCountry country) {
    //    if (country == JavaCountry.KOREA) { /* do something .. */ }
    //    if (country == JavaCountry.AMERICA) { /* do something .. */ }
    //}

    fun handleCountry(country: Country) {
        when (country) {
            // 코틀린은 when을 enum 타입으로 받는 경우 컴파일 타임에 enum에 어떤 값들이 어떤 코드가 있는지 다 알고있기 때문에 추가적으로 else를 작성해주지 않아도 괜찮다.
            // 추가로 enum에 값을 추가하고 이런 when안에 추가하지 않는 경우 인텔리제이가 warning이나 설정에 따라 error를 띄워주기도 한다.
            Country.KOREA -> TODO()
            Country.AMERICA -> TODO()
        }
    }

    // 정리하자면, 컴파일러가 country의 모든 타입을 알고 있어 다른 타입에 대한 로직(else)을 return 하는 경우에도 작성하지 않아도 된다.
    // Enum에 변화가 있으면 IDE단에서 warning이나 error를 반환해준다.

    // 3. sealed class , interface
    // sealed 클래스는 상속이 가능하도록 추상클래스를 만들까 하는데, 외부에서는 이 클래스를 상속받지 않았으면 할때 사용한다.

    // sealed class의 특징은 컴파일 타임 때 하위 클래스의 타입ㅈ을 모두 기억한다. 즉, 런타임때 클래스 타입이 추가될 수 없다. 그리고 하위 클래스는 같은 패키지에 있어야 한다.
    // Enum과 다른점은 클래스를 상속받을 수 있으며, 하위 클래스는 멀티 인스턴스가 가능하다.

    // kotlin
    sealed class HyundaiCar(
        val name: String,
        val price: Long
    )

    class Avante : HyundaiCar("아반떼", 2000L)
    class Sonata : HyundaiCar("소나타", 3000L)
    class Grandeur : HyundaiCar("제네시스", 400L)

    // 구현은 위와같은 형식으로 하는데, 이렇게 보면 abstract 클래스와 다를게 없어 보인다.
    // 똑같이 함수 같은 걸 추상 메서드로 선언한다면 아래 하위 클래스에서 모두 구현을 해주어야하며
    // 크게 다른 점은 컴파일 타임 때 하위 클래스의 타입을 모두 기억한다. 즉, 런타임때 클래스 타입이 추가될 수 없다는 점이다.

    private fun handleCar(car: HyundaiCar) {
        when (car) {
            is Avante -> TODO()
            is Grandeur -> TODO()
            is Sonata -> TODO()
        }
    }

    // sealed 클래스를 when에 조건으로 인스턴스로 넣게되면 is 키워드를 통해서 분기처리 할수있다.
    // enum과 같이 편리하게 사용할 수 있다.

    // 추상화가 필요한 Entity나 DTO에는 Sealed Class를 활용하면 효율적으로 사용할 수 있다.
    //  JDK17에서도 Sealed Class가 추가되었다.

    // 최종 정리
    // 1. Kotlin의 Data class를 사용하면 equals, hashCode, toString을 자동으로 만들어준다.
    // 2. Kotlin의 Enum Class는 Java의 Enum Class와 동일하지만, when과 함께 사용함으로써 큰 장점을 갖는다.
    // 3. Enum Class 보다 유연하지만, 하위 클래스를 제한하는 Sealed Class 역시 When과 함께 주로 사용된다.


}