package lec03_oop.helper.inheritance;

public interface JavaSwimable {

    default void act() {
        System.out.println("수영 ing......~");
    }

}