package com.cuizhanming.oop.structural;

/**
 * Bridge Pattern - Separates abstraction from implementation
 * 桥接模式 - 将抽象与实现分离
 */
public class BridgePattern {

    // Implementation interface
    public interface DrawingAPI {
        void drawCircle(double x, double y, double radius);
        void drawRectangle(double x, double y, double width, double height);
    }

    // Concrete implementations
    public static class DrawingAPI1 implements DrawingAPI {
        @Override
        public void drawCircle(double x, double y, double radius) {
            System.out.printf("API1: Drawing Circle at (%.1f, %.1f) with radius %.1f%n", x, y, radius);
        }

        @Override
        public void drawRectangle(double x, double y, double width, double height) {
            System.out.printf("API1: Drawing Rectangle at (%.1f, %.1f) with size %.1f x %.1f%n", x, y, width, height);
        }
    }

    public static class DrawingAPI2 implements DrawingAPI {
        @Override
        public void drawCircle(double x, double y, double radius) {
            System.out.printf("API2: Circle[center=(%.1f, %.1f), radius=%.1f]%n", x, y, radius);
        }

        @Override
        public void drawRectangle(double x, double y, double width, double height) {
            System.out.printf("API2: Rectangle[origin=(%.1f, %.1f), dimensions=%.1fx%.1f]%n", x, y, width, height);
        }
    }

    // Abstraction
    public abstract static class Shape {
        protected final DrawingAPI drawingAPI;

        protected Shape(DrawingAPI drawingAPI) {
            this.drawingAPI = drawingAPI;
        }

        public abstract void draw();
        public abstract void resizeByPercentage(double percentage);
    }

    // Refined abstractions
    public static class Circle extends Shape {
        private double x, y, radius;

        public Circle(double x, double y, double radius, DrawingAPI drawingAPI) {
            super(drawingAPI);
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        @Override
        public void draw() {
            drawingAPI.drawCircle(x, y, radius);
        }

        @Override
        public void resizeByPercentage(double percentage) {
            radius *= (1.0 + percentage / 100.0);
        }
    }

    public static class Rectangle extends Shape {
        private double x, y, width, height;

        public Rectangle(double x, double y, double width, double height, DrawingAPI drawingAPI) {
            super(drawingAPI);
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public void draw() {
            drawingAPI.drawRectangle(x, y, width, height);
        }

        @Override
        public void resizeByPercentage(double percentage) {
            width *= (1.0 + percentage / 100.0);
            height *= (1.0 + percentage / 100.0);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Bridge Pattern Demo ===");

        // Create shapes with different drawing APIs
        Shape[] shapes = {
            new Circle(10, 20, 5, new DrawingAPI1()),
            new Circle(15, 25, 8, new DrawingAPI2()),
            new Rectangle(5, 10, 20, 15, new DrawingAPI1()),
            new Rectangle(8, 12, 25, 18, new DrawingAPI2())
        };

        // Draw all shapes
        System.out.println("Drawing shapes:");
        for (Shape shape : shapes) {
            shape.draw();
        }

        // Resize and redraw
        System.out.println("\nAfter resizing by 50%:");
        for (Shape shape : shapes) {
            shape.resizeByPercentage(50);
            shape.draw();
        }
    }
}
