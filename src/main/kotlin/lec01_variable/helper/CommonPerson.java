package lec01_variable.helper;

import org.jetbrains.annotations.Nullable;

public class CommonPerson {
    private final String name;

    public CommonPerson(String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

}