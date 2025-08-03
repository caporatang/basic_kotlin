package lec01_variable_type.helper;

import org.jetbrains.annotations.NotNull;

public class JavaMoney implements Comparable<JavaMoney> {

    private final long amount;

    public JavaMoney(long amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(@NotNull JavaMoney o) {
        return Long.compare(this.amount, o.amount);
    }

    public JavaMoney plus(JavaMoney other) {
        return new JavaMoney(this.amount + other.amount);
    }

    public static void main(String[] args) {
        JavaMoney money1 = new JavaMoney(2_000L);
        JavaMoney money2 = new JavaMoney(1_000L);

        if (money1.compareTo(money2) > 0) {
            System.out.println("money1의 금액이 money2 보다 금액이 커요");
        }

        JavaMoney money3 = new JavaMoney(1_000L);
        JavaMoney money4 = new JavaMoney(2_000L);
        System.out.println(money3.plus(money4));

    }



}