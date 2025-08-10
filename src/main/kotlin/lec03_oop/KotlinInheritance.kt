package org.example.lec03_oop

import org.example.lec03_oop.KotlinInheritance.Derived


// 코틀린에서 상속 다루기
// 1. 추상 클래스
// 2. 인터페이스
// 3. 클래스를 상속할 때 주의할 점
// 4. 상속 관련 지시어
class KotlinInheritance {
    // 1. 추상 클래스
    // java 예시
    //public abstract class JavaAnimal {
    //    protected final String species;
    //    protected final int legCount;
    //
    //    public JavaAnimal(String species, int legCount) {
    //        this.species = species;
    //        this.legCount = legCount;
    //    }
    //
    //    abstract public void mone();
    //
    //    public String getSpecies() {
    //        return species;
    //    }
    //
    //    public int getLegCount() {
    //        return legCount;
    //    }
    //
    //}

    // java로 되어있는 JavaAnimal 클래스를 kotlin으로 바꾸면
    abstract class Animal(
        protected val species: String,
        protected open val legCount: Int
    ) {
        abstract fun move()
    }

    // 다음으로 추상클래스들을 구현하는 Cat 이라는 클래스를 java와 kotlin으로 구현해본다.
    //public class JavaCat extends JavaAnimal{
    //
    //    public JavaCat(String species, int legCount) {
    //        super(species, 4);
    //    }
    //
    //    @Override
    //    public void move() {
    //        System.out.println("고양이가 걷는다");
    //    }
    //}

    // kotlin은 상속 받을떄 ":"(콜론) 를 사용하는데, 상속 받을떈 한칸 띄고 : 를 사용해야 한다
    class Cat (
        species: String
    ) : Animal(species, 4) { // 상위 생성자 바로 호출

        // 메서드 오버라이드를 함수 앞에 붙여준다
        override fun move() {
            println("고양이가 걷는다")
        }
    }

    // 또 다른 penguin 클래스를 만들어서 비교해보자
    // Java
    //public class JavaPenguin extends JavaAnimal{
    //
    //    private final int wingCount;
    //
    //    public JavaPenguin(String species) {
    //        super(species, 2);
    //        this.wingCount = 2;
    //    }
    //
    //    @Override
    //    public void move() {
    //        System.out.println("펭귄이 움직인다");
    //    }
    //
    //    @Override
    //    public String getSpecies() {
    //        return super.getSpecies();
    //    }
    //
    //    @Override
    //    public int getLegCount() {
    //        return super.legCount + this.wingCount;
    //    }
    //}

    // 코틀린
    class Penguin (
        species: String
    ) : Animal(species, 2) {
        private val wingCount: Int = 2

        override fun move() {
            println("펭귄이 움직인다.")
        }

        // LegCount Override
        // 상위 클래스에 프로퍼티를 오버라이드 하는 경우 추상 프로퍼티가 아닌 경우 코틀린에서는 반드시 open 키워드를 붙여주어야 한다.
        override val legCount: Int
            get() = super.legCount + this.wingCount
    }

    // 2. 인터페이스
    // 위에서 작성한 펭귄 예제 코드를 기반으로 2가지 인터페이스를 구현한다.
    // Java Swimable, Flyable
    //public interface JavaSwimable {
    //    default void act() {
    //        System.out.println("수영 ing......~");
    //    }
    //  // 추상 메서드
    //  // void fly();
    //}
    //public interface JavaFlyable {
    //    default void act() {
    //        System.out.println("fly ~");
    //    }
    //}

    // kotlin Swimable, Flyable
    // 코틀린은 default를 사용하지 않아도 자동으로 default가 생성된다
    interface Flyable {
        fun act() {
            println("fly ~")
        }
        // 추상 메서드
        //fun fly()
    }

    interface Swimable {
        fun act() {
            println("수영 ing ~")
        }
    }

    // 이것을 구현한 펭귄 클래스를 각각 Java와 코틀린으로 구현하면
    //public class JavaPenguin extends JavaAnimal implements JavaFlyable, JavaSwimable{
    //    @Override
    //    public void act() {
    //        JavaSwimable.super.act();
    //        JavaFlyable.super.act();
    //    }
    //}

    class PenguinImpl (
        species: String
    ) : Animal(species, 2), Swimable, Flyable {
        private val wingCount: Int = 2

        override fun move() {
            println("펭귄이 움직인다.")
        }

        // LegCount Override
        // 상위 클래스에 프로퍼티를 오버라이드 하는 경우 추상 프로퍼티가 아닌 경우 코틀린에서는 반드시 open 키워드를 붙여주어야 한다.
        override val legCount: Int
            get() = super.legCount + this.wingCount

        override fun act() {
            // 중복되는 인터페이스를 특정할 때는 super<타입>.함수 를 사용한다.
            super<Swimable>.act()
            super<Flyable>.act()
        }
    }

    // 3. 클래스를 상속받을 때 주의할 점
    // Derived 클래스가 Base 클래스를 상속받고 있다
    open class Base(
        open val number: Int = 100
    ) {
        init {
            println("Base Class")
            println(number)
        }
    }

    class Derived(
        override val number: Int
    ) : Base(number) {
        init {
            println("Derived Class")
        }
    }

    // 2개의 클래스 모두 초기화 블록이 있는데, 만약 Derived 클래스를 인스턴스화 한다면 초기화는 어떤 순서로 실행되고, number 값에는 뭐가 들어갈까
    fun main() {
        Derived(250)
    }
    // 실행 결과는 다음과 같은데,
    //Base Class
    //0
    //Derived Class
    // 상위 클래스의 초기화 블록이 먼저 실행된다.
    // 그 다음 값이 0이다. Base 클래스에 생성자에서 100을 넣어줬고 Derived 클래스에서 250을 넣어줬는데 왜 0일까
    // 상위 클래스에서 number를 호출하면, 하위 클래스에 있는 넘버를 가져오게 되는데 상위 클래스에 constructor가 먼저 실행된 단계라서 하위 클래스에 초기화가 이루어지지 않은것이다.
    // 그 상태에서 하위 클래스에 number에 접근하다 보니 int의 기초값인 0이 나오게 된것이다.

    // 상위 클래스에 constructor와 init 블록에서는 하위 클래스의 field, final이 아닌 프로퍼티에는 접근하면 안된다.
    // 그래서 상위 클래스를 설계할 떄 생성자 또는 초기화 블록에 사용되는 프로퍼티에는 open을 피해야 한다.

    // 4. 상속 관련 키워드
    // 1. final : override를 할 수 없게 만든다. default로 보이지 않게 존재한다.
    // 2. open: override를 열어 준다.
    // 3. abstract : 반드시 override 해야 한다
    // 4. override : 상위 타입 오버라이드

    // 최종 정리
    // 1. 상속 또는 구현을 할 때에 콜론(:) 을 사용해야 한다.
    // 2. 상위 클래스 상속을 구현할 때 생성자를 반드시 호출해야 한다.
    // 3. override를 필수로 붙여야 한다.
    // 4. 추상 멤버가 아니면 기본적으로 오버라이드가 불가능하다. open을 사용해주어야 한다.
    // 5. 상위 클래스의 생성자 또는 초기화 블록에서 open 프로퍼티를 사용하면 얘기치 못한 버그가 생길 수 있다.

}

fun main() {
    Derived(250)
}