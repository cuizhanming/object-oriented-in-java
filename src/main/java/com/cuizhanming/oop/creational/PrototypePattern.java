package com.cuizhanming.oop.creational;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Pattern - Creates objects by cloning existing instances
 * 原型模式 - 通过克隆现有实例来创建对象
 */
public class PrototypePattern {

    // Prototype interface
    public interface Cloneable {
        Object clone();
    }

    // Abstract prototype
    public abstract static class Shape implements Cloneable {
        protected String color;
        protected int x, y;

        public Shape() {}

        public Shape(Shape source) {
            if (source != null) {
                this.color = source.color;
                this.x = source.x;
                this.y = source.y;
            }
        }

        public abstract Shape clone();
        public abstract void draw();

        // Getters and setters
        public void setColor(String color) { this.color = color; }
        public void setPosition(int x, int y) { this.x = x; this.y = y; }
        public String getColor() { return color; }
        public int getX() { return x; }
        public int getY() { return y; }
    }

    // Concrete prototypes
    public static class Circle extends Shape {
        private int radius;

        public Circle() {}

        public Circle(Circle source) {
            super(source);
            if (source != null) {
                this.radius = source.radius;
            }
        }

        @Override
        public Shape clone() {
            return new Circle(this);
        }

        @Override
        public void draw() {
            System.out.printf("Drawing Circle: color=%s, position=(%d,%d), radius=%d%n",
                color, x, y, radius);
        }

        public void setRadius(int radius) { this.radius = radius; }
        public int getRadius() { return radius; }
    }

    public static class Rectangle extends Shape {
        private int width, height;

        public Rectangle() {}

        public Rectangle(Rectangle source) {
            super(source);
            if (source != null) {
                this.width = source.width;
                this.height = source.height;
            }
        }

        @Override
        public Shape clone() {
            return new Rectangle(this);
        }

        @Override
        public void draw() {
            System.out.printf("Drawing Rectangle: color=%s, position=(%d,%d), size=%dx%d%n",
                color, x, y, width, height);
        }

        public void setSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }

    // Prototype registry
    public static class ShapeRegistry {
        private final Map<String, Shape> prototypes = new HashMap<>();

        public void registerPrototype(String key, Shape prototype) {
            prototypes.put(key, prototype);
        }

        public Shape createShape(String key) {
            Shape prototype = prototypes.get(key);
            if (prototype != null) {
                return prototype.clone();
            }
            throw new IllegalArgumentException("Prototype not found: " + key);
        }

        public void listPrototypes() {
            System.out.println("Available prototypes: " + prototypes.keySet());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Prototype Pattern Demo ===");

        // Create prototype registry
        ShapeRegistry registry = new ShapeRegistry();

        // Create and register prototypes
        Circle blueCircle = new Circle();
        blueCircle.setColor("Blue");
        blueCircle.setPosition(10, 20);
        blueCircle.setRadius(15);
        registry.registerPrototype("blue-circle", blueCircle);

        Rectangle redRectangle = new Rectangle();
        redRectangle.setColor("Red");
        redRectangle.setPosition(30, 40);
        redRectangle.setSize(100, 50);
        registry.registerPrototype("red-rectangle", redRectangle);

        registry.listPrototypes();
        System.out.println();

        // Clone prototypes and modify
        Circle clonedCircle1 = (Circle) registry.createShape("blue-circle");
        clonedCircle1.setPosition(50, 60);
        clonedCircle1.setRadius(25);

        Circle clonedCircle2 = (Circle) registry.createShape("blue-circle");
        clonedCircle2.setColor("Green");
        clonedCircle2.setPosition(70, 80);

        Rectangle clonedRectangle = (Rectangle) registry.createShape("red-rectangle");
        clonedRectangle.setColor("Yellow");
        clonedRectangle.setSize(150, 75);

        // Draw all shapes
        System.out.println("Original prototypes:");
        blueCircle.draw();
        redRectangle.draw();

        System.out.println("\nCloned and modified shapes:");
        clonedCircle1.draw();
        clonedCircle2.draw();
        clonedRectangle.draw();
    }
}
