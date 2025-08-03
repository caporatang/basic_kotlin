package org.example.lec01_variable_type

import lec01_variable_type.helper.JavaMoney
import org.example.lec01_variable_type.helper.KotlinMoney


// 일단 코틀린의 연산자는 Java와 비슷한 점이 많다.
// 1. 단항 연산자 / 산술 연산자
// 2. 비교 연산자와 동등성, 동일성
// 3. 논리 연산자 / 코틀린에만 있는 연산자
// 4. 연산자 오버로딩
class KotlinOperator {
    // 1. 단항, 산술, 산술대입 연산자는 Java와 모두 동일하게 동작한다.
    // 비교 연산자 또한 사용법은 동일하나, 객체를 비교한다면 Java와 다르게 내부적으로 compareTo를 호출해서 비교한다.

    //public class Money implements Comparable<Money> {
    //
    //    private final long amount;
    //
    //    public Money(long amount) {
    //        this.amount = amount;
    //    }
    //
    //    @Override
    //    public int compareTo(@NotNull Money o) {
    //        return Long.compare(this.amount, o.amount);
    //    }
    //    public static void main(String[] args) {
    //    Money money1 = new Money(2_000L);
    //    Money money2 = new Money(1_000L);
    //
    //    if (money1.compareTo(money2) > 0) {
    //        System.out.println("money1의 금액이 money2 보다 금액이 커요");
    //    }
    //}

    // 위의 java 코드를 코틀린으로 변환하면 아래와 같다. java에서 사용할떄는 비교연산자 때문에 헷갈렸는데, 코틀린은 자동으로 호출해주기 때문에 더 편한거같다.
    //fun main() {
    //    val money1 = Money(2_000L);
    //    val money2 = Money(1_000L);
    //    if(money1 > money2) {
    //        println("money1의 금액이 money2 보다 금액이 커요");
    //    }
    //}

    // 2. 비교 연산자와 동등성, 동일성
    // 동등성은 두 객체의 '값' 이 같은가 를 비교하는것이고 동일성은 두 객체의 '주소' 가 같은가를 비교하는것이다.
    // Java에서는 주소가 같은지는 == 연산자로 확인했고 값이 같은지는 .equals()를 사용해서 비교했다.
    // 코틀린에서는 동일성 비교는 === 을 사용하고, 동등성에 == 을 호출한다. 그리고 == 을 사용하면 간접적으로 equals를 호출해준다.

    //val money3 = Money(1_000L)
    //val money4 = money3
    //val money5 = Money(1_000L)
    // 동일성 비교는 ===
    //println(money3 === money4)
    // 이렇게 비교하면 equals가 호출된다
    //println(money3 == money5)

    // 3. 논리 연산자와 코틀린에만 있는 연산자
    // 논리 연산자는 && || ! 이런 종류가 있고, Java와 동일하게 동작한다. 그리고 Java처럼 Lazy 연산을 수행한다.
    // 코틀린에서는 in / !in 연산자가 있다. 이 연산자는 컬렉션이나 범위에 포함되어 있다, 포함되어 있지 않다 를 뜻한다.
    // 그리고 a..b 처럼 .. 연산자가 있다. 이 연산자는 a부터 b까지의 범위 객체를 생성한다.
    // a[i] a에서 특정 Index i로 값을 가져오는 연산자도 있고,
    // a[i] = b a의 특정 index i에 b를 할당하는 연산자도 있다.

    // 4. 연산자 오버로딩
    // Kotlin에서는 객체마다 연산자를 직접 정의할 수 있다.

    // Java에서 plus 라는 메서드를 만들어서 사용한다면 아래와 같이 정의해서 사용한다.
    // public Money plus(Money other) {
    //     return new Money(this.amount + other.amount);
    // }
    // public static void main(String args[]) {
    //  Money money3 = new Money(1_000L);
    //  Money money4 = new Money(2_000L);
    //  System.out.println(money3.plus(money4));
    // }

    // 하지만 코틀린은 아래와 같이 정의해서 사용할 수 있다. 메서드를 따로 정의하고 + 기호로 호출해도 개발자가 정의한 대로 연산된다.
    //data class KotlinMoney(
    //    val amount: Long
    //) {
    //
    //    operator fun plus(other: KotlinMoney): KotlinMoney {
    //        return KotlinMoney(this.amount + other.amount)
    //    }
    //}

    //val koMoney1 = KotlinMoney(1_000L)
    //val koMoney2 = KotlinMoney(2_000L)
    //println(koMoney2 + koMoney1)

    // 정리
    // 1. 단항 연산자, 산술연산자, 산술대입연산자 Java와 동일하다.
    // 2. 비교 연산자 사용법도 Java와 동일하다. 다만 객체끼리도 자동 호출되는 compareTo를 이용해 비교 연산자를 사용할 수 있다.
    // 3. in, !in / a..b / a[i] / a[i] = b 와 같이 코틀린에서 새로 생긴 연산자도 있다.
    // 4. 객체끼리의 연산자를 직접 정의해서 사용할수있다.

}




fun main() {
    val money1 = JavaMoney(2_000L)
    val money2 = JavaMoney(1_000L)

    if(money1 > money2) {
        println("money1의 금액이 money2 보다 금액이 커요");
    }

    val money3 = JavaMoney(1_000L)
    val money4 = money3
    val money5 = JavaMoney(1_000L)
    // 동일성 비교는 ===
    println(money3 === money4)
    // 이렇게 비교하면 equals가 호출된다
    println(money3 == money5)

    val koMoney1 = KotlinMoney(1_000L)
    val koMoney2 = KotlinMoney(2_000L)
    println(koMoney2+ koMoney1)




}