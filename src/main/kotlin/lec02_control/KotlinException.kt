package org.example.lec02_control

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


// 코틀린에서 예외 다루기
// 1. try catch finally 구문
// 2. Checked Exception과 Unchecked Exception
// 3. try with resources
class KotlinException {
    fun main() {
        // 1. try catch finally
        // java 예시
        //private int parseIntOrThrow(@NotNull String str) {
        //    try {
        //        return Integer.parseInt(str);
        //    } catch (NumberFormatException e) {
        //        throw new IllegalArgumentException(String.format("주어진 %s는 숫자가 아닙니다", str));
        //    }
        //}

        // Java와 kotlin은 try catch finally 문법은 동일하다.
        // kotlin 예시
        fun parseIntOrThrow(str: String) : Int {
            try {
                return str.toInt()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("주어진 ${str}은 숫자가 아닙니다.")
            }
        }
        // java와 다른점은 기본타입간의 형변환은 toType()을 사용한다.
        // 그리고 new를 사용하지 않고 포맷팅이 간결하다.

        // 또 다른 예제, Exception이 발생하게 되면 null을 반환하는 예제다.
        //private Integer parseIntOrThrowNull(@NotNull String str) {
        //    try {
        //        return Integer.parseInt(str);
        //    } catch (NumberFormatException e) {
        //        return null;
        //}

        fun parseIntOrThrowNull(str: String) : Int? {
            return try {
                str.toInt()
            } catch (e: NumberFormatException) {
                null
            }
        }
        // 코틀린에서는 try catch도 if-else 조건문들 처럼 하나의 Expression으로 간주된다.
        // 즉, 바로 return을 사용해서 한번만 return을 사용해도 된다.

        // 2. Checked Exception과 Unchecked Exception
        // java 예시
        //public void readFile() throws IOException {
        //    File currentFile = new File(".");
        //    File file = new File(currentFile.getAbsolutePath() + "/a.txt");
        //    BufferedReader reader = new BufferedReader(new FileReader(file));
        //    System.out.println(reader.readLine());
        //    reader.close();
        //}

        // java에서는 IoException은 CheckedException이라서 반드시 throws 를 해주어야 한다.
        // 이를 kotlin으로 변경하면
        fun readFile() {
            val currentFile = File(".")
            val file = File(currentFile.absolutePath + "/a.txt")
            val reader = BufferedReader(FileReader(file))
            println(reader.readLine())
            reader.close()
        }
        // kotlin은 throws가 없다. 따로 CheckedException을 체크해주지 않아도 된다.
        // 그 이유는 Kotlin에서는 Checked Exception과 Unchecked Exception을 구분하지 않는다.
        // 모두 Unchecked Exception 으로 간주한다.

        // 3. try with resources
        // java 에서는 괄호안에 외부 자원을 만들어주고 try가 끝나면 외부 자원을 닫아준다.
        //public void readFile(String path) throws IOException {
        //    try (BufferedReader br = new BufferedReader(new FileReader(path))){
        //        System.out.println(br.readLine());
        //    }
        //}

        // kotlin에서는 try with resources 구문이 따로 없다 그래서 아래와 같이 처리한다.
        fun readFile(path: String) {
            BufferedReader(FileReader(path)).use { reader ->
                println(reader.readLine())
            }
        }
        // 대신 use 라는 inline 확장함수를 사용해서 읽어온 파일에 대한 내용물을 출력해주는 방향으로 처리해야 한다.

        // 최종 정리
        // 1. try catch finally 구문은 문법적으로 완전히 동일하다. 그리고 Kotlin에서는 try catch가 expression이다.
        // 2. Kotlin에서 모든 예외는 Unchecked Exception이다.
        // 3. Kotlin에서는 try with resources 구문이 없다. 대신 언어적 특징을 활용해 close를 따로 호출해준다.

    } // main
}