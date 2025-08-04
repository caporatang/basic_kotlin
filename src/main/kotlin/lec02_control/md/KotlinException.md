# ☕️ Kotlin의 예외(Exception) 처리

## 1. try-catch-finally 구문

`try-catch-finally`의 기본적인 문법은 Java와 Kotlin에서 동일하다. 하지만 Kotlin은 이를 **Expression**으로 취급하여 더 간결하게 사용할 수 있다.

#### **1) 기본 사용법**

* **Java 예시**
```java
    private int parseIntOrThrow(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("주어진 %s는 숫자가 아닙니다", str));
        }
    }
```

* **Kotlin 예시**
```kotlin
    fun parseIntOrThrow(str: String): Int {
        try {
            return str.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("주어진 ${str}은 숫자가 아닙니다.")
        }
    }
```
  > **차이점**: Kotlin에서는 기본 타입 간 형변환 시 `.toType()`을 사용하고, `new` 키워드가 없으며, 문자열 포맷팅이 간결하다.

#### **2) try-catch를 Expression으로 사용**

Kotlin에서는 `try-catch` 블록 자체가 값을 반환할 수 있는 **Expression**이다.

* **Java 예시** (null 반환)
```java
    private Integer parseIntOrThrowNull(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
```

* **Kotlin 예시**
  `try` 블록의 성공 시 결과 또는 `catch` 블록의 결과가 바로 반환된다.
```kotlin
    fun parseIntOrThrowNull(str: String): Int? {
        return try {
            str.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
```

---

## 2. Checked Exception과 Unchecked Exception

Java와 달리, Kotlin은 **Checked Exception을 구분하지 않는다.**

* **Java 예시**
  `IOException`은 Checked Exception이므로, 메서드 시그니처에 `throws`를 명시하거나 `try-catch`로 감싸야 한다.
```java
    public void readFile() throws IOException {
        File currentFile = new File(".");
        File file = new File(currentFile.getAbsolutePath() + "/a.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        System.out.println(reader.readLine());
        reader.close();
    }
```
* **Kotlin 예시**
  Kotlin에서는 모든 예외를 Unchecked Exception으로 간주하므로, `throws` 구문이 필요 없다.
```kotlin
    fun readFile() {
        val currentFile = File(".")
        val file = File(currentFile.absolutePath + "/a.txt")
        val reader = BufferedReader(FileReader(file))
        println(reader.readLine())
        reader.close()
    }
```

---

## 3. try-with-resources

Kotlin에는 Java의 `try-with-resources`와 정확히 일치하는 구문은 없지만, `.use`라는 확장 함수를 통해 더 간결하게 구현 가능하다.

* **Java 예시**
  `try()` 괄호 안의 자원은 자동으로 `close()` 된다.
```java
    public void readFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            System.out.println(br.readLine());
        }
    }
```

* **Kotlin 예시**
  `Closeable`을 구현한 객체에서 `.use` 블록을 사용하면, 블록이 끝날 때 자원이 자동으로 `close()` 된다.
```kotlin
    fun readFile(path: String) {
        BufferedReader(FileReader(path)).use { reader ->
            println(reader.readLine())
        }
    }
```

---

## ✨ 최종 정리

1.  `try-catch-finally` 구문은 문법적으로 Java와 **완전히 동일**하며, Kotlin에서는 `try-catch`가 **Expression**으로 취급되어 값을 반환할 수 있다.
2.  Kotlin의 모든 예외는 **Unchecked Exception**입니다. (Checked Exception을 강제하지 않는다.)
3.  Kotlin에는 `try-with-resources` 구문이 없는 대신, `.use`라는 **inline 확장 함수**를 사용하여 리소스의 자동 `close`를 처리한다.