package com.cuizhanming.ood;

// Liskov Substitution Principle Example
// Modern Java (17+)

public class LiskovSubstitutionExample {
    public static void main(String[] args) {
        Bird bird = new Sparrow();
        bird.fly(); // Valid
        bird = new Ostrich();
        bird.fly(); // Valid: Ostrich cannot fly, but method is handled correctly
    }
}

sealed interface Bird permits Sparrow, Ostrich {
    void fly();
}

final class Sparrow implements Bird {
    @Override
    public void fly() {
        System.out.println("Sparrow is flying.");
    }
}

final class Ostrich implements Bird {
    @Override
    public void fly() {
        System.out.println("Ostrich cannot fly.");
    }
}

// Subtypes (Sparrow, Ostrich) can be substituted for Bird without breaking program correctness.
