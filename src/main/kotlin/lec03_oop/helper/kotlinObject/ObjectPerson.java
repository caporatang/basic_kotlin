package lec03_oop.helper.kotlinObject;

public class ObjectPerson {
    private static final int MIN_AGE = 1;

    public static ObjectPerson newBaby(String name) {
        return new ObjectPerson(name, MIN_AGE);
    }

    private String name;
    private int age;

    public ObjectPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}