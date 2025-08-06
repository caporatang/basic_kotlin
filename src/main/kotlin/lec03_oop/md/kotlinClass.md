# ☕️ Kotlin의 클래스 다루기

## 1. 클래스와 프로퍼티

Java에서 데이터 클래스를 만들려면 필드, 생성자, getter, setter 등 많은 보일러플레이트 코드가 필요하다.

* **Java 예시**
```java
    public class JavaPerson {
        private final String name;
        private int age;

        public JavaPerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
```

Kotlin은 **프로퍼티(Property)** 개념을 통해 이를 간결하게 만들 수 있다.

> **프로퍼티(Property)** = 필드(Field) + 접근자(Getter/Setter)

* **Kotlin 예시**
  `val`은 getter만, `var`는 getter와 setter를 모두 자동으로 생성한다.
```kotlin
    // 1단계: 기본 구조
    class Person constructor(name: String, age: Int) {
        val name: String = name
        var age: Int = age
    }

    // 2단계: 프로퍼티를 주 생성자에서 바로 선언 (가장 흔한 방식)
    // 'constructor' 키워드와 클래스 바디 '{}'도 내용이 없으면 생략 가능
    class Person2(val name: String, var age: Int)
```

* **사용법**
  Kotlin은 프로퍼티에 직접 접근하는 것처럼 보이지만, 실제로는 자동으로 생성된 getter와 setter를 호출한다.
```kotlin
    val person = Person2("김태일", 100)
    println(person.name) // person.getName() 호출
    person.age = 10      // person.setAge(10) 호출
```

---

## 2. 생성자와 init

클래스 초기화 시점의 로직을 처리하는 방법.

### 주 생성자(Primary Constructor)와 `init` 블록

* **Java**: 생성자 내에서 유효성 검증과 같은 로직을 수행한다.
    ```java
    public JavaPerson(String name, int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("나이는 0보다 작을 수 없습니다.");
        }
        this.name = name;
        this.age = age;
    }
    ```

* **Kotlin**: 주 생성자에서는 코드를 직접 실행할 수 없으므로, **`init` 블록**을 사용한다. `init` 블록은 객체가 생성될 때 주 생성자 실행 직후 호출된다.
    ```kotlin
    class Person3(val name: String, var age: Int) {
        init {
            if (age <= 0) {
                throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
            }
            println("초기화 블록")
        }
    }
    ```

### 부 생성자(Secondary Constructor)

* **Java**: `this()`를 사용하여 다른 생성자를 호출하는 오버로딩을 사용한다.
    ```java
    public JavaPerson(String name) {
        this(name, 1); // 다른 생성자 호출
    }
    ```
* **Kotlin**: `constructor` 키워드로 부 생성자를 정의하며, 반드시 `this()`를 통해 주 생성자나 다른 부 생성자를 호출해야 한다.
    ```kotlin
    class Person3(val name: String, var age: Int) {
        // ... init 블록 ...

        // 부 생성자
        constructor(name: String) : this(name, 1) {
            println("부 생성자 1")
        }
    }
    ```
> **권장사항**: Kotlin에서는 부 생성자를 만드는 것보다 **Default Parameter**를 사용하는 것을 더 권장한다. 변환 로직이 필요하다면 **정적 팩토리 메서드** 패턴을 고려하는 것이 좋다.

---

## 3. 커스텀 Getter, Setter와 Backing Field

프로퍼티의 접근자를 직접 구현할 수 있다.

### 커스텀 Getter

`isAdult`와 같이 기존 프로퍼티를 가공하여 새로운 값을 제공하고 싶을 때 사용한다.

* **Java**: 별도의 메서드로 구현.
    ```java
    public boolean isAdult() {
        return this.age >= 20;
    }
    ```
* **Kotlin**: 함수로 만들거나, 커스텀 getter를 사용해 프로퍼티처럼 만들 수 있다.
    ```kotlin
    class Person5(val name: String, var age: Int) {
        // 1. 함수로 구현
        fun isAdultFunc(): Boolean {
            return this.age >= 20
        }

        // 2. 커스텀 Getter를 사용한 프로퍼티
        val isAdult: Boolean
            get() = this.age >= 20
            // get() { return this.age >= 20 } 와 동일
    }
    ```
> **권장사항**: 해당 객체의 **속성**으로 판단되면 커스텀 Getter를, **행위**로 판단되면 함수를 사용하는 것이 좋다. (`person.isAdult` vs `person.isAdultFunc()`)

### Backing Field

커스텀 getter/setter 내부에서 프로퍼티 자기 자신을 참조하면 무한 루프에 빠지게 된다. (`name`의 getter가 `name`을 호출하고, 그 호출이 다시 getter를 부르는 식)

이를 방지하기 위해, 자기 자신을 가리키는 보이지 않는 필드인 **`field`** 라는 예약어를 사용하는데, 이를 **Backing Field**라고 한다.

```kotlin
class Person5(name: String, var age: Int) {
    val name: String = name
        get() = field.uppercase() // field는 name의 backing field를 의미

    var ageWithOffset: Int = age
        set(value) {
            // setter의 파라미터는 관례적으로 value를 사용
            field = value + 10 // field는 ageWithOffset의 backing field
        }
}
```
## ✨ 최종 정리

* Kotlin에서는 필드를 만들면 getter와 (필요에 따라) setter가 자동으로 생기는데, 이를 **프로퍼티(Property)**라고 부른다.
* Kotlin의 클래스는 **주 생성자(Primary Constructor)**가 필수이며, 클래스 선언부에 위치한다. (단, 파라미터가 없다면 생략 가능)
* `constructor` 키워드로 **부생성자(Secondary Constructor)** 를 추가할 수 있지만, 그보다는 **Default Parameter**나 **정적 팩토리 메서드**를 권장한다.
* 실제 메모리에 필드가 존재하지 않더라도, 계산된 값을 제공하는 **커스텀 Getter**를 만들 수 있다.
* 커스텀 getter/setter에서 무한 루프를 막기 위해 **`field`** 라는 키워드를 사용하며, 이를 **Backing Field**라고 부른다.
