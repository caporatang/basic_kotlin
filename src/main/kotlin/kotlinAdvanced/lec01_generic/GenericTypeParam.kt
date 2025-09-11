package org.example.kotlinAdvanced.lec01_generic


// 제네릭과 타입 파라미터
// 1. Cage 클래스
class GenericTypeParam {
    // Cage 클래스는 동물을 넣거나 꺼낼 수 있는 클래스다.
    // getFirst(): Animal - 첫 번째 동물을 가져온다.
    // put(animal: Animal) - 동물을 넣는다
    // moveFrom(cage:cage) - 다른 Cage에 있는 동물을 모두 가져온다.

    abstract class Animal(
        val nmae: String
    )

    abstract class Fish(name: String) : Animal(name)

    class GoldFish(name: String) : Fish(name)

    class Carp(name: String) : Fish(name)

    class Cage{
        val animals: MutableList<Animal> = mutableListOf()

        fun getFirst(): Animal {
            return animals.first()
        }

        fun put(animal: Animal) {
            this.animals.add(animal)
        }

        fun moveFrom(cage: Cage) {
            this.animals.addAll(cage.animals)
        }
    }

    fun main() {
        // cage에 잉어를 넣었다 빼보자
        val cage = Cage()
        cage.put(Carp("잉어"))

        // 넣은 잉어를 다시 뺼려고 하는데, 잉어를 넣었음에도 잉어 타입으로 빼려고 하면 Type Missmatch 에러가 발생한다
        // 그 이유는 어떻게 보면 당연한데, cage에는 잉어만 있긴하지만 getFirst를 호출하면 Animal 타입이 반환되기 때문이다.
        // val carp: Carp = cage.getFirst()

        // 가장 간단한 방법은 Type Casting으로 빼내는것이다.
        // 하지만 아래와 같은 코드는 위험할 수 있다. 팀원 중 누군가가 잉어가 아니라 금붕어를 넣게된다면 당연히 원하는 결과가 반환되지 않는다.
        // 그리고 금붕어를 넣고 잉어로 타입 캐스팅을 하는건 컴파일이 아니라 런타임이 되어서야 에러를 찾을 수 있기 떄문에 더욱 위험하다고 할 수 있다.
        val carp: Carp = cage.getFirst() as Carp

        // 어떻게 안전하게 잉어를 가져올 수 있을까
        // Safe Type Casting 과 Elvis 연산자를 사용하는 방법이다.
        val carpSafeCasting: Carp = cage.getFirst() as? Carp ?: throw  IllegalArgumentException() // 형변환이 실패된다면 null이 반환된다.

        // 하지만 바로 위와같은 상황에도 동일하게 실수로 다른 동물을 넣으면 Exception은 발생한다.

        // 동일한 Cage 클래스이지만 잉어만 넣을 수 있는 Cage, 금붕어만 넣을 수 있는 Cage를 구분할 수 있게 해주는게 Generic이다.
        // 클래스에 타입 파라미터를 추가할 때는 클래스 뒤에 <타입> 을 적어준다.

        // T가 타입 파라미터이고, 타입 파라미터를 가지고 있는 클래스를 제네릭 클래스라한다.
        class Cage2<T> {
            private val animals: MutableList<T> = mutableListOf()

            fun getFirst(): T {
                return animals.first()
            }

            fun put(animal: T) {
                this.animals.add(animal)
            }

            fun moveFrom(cage: Cage2<T>) {
                this.animals.addAll(cage.animals)
            }
        }

        // 제네릭 클래스를 사용하면 타입 캐스팅 같은 과정없이 바로 뺼 수 있다.
        val cageGeneric = Cage2<Carp>()
        cageGeneric.put(Carp("잉어"))
        val carpGeneric: Carp = cageGeneric.getFirst()

        // 실제로 List와 같은 Collection 역시 제네릭을 많이 사용하고 있다.

        // 금붕어 Cage에 금붕어를 한 마리 넣고, 물고기 Cage에 금붕어롤 옮긴다면
        val goldFishCage = Cage2<GoldFish>()
        goldFishCage.put(GoldFish("금붕어"))

        val fishCage = Cage2<Fish>()
        // 아래와 같이 금붕어 케이지에 있는 물고기를 물고기 케이지로 옮기려고 하면 에러가 발생한다.
        // 금붕어를 물고기 케이지에 넣는것은 가능하다. 그리고 금붕어는 물고기의 하위 타입인데, 왜 옮길려고 할때만 에러가 발생할까
        fishCage.moveFrom(goleFishCage)

        // 이 에러가 발생하는 이유를 알려면 상속관계에 대한 의미를 살펴볼 필요가 있다.
        // 상위 클래스로 선언된 함수 매개변수가 있다면, 하위 클래스는 상위 클래스 대신 매개변수에 들어갈 수 있다.
        fun doSomething(num: Number){

        }
        val a: Int = 3
        doSomething(a)
        // 상위 타입의 변수에 하위 타입이 들어가는것도 가능하다
        val intNum: Int = 5
        val num: Number = intNum

        // 두가지 케이스를 봤을떄 상속 관계는 상위 타입이 들어가는 자리에 하위 타입이 대신 위치할 수 있다는 의미가 된다.
        // 이 의미를 생각하고 다시 금붕어를 물고기 케이지로 옮기는 것을 보면
        fishCage.moveFrom(goleFishCage)

        // T에 Fish가 들어간 물고기 케이지는 moveFrom(otherCage: Cage2<Fish>) 타입이다.
        // 그런데 우리가 넣으려고 했던 goldFishCage는 Cage2<GoldFish> 타입이다.
        // 금붕어 케이지에 있는 금붕어를 꺼내서 물고기 케이지로 옮기는 동작을 하려고 했지만, Cage2<Fish>에 Cage2<GoldFish> 를 넣으려고 한것이다.
        // Fish가 상위고 GoldFish가 하위가 아니라, 아무 관계도 아니기 떄문에 에러가 발생한것이다.
        // 이런것을 무공변 혹은 불공변하다. 라고 칭한다.

        // 타입 파라미터에 집어넣은 타입끼리의 상속 관계는 제네릭 클래스까지 유지되지 않는다.
        // 왜 제네릭 클래스는 타입 파라미터 간의 상속관계가 있더라도 무공변할까..?

        // 이를 이해하기 위해서는 Java의 배열과 List를 생각해보면 된다.
        // Java의 배열은 제네릭과 다르다. A 객체가 B 객체의 하위 타입이면, A배열이 B 배열의 하위 타입으로 간주된다.
        // 이런 상속 관계가 유지되는 것을 '공변' 이라고 한다.

        // Java 배열은 공변하므로 이런 코드가 가능하다.
        String[] strs = new String[]{"A", "B", "C"};
        Object[] objs = strs;
        // String[]은 Object[]의 하위 타입이니 objs에 strs를 넣었다.
        objs[0] = 1; // 이것도 가능하지만, 컴파일에는 에러가 발생 안되지만 런타임에 에러가 발생한다. 즉, 위험한 코드가 된다.

        // List는 제네릭을 사용하기 때문에 공변인 Array와 다르게 무공변하다.
        List<String> strs2 = List.of("A", "B", "C");
        List<Object> objs2 = strs; // 컴파일 에러 발생

        // 배열보다는 리스트를 사용이 권장되는 이유중에 하나다.

        // 물고기 케이지에 금붕어를 옮기는 문제로 돌아와서 어떻게 해야 동작시킬수있을까
        // moveFrom 함수를 호출할 때 Cage2<Fish> 와 Cage2<GoldFish> 사이의 관계를 만들어주면 동작한다.
        // 그러니까, Cage2<Fish> 가 Cage2<GoldFish> 의 상위 타입이면, Cage2<Fish> 자리에 Cage2<GoldFish> 가 들어갈 수 있게 되는것이다.
        fishCage.moveFrom(goleFishCage)


    }


}