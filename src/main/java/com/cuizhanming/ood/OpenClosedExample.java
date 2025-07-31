package com.cuizhanming.ood;

// Open/Closed Principle Example
// Modern Java (17+)

import java.util.List;

public class OpenClosedExample {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(2.0), new Rectangle(3.0, 4.0));
        AreaCalculator calculator = new AreaCalculator();
        shapes.forEach(shape -> System.out.println("Area: " + calculator.calculateArea(shape)));
    }
}

sealed interface Shape permits Circle, Rectangle {
    double area();
}

final class Circle implements Shape {
    private final double radius;
    public Circle(double radius) { this.radius = radius; }
    @Override public double area() { return Math.PI * radius * radius; }
}

final class Rectangle implements Shape {
    private final double width, height;
    public Rectangle(double width, double height) { this.width = width; this.height = height; }
    @Override public double area() { return width * height; }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.area();
    }
}

// AreaCalculator is open for extension (new shapes) but closed for modification.
