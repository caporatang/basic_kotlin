package lec03_oop.helper.variousClass;

public enum JavaCountry {

    KOREA("KO"),
    AMERICA("US");
    private final String code;

    JavaCountry(String code) {this.code = code;}

    public String getCode() {return code;}

    private static void handleCountry(JavaCountry country) {
        if (country == JavaCountry.KOREA) { /* do something .. */ }
        if (country == JavaCountry.AMERICA) { /* do something .. */ }
    }
}