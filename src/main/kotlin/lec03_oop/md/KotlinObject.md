# 코틀린에서 object 다루기

코틀린의 `object` 키워드는 **static 멤버**, **싱글톤**, **익명 클래스**라는 세 가지 주요 목적을 위해 사용된다.

---

## 1. Static 함수와 변수 (`companion object`)

Java에서는 `static` 키워드를 사용하여 클래스에 속한 정적 멤버를 만든다.   
코틀린에는 `static`이 없는 대신 **`companion object`**를 사용한다.

### Java 예시

```java
public class ObjectPerson {
    private static final int MIN_AGE = 1;

    public static ObjectPerson newBaby(String name) {
        return new ObjectPerson(name, MIN_AGE);
    }

    private String name;
    private int age;

    public ObjectPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### Kotlin 예시

`companion object` 블록 내에 선언된 프로퍼티와 함수는 클래스 이름을 통해 직접 접근할 수 있다.

```kotlin
class Person private constructor(
    var name: String,
    var age: Int
) {
    // companion object는 클래스와 동행하는 유일한 오브젝트.
    companion object {
        // const는 컴파일 시점에 값이 할당되는 진짜 상수. (기본 타입, String만 가능)
        // const가 없으면 런타임에 값이 할당된다.
        private const val MIN_AGE = 1

        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }
    }
}

// 사용 예시
val baby = Person.newBaby("아기")
```

---

## 2. 싱글톤 (Singleton)

싱글톤은 애플리케이션 전체에서 단 하나의 인스턴스만 존재하는 객체.

### Java 예시

일반적으로 private 생성자와 자기 자신을 담는 static 인스턴스를 통해 구현한다.

```java
public class JavaSingleton {
    private static final JavaSingleton INSTANCE = new JavaSingleton();

    private JavaSingleton() {}

    public static JavaSingleton getInstance() {
        return INSTANCE;
    }
}
```

### Kotlin 예시

코틀린에서는 `object` 키워드를 클래스 선언 대신 사용하여 손쉽게 싱글톤을 만들 수 있다.

```kotlin
// 'object' 키워드 하나로 싱글톤 구현이 끝.
object Singleton {
    var a: Int = 0
}

fun main() {
    println(Singleton.a) // 출력: 0
    Singleton.a += 10
    println(Singleton.a) // 출력: 10

    // val singleton1 = Singleton() // 생성자가 없으므로 인스턴스화 불가
}
```

---

## 3. 익명 클래스 (Anonymous Class)

익명 클래스는 특정 인터페이스나 클래스를 상속받는 구현체를 일회성으로 사용할 때 쓰는 이름 없는 클래스다.

### Java 예시

`new` 키워드와 함께 인터페이스나 추상 클래스의 생성자를 호출하여 구현한다.

```java
public interface Movable {
    void move();
    void fly();
}

// 사용 예시
public class Main {
    public static void main(String[] args) {
        moveSomething(new Movable() {
            @Override
            public void move() {
                System.out.println("move~");
            }

            @Override
            public void fly() {
                System.out.println("fly~");
            }
        });
    }
    
    private static void moveSomething(Movable movable) {
        movable.move();
        movable.fly();
    }
}
```

### Kotlin 예시

코틀린에서는 `object : 타입` 형태로 익명 클래스를 표현한다.

```kotlin
interface MovableKotlin {
    fun move()
    fun fly()
}

private fun moveSomething(movable: MovableKotlin) {
    movable.move()
    movable.fly()
}

fun main() {
    // Java의 'new Movable() { ... }' 와 유사한 형태
    moveSomething(object : MovableKotlin {
        override fun move() {
            println("움직인다~")
        }

        override fun fly() {
            println("난다~")
        }
    })
}
```

---

## 최종 요약

1.  **Static 멤버**: Java의 `static` 변수와 함수를 만들려면, 코틀린에서는 **`companion object`**를 사용해야 한다.
2.  **Companion Object 특징**: `companion object`는 이름 지정이 가능하고 다른 타입을 상속받을 수 있는 **하나의 객체**다.
3.  **싱글톤**: 코틀린에서 싱글톤 클래스를 만들 때는 **`object`** 키워드를 사용한다.
4.  **익명 클래스**: 코틀린에서 익명 클래스를 만들 때는 **`object : 타입`** 구문을 사용한다.
