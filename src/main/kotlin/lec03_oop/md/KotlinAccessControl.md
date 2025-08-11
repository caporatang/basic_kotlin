# ☕️ Kotlin의 접근 제어 다루기

## 1. Java와 Kotlin의 가시성 제어

Java와 Kotlin은 네 가지의 가시성 제어자를 가지지만, 그 의미와 기본값이 다르다.

| 접근 제어자 | Java | Kotlin |
| :--- | :--- | :--- |
| `public` | 모든 곳에서 접근 가능 | 모든 곳에서 접근 가능 |
| `protected` | **같은 패키지** 또는 하위 클래스 | 선언된 클래스 또는 **하위 클래스에서만** |
| `default` / `internal` | `default` (같은 패키지) | `internal` (**같은 모듈**에서만) |
| `private` | 선언된 클래스 내에서만 | 선언된 클래스 내에서만 |
| **기본 접근 제어자** | `default` | `public` |

> **`protected`의 차이**: Kotlin은 패키지를 네임스페이스 관리 용도로만 사용하므로, `protected`의 범위에 '같은 패키지'가 포함되지 않는다.
> **`internal`의 등장**: Kotlin은 `default` 대신 **모듈(Module)** 기준으로 접근을 제어하는 `internal`을 도입했다. 모듈은 한 번에 컴파일되는 코드 단위를 의미한다 (예: Gradle 모듈).

---

## 2. Kotlin 파일의 접근 제어 (최상단 선언)

Kotlin은 `.kt` 파일 최상단에 직접 함수, 변수, 클래스를 선언할 수 있고, 이때 사용되는 접근 제어자의 의미는 다음과 같다.

* `public`: **(기본값)** 어디서든 접근할 수 있다.
* `protected`: **파일(최상단)에는 사용할 수 없다.** (클래스 멤버 전용)
* `internal`: 같은 모듈에서만 접근할 수 있다.
* `private`: 같은 파일 내에서만 접근할 수 있다.

> 💡 **Tip**: 유틸성 함수처럼 여러 곳에서 사용되지만, 특정 클래스에 소속시키기 애매한 함수들은 파일 최상단에 `public` 함수로 만들어두면 편리하게 사용할 수 있다.

---

## 3. 다양한 구성요소의 접근 제어

### 클래스 멤버

클래스 내부에 선언된 프로퍼티, 메서드 등은 기본적인 가시성 제어(public, protected, internal, private)를 따른다.

### 생성자

생성자의 가시성을 변경하려면, `constructor` 키워드를 명시적으로 작성해야 한다.

```kotlin
class Mem internal constructor( // internal 생성자
    val name: String
) { /* ... */ }
```

### 프로퍼티 (Setter 가시성 변경)
프로퍼티의 setter에만 더 제한적인 접근 제어자를 붙일 수 있습니다.

```kotlin
class Car(
    val name: String,
    _price: Int
) {
    var price = _price
        private set // price의 setter만 private으로 설정
}

fun main() {
    val car = Car("MyCar", 1000)
    println(car.price) // getter는 public이라 가능
    // car.price = 2000 // setter는 private이라 컴파일 에러
}
```

## ✨ 최종 정리

* Kotlin에서 **`protected`**의 의미는 Java와 다르다. (패키지 범위 제외)
* Kotlin에는 Java의 `default` 대신, **모듈 단위**로 접근을 제어하는 **`internal`** 키워드가 있다.
* 생성자에 접근 지시어를 붙일 때는 **`constructor`**를 **명시적으로 작성**해야 한다.
* 유틸리티 함수 등을 만들 때 **파일 최상단**을 이용하면 편리하다.
* 프로퍼티의 `setter`에만 더 제한적인 접근 지시어를 붙일 수 있다. (`private set`).
