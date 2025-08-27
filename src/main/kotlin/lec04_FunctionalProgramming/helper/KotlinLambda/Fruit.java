package lec04_FunctionalProgramming.helper.KotlinLambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Fruit {
    private final String name;
    private final int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name;}

    public int getPrice() {
        return price;
    }


    public static void main(String[] args) {
        List<Fruit> fruits = Arrays.asList(
                new Fruit("사과", 1_000),
                new Fruit("사과", 1_200),
                new Fruit("사과", 1_200),
                new Fruit("사과", 1_500),
                new Fruit("바나나", 3_000),
                new Fruit("바나나", 3_000),
                new Fruit("바나나", 2_500),
                new Fruit("수박", 1_0000)
        );

        String targetFruitName = "바나나";
        targetFruitName = "수박";
        filterFruits(fruits, (fruit) -> targetFruitName.equals(fruit.getName()));
    }

    private List<Fruit> filterFruits(List<Fruit> fruits, Predicate<Fruit> filter) {
        List<Fruit> results = new ArrayList<>();
        for(Fruit fruit : fruits) {
            if (filter.test(fruit)) {
                results.add(fruit);
            }
        }
        return results;
    }

}