# ☕️ Kotlin의 상속 다루기

## 1. 추상 클래스

* **Java 예시**
    ```java
    public abstract class JavaAnimal {
        protected final String species;
        protected final int legCount;

        public JavaAnimal(String species, int legCount) {
            this.species = species;
            this.legCount = legCount;
        }

        abstract public void move();
    }
    ```

* **Kotlin 예시**
  Kotlin에서는 `abstract` 키워드를 사용하며, 상속을 허용할 프로퍼티에는 `open` 키워드를 붙인다.
    ```kotlin
    abstract class Animal(
        protected val species: String,
        protected open val legCount: Int // 프로퍼티를 override 할 수 있도록 open
    ) {
        abstract fun move()
    }
    ```

### 추상 클래스 구현

Kotlin에서는 상속 및 구현에 콜론(`:`)을 사용하며, 상위 클래스의 생성자를 바로 호출해야 한다.

* **Cat 클래스 예시**
    ```java
    // Java
    public class JavaCat extends JavaAnimal {
        public JavaCat(String species) {
            super(species, 4);
        }

        @Override
        public void move() {
            System.out.println("고양이가 걷는다");
        }
    }
    ```
    ```kotlin
    // Kotlin
    class Cat(
        species: String
    ) : Animal(species, 4) { // ':' 로 상속하고, 상위 클래스 생성자 호출
        override fun move() { // override 지시어 사용
            println("고양이가 걷는다")
        }
    }
    ```

* **Penguin 클래스 예시 (프로퍼티 오버라이드)**
    ```java
    // Java (메서드를 오버라이드)
    public class JavaPenguin extends JavaAnimal {
        // ... 생략 ...
        @Override
        public int getLegCount() {
            return super.getLegCount() + this.wingCount;
        }
    }
    ```
    ```kotlin
    // Kotlin (프로퍼티를 직접 오버라이드)
    class Penguin(
        species: String
    ) : Animal(species, 2) {
        private val wingCount: Int = 2

        override fun move() {
            println("펭귄이 움직인다.")
        }

        override val legCount: Int
            get() = super.legCount + this.wingCount
    }
    ```
> **핵심**: Kotlin에서 추상 멤버가 아닌 프로퍼티나 메서드를 오버라이드하려면, 상위 클래스에서 **반드시 `open` 키워드**를 붙여줘야 한다.

---

## 2. 인터페이스

Kotlin의 인터페이스는 Java 8의 `default` 메서드처럼 기본 구현을 가질 수 있다.

* **Java 예시**
    ```java
    public interface JavaFlyable {
        default void act() {
            System.out.println("fly ~");
        }
    }
    ```
* **Kotlin 예시**
  `default` 키워드 없이 메서드 본문을 작성하면 기본 구현 된다.
    ```kotlin
    interface Flyable {
        fun act() {
            println("fly ~")
        }
    }
    ```

### 다중 인터페이스 구현

구현하는 인터페이스들의 시그니처가 동일한 메서드가 있다면, 하위 클래스에서 어떤 상위 타입을 호출할지 명시해야 한다.

* **Java 예시**
    ```java
    public class JavaPenguin extends JavaAnimal implements JavaFlyable, JavaSwimable {
        @Override
        public void act() {
            JavaSwimable.super.act();
            JavaFlyable.super.act();
        }
    }
    ```
* **Kotlin 예시**
  `super<타입>.함수()` 구문을 사용합니다.
    ```kotlin
    class PenguinImpl(
        species: String
    ) : Animal(species, 2), Swimable, Flyable { // 콤마(,)로 구분하여 다중 구현
        override fun move() { /* ... */ }

        override fun act() {
            super<Swimable>.act() // 어떤 인터페이스의 act를 호출할지 명시
            super<Flyable>.act()
        }
    }
    ```

---

## 3. 클래스를 상속할 때 주의할 점

**상위 클래스의 생성자 및 `init` 블록에서는 `open` 프로퍼티를 사용하면 안된다.**

* **예시 코드**
    ```kotlin
    open class Base(
        open val number: Int = 100
    ) {
        init {
            println("Base Class")
            println(number) // 하위 클래스에서 오버라이드한 프로퍼티에 접근
        }
    }

    class Derived(
        override val number: Int
    ) : Base(number) {
        init {
            println("Derived Class")
        }
    }

    fun main() {
        Derived(250)
    }
    ```

* **실행 결과 및 원인**
    ```
    Base Class
    0
    Derived Class
    ```
  **원인**: 하위 클래스(`Derived`)의 인스턴스를 만들면, 상위 클래스(`Base`)의 생성자와 `init` 블록이 **먼저 실행**된다. 이때 `Base`의 `init` 블록이 `number`를 출력하려고 하면, 오버라이드된 `Derived`의 `number`에 접근하게 되는데, 이 시점에는 `Derived`의 `number` 프로퍼티가 아직 생성자 파라미터(250)로 초기화되기 전이라, `Int`의 기본값인 **`0`**이 출력되는 문제가 발생한다.

---

## 4. 상속 관련 키워드

| 키워드 | 설명                                      |
| :--- |:----------------------------------------|
| **`final`** | Override를 할 수 없게 막는다. (기본값, 보이지 않게 적용됨) |
| **`open`** | Override를 할 수 있도록 허용한다.                 |
| **`abstract`** | 하위 클래스에서 반드시 Override 해야 함을 명시한다.       |
| **`override`** | 상위 타입의 멤버를 재정의하고 있음을 명시한다.              |

---

## ✨ 최종 정리

1.  상속 또는 인터페이스 구현 시 콜론(`:`)을 사용한다.
2.  클래스를 상속받을 때는 생성자 파라미터를 포함하여 **상위 클래스의 생성자를 반드시 호출**해야 한다.
3.  상위 클래스의 멤버를 재정의할 때는 `override` 지시어를 **필수로 붙여야 한다.**
4.  추상 멤버가 아닌 일반 멤버는 기본적으로 `final`이므로, 오버라이드를 허용하려면 상위 클래스에서 **`open` 키워드**를 사용해야 한다.
5.  **상위 클래스의 생성자 또는 `init` 블록에서 `open` 프로퍼티를 사용하면**, 하위 클래스의 상태가 초기화되기 전에 접근하여 예기치 못한 버그가 발생할 수 있으므로 **피해야 한다.**