package com.cuizhanming.oop.structural;

/**
 * Decorator Pattern - Adds behavior to objects dynamically
 * 装饰器模式 - 动态地为对象添加行为
 */
public class DecoratorPattern {

    // Component interface
    public interface Coffee {
        String getDescription();
        double getCost();
    }

    // Concrete component
    public static class SimpleCoffee implements Coffee {
        @Override
        public String getDescription() {
            return "Simple coffee";
        }

        @Override
        public double getCost() {
            return 2.00;
        }
    }

    // Base decorator
    public abstract static class CoffeeDecorator implements Coffee {
        protected final Coffee coffee;

        public CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }

        @Override
        public String getDescription() {
            return coffee.getDescription();
        }

        @Override
        public double getCost() {
            return coffee.getCost();
        }
    }

    // Concrete decorators
    public static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", milk";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 0.50;
        }
    }

    public static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", sugar";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 0.25;
        }
    }

    public static class VanillaDecorator extends CoffeeDecorator {
        public VanillaDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", vanilla";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 0.75;
        }
    }

    public static class WhippedCreamDecorator extends CoffeeDecorator {
        public WhippedCreamDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", whipped cream";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 1.00;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Decorator Pattern Demo ===");

        // Simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add milk
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add sugar
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add vanilla
        coffee = new VanillaDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add whipped cream
        coffee = new WhippedCreamDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        System.out.println("\n--- Creating another coffee ---");

        // Create a different combination
        Coffee specialCoffee = new WhippedCreamDecorator(
            new VanillaDecorator(
                new MilkDecorator(
                    new SimpleCoffee()
                )
            )
        );

        System.out.println(specialCoffee.getDescription() + " - $" + specialCoffee.getCost());
    }
}
