package org.example.lec03_oop

import lec03_oop.helper.kotlinObject.Movable
import org.example.lec03_oop.helper.kotlinObject.MovableKotlin

// 코틀린에서 object 키워드 다루기
// 1. static 함수와 변수
// 2. 싱글톤
// 3. 익명 클래스
class KotlinObject {
    // 1. static 함수와 변수
    // java 예시
    //public class ObjectPerson {
    //    private static final int MIN_AGE = 1;
    //
    //    public static ObjectPerson newBaby(String name) {
    //        return new ObjectPerson(name, MIN_AGE);
    //    }
    //
    //    private String name;
    //    private int age;
    //
    //    public ObjectPerson(String name, int age) {
    //        this.name = name;
    //        this.age = age;
    //    }
    //}

    // kotlin으로 바꾸면
    class Person private constructor(
        var name: String,
        var age: Int
    ) {
        // kotlin에는 static이 없다.
        companion object {
            // const 키워드를 붙이지 않으면 val 이라는게 붙으면 0이라는 값이 런타임시에 할당된다.
            // const를 붙여주면 컴파일시에 변수가 할당된다. 즉, const 키워드는 진짜 상수에 붙이기 위한 용도이며 기본 타입과 String에만 붙일 수 있다.
            private const val MIN_AGE = 1
            fun newBaby(name: String): Person {
                return Person(name, MIN_AGE)
            }
        }
    }
    // static이라는 것은 클래스가 인스턴스화 될 때 새로운 값이 복제되는게 아니라 정적으로 인스턴스끼리의 값을 공유하는것이다.
    // companion object는 클래스와 동행하는 유일한 오브젝트, 인스턴스가 여러 개 생기더라도 이 클래스라는 설계도와 동행하는 오브젝트 라고 생각하면 된다.

    // companion object가 java와 다른점은 companion object는 하나의 객체로 간주된다. 떄문에 이름을 붙일 수도 있고, 따로 interface도 구현할 수도 있다.
    interface Log {
        fun log()
    }

    class Person2 private constructor(
        var name: String,
        var age: Int
    ) {
        // 이름 붙임
        companion object Factory : Log{
            private const val MIN_AGE = 1
            fun newBaby(name: String): Person2 {
                return Person2(name, MIN_AGE)
            }

            override fun log() {
                println("person2 companion ~")
            }
        }
    }
    // 유틸성 함수들을 추가해서 사용할 수 있지만 유틸은 가급적 최상단 파일을 활용하는 것이 좋다.

    // 2. 싱글톤
    // 싱글톤은 단 하나의 인스턴스만을 갖는 클래스다.

    // java 예시
    //public class JavaSingleton {
    //    private static final JavaSingleton INSTANCE = new JavaSingleton();
    //
    //    private JavaSingleton() {}
    //
    //    public static JavaSingleton getInstance() {
    //        return INSTANCE;
    //    }
    //
    //}

    // 코틀린은 object 키워드를 붙여주기만 하면 자동으로 싱글톤이다.
    object Singleton

    // 사용 예시
    object TestSingle {
        var a: Int = 0
    }

    fun main() {
        // 실행 결과는
        // 0
        // 10
        println(TestSingle.a)
        TestSingle.a += 10
        println(TestSingle.a)
    }

    // 3. 익명 클래스
    // 익명 클래스는 인터페이스나 클래스를 상속받은 구현체를 일회성으로 사용할 때 쓰는 클래스다.
    // java 예시
    //public interface Movable {
    //    void move();
    //    void fly();
    //
    //    private static void moveSomething(Movable movable) {
    //        movable.move();
    //        movable.fly();
    //    }
    //
    //    public static void main(String[] args) {
    //        moveSomething(new Movable() {
    //            @Override
    //            public void move() {
    //                System.out.println("move~");
    //            }
    //
    //            @Override
    //            public void fly() {
    //                System.out.println("fly ~ ");
    //            }
    //        });
    //    }
    //}

    //fun main() {
    //    moveSome(object : MovableKotlin {
    //        override fun move() {
    //            TODO("Not yet implemented")
    //        }
//
    //        override fun fly() {
    //            TODO("Not yet implemented")
    //        }
    //    })
    //}

    private fun moveSome(movableKotlin: MovableKotlin) {
        movableKotlin.move()
        movableKotlin.fly()
    }
    // java에서는 new 타입 이름 () 그 다음 중괄호로 인터페이스나 추상클래스 등을 구현했지만
    // 코틀린에서는 object : 타입 그리고 중괄호로 익명클래스를 표현한다.

    // 최종 정리
    // 1. Java의 static 변수와 함수를 만들려면, Kotlin에서는 companion object를 사용해야 한다.
    // 2. companion object도 하나의 객체로 간주되기 때문에 이름을 붙일 수 있고, 다른 타입을 상속받을 수도 있다.
    // 3. Kotlin에서 싱글톤 클래스를 만들 때 object 키워드를 사용한다.
    // 4. Kotlin에서 익명 클래스를 만들 때 object : 타입을 사용한다.

} // class end

