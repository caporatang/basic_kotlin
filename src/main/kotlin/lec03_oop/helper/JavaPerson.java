package lec03_oop.helper;

public class JavaPerson {
    private final String name;
    private int age;

    public JavaPerson(String name, int age) {
        if (age <= 0) {
            throw new IllegalArgumentException(String.format("나이는 %s일 수 없습니다."));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public JavaPerson(String name) {
        this(name, 1);
    }

    public boolean isAudult() {
        return this.age >= 20;
    }

}