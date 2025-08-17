# 코틀린의 다양한 클래스 정리 (Data, Enum, Sealed)

코틀린은 데이터 처리, 상태 관리 등을 효율적으로 다루기 위해 특별한 목적을 가진 클래스들을 제공한다.


## 1. Data 클래스

**`data` 클래스**는 계층 간 데이터 전달을 목적으로 하는 DTO(Data Transfer Object)를 쉽게 만들기 위해 사용된다. `data` 키워드를 붙이면 `equals()`, `hashCode()`, `toString()` 메서드를 자동으로 생성해 준다.

### Java 예시 (DTO)

Lombok 등의 라이브러리 없이는 보일러플레이트 코드가 길어진다..

```java
public class JavaPersonDto {
    private final String name;
    private final int age;

    public JavaPersonDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        // ... 필드를 이용한 hashCode 구현
    }

    @Override
    public boolean equals(Object obj) {
        // ... 필드를 이용한 equals 구현
    }

    @Override
    public String toString() {
        // ... 필드를 이용한 toString 구현
    }
}
```

### Kotlin 예시

`data` 키워드 하나로 위의 모든 기능이 자동으로 구현된다.

```kotlin
data class PersonDto(
    val name: String,
    val age: Int
)
```
> **참고**: Java 16부터는 이와 유사한 기능을 하는 `record` 클래스가 도입되었다.

---

## 2. Enum 클래스

**`enum` 클래스**는 정해진 상수들의 집합을 나타낸다. 코틀린의 `enum`은 Java와 유사하지만, `when` 표현식과 함께 사용할 때 강력한 시너지를 발휘할 수 있다.

### Java 예시

```java
public enum JavaCountry {
    KOREA("KO"),
    AMERICA("US");

    private final String code;

    JavaCountry(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
```

### Kotlin 예시

구문은 Java와 거의 동일하다.

```kotlin
enum class Country(
    private val code: String
){
    KOREA("KO"),
    AMERICA("US");
}
```

### `when` 표현식과의 활용

`when`과 함께 사용하면, 컴파일러가 `enum`의 모든 타입을 인지하므로 불필요한 `else` 분기를 작성하지 않아도 된다. 만약 `enum`에 새로운 상수가 추가되면, IDE가 `when`문에 해당 분기를 추가하라고 경고해 주어 코드의 안정성을 높인다.

```kotlin
fun handleCountry(country: Country) {
    when (country) {
        Country.KOREA -> println("대한민국")
        Country.AMERICA -> println("미국")
        // 별도의 else 문이 필요 없음
    }
}
```

---

## 3. Sealed 클래스

**`sealed` 클래스**(봉인된 클래스)는 상속을 특정 하위 클래스들로만 제한하고 싶을 때 사용한다. 컴파일 타임에 하위 클래스의 종류를 모두 알 수 있다는 특징이 있다.

- **특징**:
    - 하위 클래스는 반드시 같은 패키지 내에 있어야 한다. (Kotlin 1.5부터는 같은 모듈)
    - `enum`과 달리, 하위 클래스는 여러 인스턴스를 가질 수 있다.

### Kotlin 예시

`HyundaiCar`는 `Avante`, `Sonata`, `Grandeur` 외의 다른 클래스로는 상속될 수 없다.

```kotlin
sealed class HyundaiCar(
    val name: String,
    val price: Long
)

class Avante : HyundaiCar("아반떼", 2000L)
class Sonata : HyundaiCar("소나타", 3000L)
class Grandeur : HyundaiCar("그랜저", 4500L)
```

### `when` 표현식과의 활용

`sealed` 클래스를 `when`과 함께 사용하면, 컴파일러가 모든 하위 타입을 알고 있으므로 `else` 없이도 모든 케이스를 안전하게 처리할 수 있다.

```kotlin
private fun handleCar(car: HyundaiCar) {
    // is 키워드를 통해 타입을 검사하고 스마트 캐스팅이 이루어짐
    when (car) {
        is Avante -> println("${car.name}는 준중형 세단입니다.")
        is Grandeur -> println("${car.name}는 대형 세단입니다.")
        is Sonata -> println("${car.name}는 중형 세단입니다.")
    }
}
```
> **참고**: Java 17에서도 유사한 기능의 `sealed` 클래스가 추가되었다.

---

## 최종 요약

1.  **`Data` 클래스**: `equals`, `hashCode`, `toString` 등을 자동으로 만들어주는 DTO에 최적화된 클래스다.
2.  **`Enum` 클래스**: 상수의 집합을 정의하며, **`when`**과 함께 사용하면 타입 안전성을 높일 수 있다.
3.  **`Sealed` 클래스**: 상속 가능한 하위 클래스를 제한하는 클래스로, `Enum`보다 유연하며 **`when`**과 함께 주로 사용된다.
