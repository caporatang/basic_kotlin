package lec01_variable.helper;

public class VariableBasicTypePerson {
    public static void main(String[] args) { }


    public static void printAgeIfPerson(Object obj) {
        if (obj instanceof CommonPerson) {
            CommonPerson person = (CommonPerson) obj;
            System.out.println(person.getName());
        }
    }
}