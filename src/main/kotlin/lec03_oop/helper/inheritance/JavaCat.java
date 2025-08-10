package lec03_oop.helper.inheritance;

public class JavaCat extends JavaAnimal{

    public JavaCat(String species, int legCount) {
        super(species, 4);
    }

    @Override
    public void move() {
        System.out.println("고양이가 걷는다");
    }
}