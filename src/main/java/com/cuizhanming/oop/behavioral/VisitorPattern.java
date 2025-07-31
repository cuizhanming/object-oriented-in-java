package com.cuizhanming.oop.behavioral;

/**
 * Visitor Pattern - Defines operations on objects without changing their classes
 * 访问者模式 - 在不修改对象类的情况下定义新操作
 */
public class VisitorPattern {

    // Visitor interface
    public interface ShapeVisitor {
        void visit(Circle circle);
        void visit(Rectangle rectangle);
        void visit(Triangle triangle);
    }

    // Element interface
    public interface Shape {
        void accept(ShapeVisitor visitor);
        String getName();
    }

    // Concrete elements
    public static class Circle implements Shape {
        private final double radius;
        private final double x, y;

        public Circle(double radius, double x, double y) {
            this.radius = radius;
            this.x = x;
            this.y = y;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String getName() {
            return "Circle";
        }

        public double getRadius() { return radius; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getArea() { return Math.PI * radius * radius; }
    }

    public static class Rectangle implements Shape {
        private final double width, height;
        private final double x, y;

        public Rectangle(double width, double height, double x, double y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String getName() {
            return "Rectangle";
        }

        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getArea() { return width * height; }
    }

    public static class Triangle implements Shape {
        private final double base, height;
        private final double x, y;

        public Triangle(double base, double height, double x, double y) {
            this.base = base;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String getName() {
            return "Triangle";
        }

        public double getBase() { return base; }
        public double getHeight() { return height; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getArea() { return 0.5 * base * height; }
    }

    // Concrete visitors
    public static class AreaCalculatorVisitor implements ShapeVisitor {
        private double totalArea = 0;

        @Override
        public void visit(Circle circle) {
            double area = circle.getArea();
            totalArea += area;
            System.out.printf("Circle area: %.2f%n", area);
        }

        @Override
        public void visit(Rectangle rectangle) {
            double area = rectangle.getArea();
            totalArea += area;
            System.out.printf("Rectangle area: %.2f%n", area);
        }

        @Override
        public void visit(Triangle triangle) {
            double area = triangle.getArea();
            totalArea += area;
            System.out.printf("Triangle area: %.2f%n", area);
        }

        public double getTotalArea() { return totalArea; }
        public void reset() { totalArea = 0; }
    }

    public static class PerimeterCalculatorVisitor implements ShapeVisitor {
        private double totalPerimeter = 0;

        @Override
        public void visit(Circle circle) {
            double perimeter = 2 * Math.PI * circle.getRadius();
            totalPerimeter += perimeter;
            System.out.printf("Circle perimeter: %.2f%n", perimeter);
        }

        @Override
        public void visit(Rectangle rectangle) {
            double perimeter = 2 * (rectangle.getWidth() + rectangle.getHeight());
            totalPerimeter += perimeter;
            System.out.printf("Rectangle perimeter: %.2f%n", perimeter);
        }

        @Override
        public void visit(Triangle triangle) {
            // Assuming equilateral triangle for simplicity
            double side = triangle.getBase();
            double perimeter = 3 * side;
            totalPerimeter += perimeter;
            System.out.printf("Triangle perimeter: %.2f%n", perimeter);
        }

        public double getTotalPerimeter() { return totalPerimeter; }
        public void reset() { totalPerimeter = 0; }
    }

    public static class DrawingVisitor implements ShapeVisitor {
        @Override
        public void visit(Circle circle) {
            System.out.printf("Drawing Circle: center=(%.1f, %.1f), radius=%.1f%n",
                circle.getX(), circle.getY(), circle.getRadius());
        }

        @Override
        public void visit(Rectangle rectangle) {
            System.out.printf("Drawing Rectangle: origin=(%.1f, %.1f), size=%.1fx%.1f%n",
                rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }

        @Override
        public void visit(Triangle triangle) {
            System.out.printf("Drawing Triangle: origin=(%.1f, %.1f), base=%.1f, height=%.1f%n",
                triangle.getX(), triangle.getY(), triangle.getBase(), triangle.getHeight());
        }
    }

    // Object structure
    public static class Drawing {
        private final java.util.List<Shape> shapes = new java.util.ArrayList<>();

        public void addShape(Shape shape) {
            shapes.add(shape);
            System.out.println("Added " + shape.getName() + " to drawing");
        }

        public void removeShape(Shape shape) {
            shapes.remove(shape);
            System.out.println("Removed " + shape.getName() + " from drawing");
        }

        public void accept(ShapeVisitor visitor) {
            for (Shape shape : shapes) {
                shape.accept(visitor);
            }
        }

        public int getShapeCount() { return shapes.size(); }
    }

    public static void main(String[] args) {
        System.out.println("=== Visitor Pattern Demo ===");

        Drawing drawing = new Drawing();

        // Create shapes
        drawing.addShape(new Circle(5.0, 10.0, 15.0));
        drawing.addShape(new Rectangle(4.0, 6.0, 20.0, 25.0));
        drawing.addShape(new Triangle(8.0, 6.0, 30.0, 35.0));

        System.out.println("\nDrawing contains " + drawing.getShapeCount() + " shapes\n");

        // Calculate areas
        System.out.println("--- Area Calculation ---");
        AreaCalculatorVisitor areaVisitor = new AreaCalculatorVisitor();
        drawing.accept(areaVisitor);
        System.out.printf("Total area: %.2f%n", areaVisitor.getTotalArea());

        // Calculate perimeters
        System.out.println("\n--- Perimeter Calculation ---");
        PerimeterCalculatorVisitor perimeterVisitor = new PerimeterCalculatorVisitor();
        drawing.accept(perimeterVisitor);
        System.out.printf("Total perimeter: %.2f%n", perimeterVisitor.getTotalPerimeter());

        // Draw shapes
        System.out.println("\n--- Drawing Shapes ---");
        DrawingVisitor drawingVisitor = new DrawingVisitor();
        drawing.accept(drawingVisitor);
    }
}
