# ☕️ Kotlin의 함수 다루기

## 1. 함수 선언 문법

Kotlin의 함수는 Java보다 훨씬 유연하고 간결하게 선언할 수 있다. 특히 `if-else`와 같은 제어문이 **Expression**이라는 특징을 활용하면 다양한 형태로 함수를 표현할 수 있다.

* **Java 예시**
```java
    public int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }
```

* **Kotlin 예시 1: Block Body**
  `{...}` 블록을 사용하는 가장 기본적인 형태의 함수
```kotlin
    fun max(a: Int, b: Int): Int {
        return if (a > b) {
            a
        } else {
            b
        }
    }
```

* **Kotlin 예시 2: Expression Body**
  함수의 본문(body)이 하나의 Expression으로 표현될 경우, `{}`와 `return`을 생략하고 `=`로 바로 할당할 수 있다.
```kotlin
    fun max2(a: Int, b: Int): Int =
        if (a > b) {
            a
        } else {
            b
        }
```

* **Kotlin 예시 3: 타입 추론을 활용한 Expression Body**
Expression Body를 사용하는 경우, 컴파일러가 반환 타입을 추론할 수 있으므로 반환 타입 선언을 생략할 수 있다.
```kotlin
    fun max3(a: Int, b: Int) = if (a > b) a else b
```
-  **주의**: Block Body (`{...}`)를 사용하면서 `Unit`(void)이 아닌 타입을 반환하는 경우에는 **반드시 반환 타입을 명시**해야 한다.

---

## 2. Default Parameter (기본값 파라미터)

Java에서는 특정 파라미터에 기본값을 부여하고 싶을 때 **오버로딩(Overloading)**을 사용해야 했다.

* **Java 예시: 오버로딩**
```java
    // 기본 형태
    public void repeat(String str, int num, boolean useNewLine) {
        for (int i = 1; i <= num; i++) {
            if (useNewLine) {
                System.out.println(str);
            } else {
                System.out.print(str); // print로 수정
            }
        }
    }

    // 오버로딩 1
    public void repeat(String str, int num) {
        repeat(str, num, true); // useNewLine 기본값을 true로 설정
    }

    // 오버로딩 2
    public void repeat(String str) {
        repeat(str, 3, true); // num과 useNewLine 기본값 설정
    }
```
- Java의 오버로딩은 비슷한 함수를 여러 개 만들어야 하는 단점이 있습니다.

* **Kotlin 예시: Default Parameter**
  Kotlin은 파라미터에 직접 기본값을 할당하여 하나의 함수로 동일한 기능을 구현 가능 하다.
    ```kotlin
    fun repeat(
        str: String,
        num: Int = 3,             // 기본값 할당
        useNewLine: Boolean = true  // 기본값 할당
    ) {
        for (i in 1..num) {
            if (useNewLine) {
                println(str)
            } else {
                print(str)
            }
        }
    }
    ```
  - 함수 호출 시 해당 파라미터를 생략하면 지정된 기본값이 된다.

---

## 3. Named Argument (이름 있는 인자)

`Default Parameter`와 함께 사용하면 시너지를 내는 기능이다. 특정 파라미터의 값만 지정하고 싶을 때, 파라미터 이름을 명시하여 값을 전달할 수 있다.

* **기본 호출**: `repeat("Hello World", 3, false)`
* **Named Argument 사용**: `num`은 기본값을 사용하고 `useNewLine`만 `false`로 바꾸고 싶을 때
```kotlin
    repeat("Hello world", useNewLine = false) // num=3은 기본값 그대로 사용된다.
```

* **장점**:
    1.  **빌더(Builder)의 장점**: 빌더 패턴처럼 필요한 값만 명시적으로 설정할 수 있다.
    2.  **실수 방지**: 파라미터 순서와 상관없이 이름을 지정해 전달하므로 실수를 줄일 수 있다.
        ```kotlin
        fun printNameAndGender(name: String, gender: String) {
            println(name)
            println(gender)
        }

        // 순서가 헷갈려도 이름을 명시하면 안전하다.
        printNameAndGender(gender = "남자", name = "김태일")
        ```
---

## 4. 같은 타입의 여러 파라미터 받기 (가변인자)

* **Java 예시: `...` 사용**
```java
    public static void printAll(String... strings) {
        for (String str : strings) {
            System.out.println(str);
        }
    }

    // 호출 시
    String[] array = new String[]{"A", "B", "C"};
    printAll(array);
    printAll("A", "B", "C");
```

* **Kotlin 예시: `vararg` 키워드와 스프레드 연산자(`*`)**
  Kotlin은 `vararg` 키워드를 사용하며, 배열을 인자로 넘길 때는 배열명 앞에 **스프레드 연산자(`*`)**를 붙여주어야 한다.
```kotlin
    fun printAll(vararg strings: String) {
        for (str in strings) {
            println(str)
        }
    }

    // 호출 시
    printAll("A", "B", "C")

    val array = arrayOf("A", "B", "C")
    printAll(*array) // 배열의 원소를 펼쳐서 넣어준다는 의미
```
---

## ✨ 최종 정리

1.  Kotlin의 함수 문법은 Java와 다르며, 더 유연하고 간결하다.
2.  함수의 본문(body)이 **하나의 값으로 간주되는 경우(Expression Body)**, `{}` 블록과 `return`을 생략하고 `=`로 할당할 수 있으며, 이 경우 **반환 타입 생략(타입 추론)**도 가능하다.
3.  함수 파라미터에 **기본값(`Default Parameter`)**을 설정하여 오버로딩을 대체할 수 있다.
4.  함수를 호출할 때 **`파라미터명 = 값`** 형식으로 특정 인자를 지정(`Named Argument`)하여 전달할 수 있다.
5.  가변인자에는 `vararg` 키워드를 사용하며, 배열을 전달할 때는 **스프레드 연산자(`*`)**를 붙여야 한다.