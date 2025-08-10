package lec03_oop.helper.inheritance;

public interface JavaFlyable {

    default void act() {
        System.out.println("fly ~");
    }
}