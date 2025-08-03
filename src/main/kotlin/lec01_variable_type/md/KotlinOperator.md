# ☕️ Java와 Kotlin의 연산자 비교 및 정리

## 1. 단항, 산술, 비교 연산자

단항 연산자, 산술 연산자, 산술 대입 연산자는 Java와 Kotlin에서 **모두 동일하게 동작한다.**

비교 연산자(`>`, `<` 등) 또한 사용법은 같지만, 객체를 비교할 때 Java와 달리 Kotlin은 **내부적으로 `compareTo`를 자동으로 호출**해주는 큰 장점이 있다.

* **Java**: `Comparable` 인터페이스를 구현하더라도, `.compareTo()` 메서드를 직접 호출해야 한다.

    ```java
    // Java의 Money 클래스
    public class Money implements Comparable<Money> {
        private final long amount;

        public Money(long amount) {
            this.amount = amount;
        }

        @Override
        public int compareTo(Money o) {
            return Long.compare(this.amount, o.amount);
        }

        public static void main(String[] args) {
            Money money_1 = new Money(2_000L);
            Money money_2 = new Money(1_000L);

            if (money_1.compareTo(money_2) > 0) {
                System.out.println("money_1의 금액이 money_2 보다 금액이 커요");
            }
        }
    }
    ```

* **Kotlin**: `Comparable`을 구현한 객체는 비교 연산자를 바로 사용할 수 있어 코드가 훨씬 간결하고 직관적이다.

    ```kotlin
    // 위의 Java 코드를 Kotlin으로 변환
    fun main() {
        val money_1 = Money(2_000L)
        val money_2 = Money(1_000L)

        // compareTo()를 자동으로 호출해줘서 더 편하다.
        if (money_1 > money_2) {
            println("money_1의 금액이 money_2 보다 금액이 커요")
        }
    }
    ```

## 2. 비교 연산자와 동등성, 동일성

* **동등성(Equality)**: 두 객체의 **값**이 같은지를 비교.
* **동일성(Identity)**: 두 객체의 **메모리 주소**가 같은지를 비교.

| 구분 | Java | Kotlin | 설명             |
| :--- | :--- | :--- |:---------------|
| **동일성 (주소 비교)** | `==` | `===` | 두 객체의 주소가 같은가? |
| **동등성 (값 비교)** | `.equals()` | `==` | 두 객체의 값이 같은가?  |

Kotlin의 `==`는 내부적으로 `equals`를 호출해주므로 `null` 체크까지 안전하게 처리해준다.

```kotlin
fun main() {
    val money3 = Money(1_000L)
    val money4 = money3 // money3와 money4는 같은 객체를 참조
    val money5 = Money(1_000L) // money3와 값은 같지만 다른 객체

    // 동일성 비교는 ===
    println(money3 === money4) // true

    // 동등성 비교는 == 이며, 내부적으로 equals가 호출된다
    println(money3 == money5) // true
}
```

## 3. 논리 연산자와 Kotlin에만 있는 연산자

논리 연산자(`&&`, `||`, `!`)는 Java와 동일하게 동작하며, 불필요한 연산을 생략하는 **지연 평가(Lazy Evaluation)**를 수행한다.

Kotlin에는 코드 작성을 편리하게 해주는 고유의 연산자들이 추가되었다.

* `in` / `!in` : 컬렉션이나 범위에 특정 요소가 **포함되어 있다 / 포함되어 있지 않다**를 확인.
* `a..b` : `a`부터 `b`까지의 **범위(Range) 객체**를 생성.
* `a[i]` : 컬렉션이나 배열에서 특정 **인덱스(`i`)의 값을 가져옵니다**. (`get` 메서드 호출)
* `a[i] = b` : 특정 **인덱스(`i`)에 값(`b`)을 할당합니다**. (`set` 메서드 호출)


## 4. 연산자 오버로딩 (Operator Overloading)

Kotlin에서는 `operator` 키워드를 사용해 객체마다 사용할 연산자를 직접 정의할 수 있다. 이를 통해 코드의 가독성을 크게 향상시킬 수 있다.

* **Java**: 연산을 위해서는 항상 별도의 메서드를 정의하고 호출해야 한다.

    ```java
    // Java에서 plus 메서드를 만들어 사용
    public class Money {
        //...
        public Money plus(Money other) {
            return new Money(this.amount + other.amount);
        }

        public static void main(String args[]) {
            Money money3 = new Money(1_000L);
            Money money4 = new Money(2_000L);
            System.out.println(money3.plus(money4));
        }
    }
    ```

* **Kotlin**: `operator` 키워드를 붙인 함수를 정의하면, 해당 연산자(`+`)를 객체에 직접 사용할 수 있다.

    ```kotlin
    data class KotlinMoney(
        val amount: Long
    ) {
        // 메서드를 따로 정의하고 + 기호로 호출해도 개발자가 정의한 대로 연산된다.
        operator fun plus(other: KotlinMoney): KotlinMoney {
            return KotlinMoney(this.amount + other.amount)
        }
    }

    fun main() {
        val koMoney1 = KotlinMoney(1_000L)
        val koMoney2 = KotlinMoney(2_000L)

        // 연산자를 직접 사용해 직관적인 코드 작성 가능
        println(koMoney2 + koMoney1)
    }
    ```


## ✨ 최종 정리

* 단항 연산자, 산술 연산자, 산술 대입 연산자는 Java와 **동일**하다.
* 비교 연산자의 사용법도 동일하나, Kotlin에서는 객체끼리도 `compareTo` 자동 호출을 통해 **비교 연산자를 직접 사용할 수 있다.**
* `in`, `!in`, `a..b`, `a[i]`, `a[i] = b`와 같이 **Kotlin에서 새로 생긴 유용한 연산자**들이 있다.
* Kotlin에서는 **객체끼리의 연산자를 직접 정의하여 사용할 수 있다** (연산자 오버로딩).