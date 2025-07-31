package com.cuizhanming.oop.creational;

/**
 * Factory Method Pattern - Creates objects without specifying exact classes
 * 工厂方法模式 - 在不指定确切类的情况下创建对象
 */
public class FactoryMethodPattern {

    // Product interface
    public interface Vehicle {
        void start();
        void stop();
        String getType();
    }

    // Concrete products
    public static class Car implements Vehicle {
        @Override
        public void start() {
            System.out.println("Car engine started");
        }

        @Override
        public void stop() {
            System.out.println("Car engine stopped");
        }

        @Override
        public String getType() {
            return "Car";
        }
    }

    public static class Motorcycle implements Vehicle {
        @Override
        public void start() {
            System.out.println("Motorcycle engine started");
        }

        @Override
        public void stop() {
            System.out.println("Motorcycle engine stopped");
        }

        @Override
        public String getType() {
            return "Motorcycle";
        }
    }

    public static class Truck implements Vehicle {
        @Override
        public void start() {
            System.out.println("Truck engine started");
        }

        @Override
        public void stop() {
            System.out.println("Truck engine stopped");
        }

        @Override
        public String getType() {
            return "Truck";
        }
    }

    // Creator abstract class
    public abstract static class VehicleFactory {
        public abstract Vehicle createVehicle();

        // Template method that uses the factory method
        public void operateVehicle() {
            Vehicle vehicle = createVehicle();
            vehicle.start();
            System.out.println("Operating " + vehicle.getType());
            vehicle.stop();
        }
    }

    // Concrete creators
    public static class CarFactory extends VehicleFactory {
        @Override
        public Vehicle createVehicle() {
            return new Car();
        }
    }

    public static class MotorcycleFactory extends VehicleFactory {
        @Override
        public Vehicle createVehicle() {
            return new Motorcycle();
        }
    }

    public static class TruckFactory extends VehicleFactory {
        @Override
        public Vehicle createVehicle() {
            return new Truck();
        }
    }

    public static void main(String[] args) {
        VehicleFactory carFactory = new CarFactory();
        VehicleFactory motorcycleFactory = new MotorcycleFactory();
        VehicleFactory truckFactory = new TruckFactory();

        System.out.println("=== Factory Method Pattern Demo ===");
        carFactory.operateVehicle();
        System.out.println();
        motorcycleFactory.operateVehicle();
        System.out.println();
        truckFactory.operateVehicle();
    }
}
