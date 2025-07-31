package com.cuizhanming.oop.creational;

import java.util.List;
import java.util.ArrayList;

/**
 * Builder Pattern - Constructs complex objects step by step
 * 建造者模式 - 逐步构建复杂对象
 */
public class BuilderPattern {

    // Product class
    public static class Computer {
        private final String cpu;
        private final String ram;
        private final String storage;
        private final String gpu;
        private final boolean hasWifi;
        private final boolean hasBluetooth;
        private final List<String> ports;

        private Computer(Builder builder) {
            this.cpu = builder.cpu;
            this.ram = builder.ram;
            this.storage = builder.storage;
            this.gpu = builder.gpu;
            this.hasWifi = builder.hasWifi;
            this.hasBluetooth = builder.hasBluetooth;
            this.ports = List.copyOf(builder.ports);
        }

        @Override
        public String toString() {
            return String.format("""
                Computer Configuration:
                CPU: %s
                RAM: %s
                Storage: %s
                GPU: %s
                WiFi: %s
                Bluetooth: %s
                Ports: %s
                """, cpu, ram, storage, gpu, hasWifi, hasBluetooth, ports);
        }

        // Builder class
        public static class Builder {
            private String cpu;
            private String ram;
            private String storage;
            private String gpu = "Integrated Graphics";
            private boolean hasWifi = false;
            private boolean hasBluetooth = false;
            private List<String> ports = new ArrayList<>();

            public Builder cpu(String cpu) {
                this.cpu = cpu;
                return this;
            }

            public Builder ram(String ram) {
                this.ram = ram;
                return this;
            }

            public Builder storage(String storage) {
                this.storage = storage;
                return this;
            }

            public Builder gpu(String gpu) {
                this.gpu = gpu;
                return this;
            }

            public Builder withWifi() {
                this.hasWifi = true;
                return this;
            }

            public Builder withBluetooth() {
                this.hasBluetooth = true;
                return this;
            }

            public Builder addPort(String port) {
                this.ports.add(port);
                return this;
            }

            public Computer build() {
                if (cpu == null || ram == null || storage == null) {
                    throw new IllegalStateException("CPU, RAM, and Storage are required");
                }
                return new Computer(this);
            }
        }
    }

    // Director class (optional) - knows how to build specific configurations
    public static class ComputerDirector {
        public Computer buildGamingComputer() {
            return new Computer.Builder()
                .cpu("Intel i9-13900K")
                .ram("32GB DDR5")
                .storage("1TB NVMe SSD")
                .gpu("NVIDIA RTX 4080")
                .withWifi()
                .withBluetooth()
                .addPort("USB-C")
                .addPort("USB 3.0")
                .addPort("HDMI")
                .addPort("DisplayPort")
                .build();
        }

        public Computer buildOfficeComputer() {
            return new Computer.Builder()
                .cpu("Intel i5-13400")
                .ram("16GB DDR4")
                .storage("512GB SSD")
                .withWifi()
                .addPort("USB 3.0")
                .addPort("HDMI")
                .build();
        }

        public Computer buildBudgetComputer() {
            return new Computer.Builder()
                .cpu("AMD Ryzen 5")
                .ram("8GB DDR4")
                .storage("256GB SSD")
                .addPort("USB 3.0")
                .build();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Builder Pattern Demo ===");

        // Manual building
        Computer customComputer = new Computer.Builder()
            .cpu("Apple M2 Pro")
            .ram("16GB Unified Memory")
            .storage("512GB SSD")
            .withWifi()
            .withBluetooth()
            .addPort("Thunderbolt 4")
            .addPort("USB-C")
            .build();

        System.out.println("Custom Computer:");
        System.out.println(customComputer);

        // Using director
        ComputerDirector director = new ComputerDirector();

        Computer gamingPC = director.buildGamingComputer();
        System.out.println("Gaming Computer:");
        System.out.println(gamingPC);

        Computer officePC = director.buildOfficeComputer();
        System.out.println("Office Computer:");
        System.out.println(officePC);
    }
}
