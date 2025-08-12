package lec03_oop.helper.kotlinObject;

public interface Movable {
    void move();
    void fly();

    private static void moveSomething(Movable movable) {
        movable.move();
        movable.fly();
    }

    public static void main(String[] args) {
        moveSomething(new Movable() {
            @Override
            public void move() {
                System.out.println("move~");
            }

            @Override
            public void fly() {
                System.out.println("fly ~ ");
            }
        });
    }
}




