package lec04_FunctionalProgramming.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaArray {

    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(100, 200);
        int[] array = {100, 200};
        for (int i = 0; i <array.length; i++) {
            System.out.printf("%s %s", i, array[i]);
        }

        // 하나 가져오기
        System.out.println(numbers.get(0));

        // For Each
        for (int number : numbers ) {
            System.out.println(number);
        }

        // 전통적인 for문
        for (int i = 0; i < numbers.size(); i++) {
            System.out.printf("%s %s", i, numbers.get(i));
        }

        // JDK8까지
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "asd");
        map.put(2, "qwe");

        // JDK9 부터
        Map.of(1, "asd", 2, "qwe");


        for (int key : map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}