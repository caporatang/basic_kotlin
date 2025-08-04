package org.example.lec02_control


// 코틀린에서 조건문 다루기
// 1. if문
// 2. Expression과 Statement
// 3. switch와 when
class ControlStatement {
    // 1. if문
    // java 예시
    // private void validateScoreIsNotNegative(int score) {
    //    if (score < 0) {
    //        throw new IllegalArgumentException(String.format("%s는 0보다 작을 수 없습니다.", score));
    //    }
    //}
    // kotlin 예시
    fun validateScoreIsNotNegative(score: Int) {
        if (score < 0) {
            throw IllegalArgumentException("${score}는 0보다 작을 수 없습니다.")
        }
    }
    // kotlin으로 변경한 코드를 보면, void는 Unit인데 Unit이 생략됐다. 그리고 Excpetion을 던질때 new 를 생략한다. 나머지는 java와 동일하다.

    // else도 있다면?
    // java 예시
    // private String getPassOrFail(int score) {
    //    if(score >= 50) {
    //        return "P";
    //    } else {
    //        return "F";
    //    }
    //}
    // kotlin 예시
    fun getPassOrFail(score: Int): String {
        return if (score >= 50) {
            "P"
        } else {
            "F"
        }
    }

    // 2. 사용하는 방법은 똑같지만 차이점이 있다.
    // Java에서 if-else는 Statement 지만, Kotlin에서는 Expression 이다.
    // Statement : 프로그램의 하나의 문장, 하나의 값으로 도출되지 않는다.
    // Expression : 하나의 값으로 도출되는 문장
    // 즉, Statement 중에 하나의 값으로 도출되는 문장들이 Expression이다.
    // 예를 들어 int score = 10 + 20; 은 30이라는 하나의 결과가 나온다. 이는 Expression (하나의 값으로 도출되는 문장) 이면서 Statement(하나의 문장이다) 이다.

    // Java에서는 if문을 변수에 할당해서 사용하려고 하면 에러가 발생한다. 즉, 아래의 문장은 Statement다.
    // String grade = if(score >= 50) {
    //        return "P";
    //    } else {
    //        return "F";
    //    }
    // java에서는 위와 같은 상황에 사용하기 위해 3항 연산자를 제공한다. 3항 연산자는 Expression 이면서 Statement이다.
    // String grade = score >= 50 ? "P" : "F";

    // kotlin에서 if-else는 java와 다르게 Expression이라고 했다. 이는 if-else 문 자체를 return 할 수 있다는 뜻이며, 아래처럼 중복되는 return 또한 없앨 수 있다.
    // 이러한 이유로 코틀린에서는 3항 연산자를 제공하지 않는다.
    fun getPassOrFail2(score: Int): String {
        return if (score >= 50) {
            "P"
        } else {
            "F"
        }
    }

    // 추가적으로 어떠한 값이 특정 범위에 포함되어 있는지, 아닌지를 판단할 때
    // java 에서는 이렇게 하지만
    // if (0 <= score && score <= 100) {

    // kotlin도 java에서 처럼 판단할 수 있지만 in 과 .. 연산을 사용하면 훨씬 깔끔하게 사용할 수 있다.
    // if (score in 0..100)
    fun boxNotBox(score: Int) {
        if (score !in 0..100) throw IllegalArgumentException("score의 범위는 0부터 100임")
    }

    fun validateScore(score: Int) {
        if (score in 0..100) {
            println("0과 100사이 어디인가~ 있다~")
        }
    }

    // 3. switch 와 when
    // java 예시
    //private String getGradeWithSwitch(int score) {
    //    switch (score / 10) {
    //        case 9:
    //            return "A";
    //        case 8:
    //            return "B";
    //        case 7:
    //            return "C";
    //        default 6:
    //            return "D";
    //
    //    }
    //}

    // kotlin은 switch case 문이 사라졌다. 대신 when을 제공한다.
    fun getGradeWithSwitch(score: Int): String {
        return when (score / 10) {
            9 -> "A"
            8 -> "B"
            7 -> "C"
            else -> "D"
        }
    }

    // 추가적으로 kotlin의 when은 다양한 방법으로 사용될 수 있다.
    fun getGradeWithSwitch2(score: Int): String {
        return when (score) {
            in 90..99 -> "A"
            in 80..89 -> "B"
            in 70..79 -> "C"
            else -> "D"
        }
    }

    // kotlin의 구문을 정리하면 이렇다.
    // when (값) {
    // 조건부 -> 어떤~ 구문 ~
    // 조건부 -> 어떤~ 구문 ~
    // else -> 어떤~ 구문 ~
    // }
    // 이때 조건부 에는 어떠한 expression이라도 들어갈 수 있다. in 이라던지 is 라던지..

    // 그리고 조건부에서 여러개의 조건을 동시에 검사할 수 있다.
    fun judgeNumber(number: Int) {
        when (number) {
            1, 0, -1 -> println("어디서 많이 본 숫자입니다.")
            else -> println("1, 0, -1이 아닙니다")
        }
    }

    // when에 값이 없이도 사용할 수 있다. java로 생각하면 early return과 유사하게 사용할 수 있다
    fun judgeNumber2(number: Int) {
        when {
            number == 0 -> println("주어진 숫자는 0")
            number % 2 == 0 -> println("주어진 숫자는 짝수")
            else -> println("주어진 숫자는 홀수입니다.")
        }
    }

    // 정리
    // 1. if / if-else / if-else if-else 모두 Java와 문법이 동일하다.
    // 2. 단 Kotlin에서는 Expression으로 취급된다. -> kotlin에는 삼항 연산자가 없다.
    // 3. Java의 switch는 Kotlin에서 when으로 대체되었고, when은 더 유연하게 사용할 수 있다.

}


