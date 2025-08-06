package org.example.lec03_oop

// 코틀린에서 클래스 다루기
// 1. 클래스와 프로퍼티
// 2. 생성자와 init
// 3. 커스텀 getter, setter
// 4. backing field
class KotlinClass {
    fun main() {
        // 1. 코틀린에서 클래스 다루기
        // java 예시
        //public class JavaPerson {
        //    private final String name;
        //    private int age;
        //}
        // 위와 같은 코드는 name을 초기화할 수 없어 에러가 발생한다.
        // 생성자와 getter, setter가 필요하다.

        //public JavaPerson(String name, int age) {
        //    this.name = name;
        //    this.age = age;
        //}

        //public String getName() {
        //    return name;
        //}

        //public void setAge(int age) {
        //    this.age = age;
        //}

        //public int getAge() {
        //    return age;
        //}

        // name은 setter가 존재할 수 없다 위와 같은 클래스를 kotlin으로 변환하면

        // 코틀린은 기본이 public 이다.
        class Person constructor(name: String, age: Int){
            val name: String = name
            var age: Int = age
        }
        // 코틀린은 생성자를 클래스 옆에 선언하고 사용한다.
        // 프로퍼티란 필드 + getter + setter 를 의미하는데 코틀린에서는 필드만 만들면 getter, setter를 자동으로 만들어준다.
        // 즉, 먼저 작성한 JavaPerson 클래스와 동일하게 클래스를 만든것이다.

        // constructor 지시어는 생략도 가능하고, java와 다르게 클래스 body 괄호가 아니라 생성자에서 프로퍼티를 만들 수 있기 때문에 생성자 안에서 바로 선언도 가능하며,
        // 특별한 내용이 없다면 중괄호 또한 작성하지 않아도 된다.
        class Person2 (val name: String, var age: Int)

        // 코틀린은 getter, setter 또한 자동으로 호출해준다. 바로 접근이 가능하다.
        val person = Person2("김태일", 100)
        println(person.name)
        person.age = 10

        // 2. 생성자와 init
        // 클래스가 생성되는 시점에 나이 검증하기
        //public JavaPerson(String name, int age) {
        //    if (age <= 0) {
        //        throw new IllegalArgumentException(String.format("나이는 %s일 수 없습니다."));
        //    }
        //    this.name = name;
        //    this.age = age;
        //}
        // java에서는 생성자에서 값을 검증해볼수있다.

        // 코틀린에서는 어떻게 해야할까?
        class Person3 (val name: String, var age: Int) {
            // 코틀린에서는 init이라는 키워드를 사용하는데, init은 클래스가 초기화되는 시점에 한 번 호출되는 블록이다.
            // 값을 적절히 만들어주거나 validation 로직을 넣거나 하는 용도로 사용된다.
            init {
                if (age <= 0) {
                    throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
                }
            }
            // 생성자를 하나 더 추가해야 하는 상황이라면?
            //public JavaPerson(String name) {
            //    this(name, 1);
            //}

            // 코틀린은 this를 사용해서 Person3의 생성자를 호출 해주는 것이라고 생각하면 된다.
            constructor(name: String): this(name, 1);
        }
        Person3("기무태이루")

        // 추가적으로 클래스를 가장 처음 만들었을 때 함께 만들었던 생성자를 '주생성자' primary constructor 라고 하며, 당연하게도 반드시 존재해야한다.
        // 단, 주생성자에 파라미터가 하나도 없다면 생략 가능하다.
         class Person4 { }

        // 추가로 constructor를 사용해서 만드는 생성자를 부생성자라고 하는데, 부생성자는 최종적으로 주생성자를 this로 호출해야한다.
        // 하지만, 코틀린에서는 부생성자보다는 default parameter를 권장한다.
        // A 클래스를 B 클래스로 컨버팅 해야하는 경우에는 부생성자를 사용할 수 있지만, 컨버팅 보다는 정적 팩토리 메소드를 사용하는게 효과적이다.

        // 3. 커스텀 getter, setter
        // java예시
        //public boolean isAudult() {
        //    return this.age >= 20;
        //}

        // kotlin으로 변환하면 아래와 같다.
        class Person5 (
            name: String,
            age: Int
        ) {
            fun isAdult(): Boolean {
                // kotlin으로 변환하면 아래와 같은데 이는 Java와 동일하게 표현한것
                return this.age >=  20
            }
            // 코틀린에서는 커스텀 getter로도 사용할 수  있다.
            //val isAdult: Boolean
                // 하나의 expression이니까 이렇게도 되고
                //get() = this.age >= 20
            val isAdult: Boolean
                get() {
                    return this.age >= 20
                }

            // 그래서 코틀린에서는 방법이 3가지가 되는것인데,
            // 객체의 속성이라면, custom getter 그렇지 않다면 함수로 만드는게 추천한다.

            // 추가적으로 커스텀 getter()를 사용하면 get이 되는 순간에 자기 자신을 변형해서 데이터를 내보낼수도있다.
            val name = name
                get() = field.uppercase()
            // name.으로 접근하지 않고 field 키워드로 접근하는 이유는
            // name을 호출하게 된다면, 자연스럽게 getter가 호출이 되는것이다. 즉, name은 name에 대한 getter를 호출하니까 다시 get을 호출한다.
            // 이런식으로 무한루프가 발생하게 된다. 그래서 name으로 접근하지 않는다.
            // 그래서 field라는 예약어를 사용한다. 이러한 field를 뒤에 있는, 보이지 않는 field다 해서 backing field 라고 부른다.

            // setter -> setter 자체를 지양하기 때문에 사실 쓸일이 많이 없을거같다.
            var age = age
                set(value) {
                    field = value+10
                }
        }

        // 최종 정리
        // 1. 코틀린에서는 필드를 만들면 getter와 (필요에 따라) setter가 자동으로 생기는데, 이를 프로퍼티 라고 부른다.
        // 2. 코틀린에서는 주생성자가 필수이다.
        // 3. 코틀린에서는 constructor 키워드를 사용해 부생성자를 추가로 만들 수 있지만 default parameter 혹은 정적 팩토리 메서드를 추천한다.
        // 4. 실제 메모리에 존재하는 것과 무관하게 custom getter와 custom setter를 만들 수 있다.
        // 5. custom getter, custom setter에서 무한루프를 막기 위해 field라는 키워드를 사용한다. 이를 backing field라고 부른다.

    } // end main
}

