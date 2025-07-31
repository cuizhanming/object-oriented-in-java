# Object Oriented Design: SOLID Principles in Java

## Project Structure

```
object-oriented-design/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cuizhanming/
│   │   │           └── ood/
│   │   │               ├── SingleResponsibilityExample.java
│   │   │               ├── OpenClosedExample.java
│   │   │               ├── LiskovSubstitutionExample.java
│   │   │               ├── InterfaceSegregationExample.java
│   │   │               └── DependencyInversionExample.java
│   └── resources/
└── README.md
```

## Overview

The SOLID principles are five foundational guidelines for designing robust, maintainable, and scalable object-oriented software. Adhering to these principles helps developers create systems that are easier to understand, extend, and refactor.

## The SOLID Principles

### 1. Single Responsibility Principle (SRP)
A class should have only one reason to change, meaning it should have only one job or responsibility.

### 2. Open/Closed Principle (OCP)
Software entities (classes, modules, functions) should be open for extension but closed for modification.

### 3. Liskov Substitution Principle (LSP)
Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.

### 4. Interface Segregation Principle (ISP)
Clients should not be forced to depend on interfaces they do not use. Prefer several specific interfaces over a single general-purpose interface.

### 5. Dependency Inversion Principle (DIP)
High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

---

## Java Examples

Each principle is demonstrated in the corresponding Java file in `src/main/java/com/cuizhanming/ood/`:
- `SingleResponsibilityExample.java`
- `OpenClosedExample.java`
- `LiskovSubstitutionExample.java`
- `InterfaceSegregationExample.java`
- `DependencyInversionExample.java`

All examples use modern Java (17+) conventions, including records for DTOs and constructor-based dependency injection.

---

## Best Practices
- Follow SOLID principles for maintainable and scalable code.
- Use immutable data structures where possible.
- Prefer constructor injection for mandatory dependencies.
- Use DTOs for API boundaries.
- Leverage Java Streams and Optionals for clean, functional-style code.

---

Explore the example files for practical demonstrations of each principle.
