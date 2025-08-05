package org.example.lec02_control

// 코틀린에서 함수 다루기
// 1. 함수 선언 문법
// 2. default parameter
// 3. named argument (parameter)
// 4. 같은 타입의 여러 파라미터 받기 (가변인자)
class KotlinFun {
    fun main () {

        // 1. 함수 선언 문법
        // java 예시
        //public int max(int a, int b) {
        //    if (a > b) {
        //        return a;
        //    }
        //    return b;
        //}

        // kotlin에서 하나의 함수를 if-else가  Expression임을 활용해서 아래 처럼 사용해도 되지만,
        fun max(a: Int, b: Int): Int {
            return if (a > b) {
                a
            } else {
                b
            }
        }

        // 아래 처럼 중괄호를 없애고 바로 할당할 수도 있다.
        fun max2(a: Int, b: Int): Int =
            if (a > b) {
                a
            } else {
                b
            }

        // 그리고 더 간결하게 표현도 가능하다.
        // kotlin은 반환 타입의 추론이 가능하다. 아래와 같은 경우 어떤 경우가 됐던 Int 타입을 반환하기 때문에
        // 반환 타입도 생략 가능하다. 단, 타입 생략이 가능한 이유는 함수를 쓸 때 중괄호를 쓰는 대신 = 으로 바로 할당을 했기 떄문에 생략이 가능한것이다.
        // block {} 을 사용하는 경우 반환 타입이 Unit이 아니라면, 반드시 명시적으로 작성해주어야 한다.
        fun max3(a: Int, b: Int) = if (a > b) a else b

        // 2. default parameter
        // java 예시
        // 아래와 같은 함수를 계속해서 useNewLine에 값을 true로 할당해서 호출한다면 사용하는 개발자는 상당히 귀찮아질 수 있다.
        //    public void repeat(String str, int num, boolean useNewLine) {
        //    for (int i = 1; i <= num; i++) {
        //        if(useNewLine) {
        //            System.out.println(str);
        //        } else {
        //            System.out.println(str);
        //        }
        //    }
        //}

        // 그래서, java에서는 이럴때 사용 가능한게 Overloading이다. 비슷한 시그니처, 같은 이름으로 재정의해서 사용할 수 있다.
        // public void repeat(String str, int num) {
        //    repeat(str, num, true);
        //}
        //public void repeat(String str) {
        //    repeat(str, 3, true);
        //}

        // 이 오버로딩의 단점은 중복 함수 느낌도 있고, 함수를 여러개 정의해야 한다는 단점? 아닌 단점이 있다.

        // kotlin은 default parameter를 활용해서 하나의 메서드로 해결할 수 있다 .
        fun repeat(
            str: String,
            num: Int = 3, // 기본값 할당
            useNewLine: Boolean = true // 기본값 할당
        ) {
            for (i in 1..num) {
                if(useNewLine) {
                    println(str)
                } else {
                    println(str)
                }
            }
        }

        // default parameter는 외부에서 호출할때 parameter를 넣어주지 않는 경우 기본 값을 쓰겠다. 는 개념인것이다.
        // 코틀린에도 Java와 동일하게 오버로딩 기능은 있다. 필요하다면 사용하면 된다.

        // 3. named argument
        // 만약 repeat에 default parameter를 이용해서 기본값을 넣어놨는데, 다른 값으로 사용하고 싶다면 어떻게 해야할까?
        repeat("Hello world", 3, false)
        // 이렇게 사용해도 되지만, default parameter를 넣어놨는데, 굳이 명시해서 3이라는 값도 넣어주고 싶지 않다면, 함수를 호출하는 쪽에서 명시해주면 된다.
        repeat("Hello world", useNewLine = false)

        // 이렇게 호출하는 쪽에서 명시하는 기능을 이름있는 argument (parameter) 라고 한다.
        // named argument의 장점은 builder를 직접 만들지 않고 builder의 장점을 가진다.
        fun printNameAndGender(name: String, gender: String) {
            println(name)
            println(gender)
        }
        // 함수 인자를 순서에 맞지 않게 넣어서 실수하는 경우가 생기는데, named argument로 직접 지정해서 사용하면 실수를 줄일 수 있다
        printNameAndGender(name = "김태일", gender = "남자")

        // 4. 같은 타입의 여러 파라미터 받기 (가변인자)
        // java에서는 ... 을 사용해서 가변인자가 사용되는구나 라고 이해했다.
        //public static void printAll(String... strings) {
        //    for (String str : strings) System.out.println(str);
        //}

        // 사용할 때는 아래와 같이 배열을 만들거나 콤마를 이용해서 사용했다.
        //String[] array = new String[]{"A", "B", "C"};
        //printAll(array);
        //printAll("A", "B","C");

        // kotlin은 ... 대신에 vararg 키워드를 사용하고, 배열을 바로 넣는 대신 스프레드 연산자(*)를 붙여주어야 한다.
        fun printAll(vararg strings: String) {
            for (str in strings) println(str)
        }
        printAll("A", "B","C")

        val array = arrayOf("A","B","C")
        printAll(*array)

        // 최종정리
        // 1. 함수의 문법은 Java와 다르다
        // 2. 함수의 body가 하나의 값으로 간주되는 경우 block을 없앨 수도 있고, block이 없다면 반환 타입을 없앨 수도 있다.
        // 3. 함수 파라미터에 기본값을 설정해줄 수 있다.
        // 4. 함수를 호출할때 특정 파라미터를 지정해 넣어줄 수 있다.
        // 5. 가변인자에는 vararg 키워드를 사용하며, 가변인자 함수를 배열과 호출할 때는 *를 붙여주어야 한다.


    } // end main
}