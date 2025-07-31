# Object Oriented Design: SOLID Principles and Design Patterns in Java

## 项目概述 | Project Overview

**English**: This project demonstrates fundamental object-oriented design principles and patterns in Java. It covers the SOLID principles for robust software design and implements all 23 Gang of Four (GoF) design patterns categorized into behavioral, creational, and structural patterns.

**中文**: 本项目演示了Java中面向对象设计的基本原则和模式。涵盖了用于健壮软件设计的SOLID原则，并实现了所有23个四人帮(GoF)设计模式，分为行为型、创建型和结构型模式。

## 项目结构 | Project Structure

```
object-oriented-design/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cuizhanming/
│   │   │           ├── ood/           # SOLID Principles Examples | SOLID原则示例
│   │   │           │   ├── SingleResponsibilityExample.java
│   │   │           │   ├── OpenClosedExample.java
│   │   │           │   ├── LiskovSubstitutionExample.java
│   │   │           │   ├── InterfaceSegregationExample.java
│   │   │           │   └── DependencyInversionExample.java
│   │   │           └── oop/           # GoF Design Patterns | GoF设计模式
│   │   │               ├── behavioral/    # Behavioral Patterns | 行为型模式 (11)
│   │   │               ├── creational/    # Creational Patterns | 创建型模式 (5)
│   │   │               └── structural/    # Structural Patterns | 结构型模式 (7)
│   │   └── resources/
├── pom.xml              # Maven Configuration | Maven配置
├── build.gradle         # Gradle Configuration | Gradle配置
└── README.md
```

---

## SOLID原则详解 | SOLID Principles Explained

### 1. 单一职责原则 | Single Responsibility Principle (SRP)

**English**: A class should have only one reason to change, meaning it should have only one job or responsibility. This principle helps create more maintainable and focused classes.

**中文**: 一个类应该只有一个改变的理由，即只有一个职责。这个原则有助于创建更易维护和专注的类。

**核心概念 | Core Concepts**:
- **职责分离** | **Separation of Concerns**: Each class handles one specific responsibility
- **高内聚** | **High Cohesion**: Related functionality grouped together
- **易测试** | **Easier Testing**: Focused classes are easier to unit test

**示例场景 | Example Scenarios**:
- ❌ **违反SRP**: A `User` class that handles user data, validation, and database operations
- ✅ **遵循SRP**: Separate `User` (data), `UserValidator` (validation), and `UserRepository` (persistence)

### 2. 开闭原则 | Open/Closed Principle (OCP)

**English**: Software entities should be open for extension but closed for modification. This is typically achieved through inheritance, interfaces, and composition.

**中文**: 软件实体应该对扩展开放，对修改关闭。这通常通过继承、接口和组合来实现。

**核心概念 | Core Concepts**:
- **扩展性** | **Extensibility**: Add new functionality without changing existing code
- **稳定性** | **Stability**: Existing code remains unchanged and stable
- **多态性** | **Polymorphism**: Use interfaces and abstract classes for flexibility

**实现方式 | Implementation Approaches**:
- **Strategy Pattern**: Different algorithms through interfaces
- **Template Method**: Extension points in abstract classes
- **Plugin Architecture**: Modular extensions

### 3. 里氏替换原则 | Liskov Substitution Principle (LSP)

**English**: Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.

**中文**: 超类的对象应该能够被其子类的对象替换，而不影响程序的正确性。

#### 里氏替换原则详解 | Detailed LSP Explanation

**里氏替换原则 (Liskov Substitution Principle)** 是面向对象设计中的一个重要原则，由计算机科学家芭芭拉·里氏科夫 (Barbara Liskov) 在1987年提出。

**核心思想 | Core Philosophy**:
- 任何基类可以出现的地方，子类一定可以出现
- 子类必须能够替换其基类，而不会导致程序出错
- 这是对开闭原则的补充

**具体要求 | Specific Requirements**:
1. **前置条件不能加强** | **Preconditions cannot be strengthened**: 子类方法的输入参数要求不能比父类更严格
2. **后置条件不能削弱** | **Postconditions cannot be weakened**: 子类方法的输出结果不能比父类承诺的更少
3. **异常不能增加** | **Exceptions cannot be added**: 子类不能抛出父类没有声明的异常
4. **历史约束** | **History constraints**: 子类需要保持父类的所有约束和规范

**违反LSP的经典例子 | Classic LSP Violation Example**:
```java
// 错误示例：正方形继承矩形 | Wrong: Square inherits Rectangle
class Rectangle {
    protected int width, height;
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = this.height = width; // 违反了LSP | Violates LSP
    }
    @Override
    public void setHeight(int height) {
        this.width = this.height = height; // 违反了LSP | Violates LSP
    }
}

// 问题：客户端代码会失败 | Problem: Client code fails
void testRectangle(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.getArea() == 20; // Square会失败！| Square fails!
}
```

**正确的设计方法 | Correct Design Approach**:
```java
// 使用接口或抽象类定义共同行为 | Use interfaces for common behavior
interface Shape {
    double getArea();
    void draw();
}

class Rectangle implements Shape {
    private final double width, height;
    public Rectangle(double width, double height) {
        this.width = width; this.height = height;
    }
    // Implementation...
}

class Square implements Shape {
    private final double side;
    public Square(double side) { this.side = side; }
    // Implementation...
}
```

### 4. 接口隔离原则 | Interface Segregation Principle (ISP)

**English**: Clients should not be forced to depend on interfaces they do not use. Prefer several specific interfaces over a single general-purpose interface.

**中文**: 客户端不应该被迫依赖它们不使用的接口。应该优先使用多个特定接口，而不是单一的通用接口。

**核心概念 | Core Concepts**:
- **接口最小化** | **Interface Minimization**: Keep interfaces small and focused
- **客户端特定** | **Client-Specific**: Design interfaces for specific client needs
- **解耦合** | **Decoupling**: Reduce dependencies between components

**设计指导 | Design Guidelines**:
- **角色接口** | **Role Interfaces**: Design interfaces based on client roles
- **功能分组** | **Functional Grouping**: Group related methods together
- **避免胖接口** | **Avoid Fat Interfaces**: Large interfaces with many unrelated methods

### 5. 依赖倒置原则 | Dependency Inversion Principle (DIP)

**English**: High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details; details should depend on abstractions.

**中文**: 高层模块不应该依赖低层模块，两者都应该依赖于抽象。抽象不应该依赖于细节，细节应该依赖于抽象。

**核心概念 | Core Concepts**:
- **依赖抽象** | **Depend on Abstractions**: Use interfaces instead of concrete classes
- **控制反转** | **Inversion of Control**: Let framework manage dependencies
- **依赖注入** | **Dependency Injection**: Inject dependencies rather than create them

**实现方式 | Implementation Methods**:
- **Constructor Injection**: Dependencies passed through constructor
- **Setter Injection**: Dependencies set through setter methods
- **Interface Injection**: Dependencies provided through interface methods

---

## 设计模式完整指南 | Complete Design Patterns Guide

### 创建型模式 | Creational Patterns (5/5)

**Purpose | 用途**: Focus on object creation mechanisms, trying to create objects in a manner suitable to the situation.

**目的**: 专注于对象创建机制，试图以适合情况的方式创建对象。

#### 1. 单例模式 | Singleton Pattern
- **Intent | 意图**: Ensures only one instance of a class exists
- **Use Case | 使用场景**: Database connections, logging, configuration settings
- **Implementation | 实现**: Thread-safe enum approach (recommended) and traditional double-checked locking

#### 2. 工厂方法模式 | Factory Method Pattern
- **Intent | 意图**: Creates objects without specifying exact classes
- **Use Case | 使用场景**: UI components for different platforms, document creation
- **Implementation | 实现**: Abstract creator with concrete factory implementations

#### 3. 抽象工厂模式 | Abstract Factory Pattern
- **Intent | 意图**: Creates families of related objects
- **Use Case | 使用场景**: Cross-platform UI components, database drivers for different vendors
- **Implementation | 实现**: Factory interface with concrete factories for each product family

#### 4. 建造者模式 | Builder Pattern
- **Intent | 意图**: Constructs complex objects step by step
- **Use Case | 使用场景**: Configuration objects, SQL query builders, computer specifications
- **Implementation | 实现**: Fluent interface with method chaining, optional director class

#### 5. 原型模式 | Prototype Pattern
- **Intent | 意图**: Creates objects by cloning existing instances
- **Use Case | 使用场景**: Object pooling, template-based object creation, expensive object initialization
- **Implementation | 实现**: Clone method with deep/shallow copy considerations, prototype registry

### 结构型模式 | Structural Patterns (7/7)

**Purpose | 用途**: Deal with object composition and relationships between entities.

**目的**: 处理对象组合和实体之间的关系。

#### 1. 适配器模式 | Adapter Pattern
- **Intent | 意图**: Allows incompatible interfaces to work together
- **Use Case | 使用场景**: Legacy system integration, third-party library wrapper
- **Implementation | 实现**: Object adapter (composition) and class adapter (inheritance)

#### 2. 桥接模式 | Bridge Pattern
- **Intent | 意图**: Separates abstraction from implementation
- **Use Case | 使用场景**: Cross-platform GUI, database drivers, graphics rendering
- **Implementation | 实现**: Abstraction hierarchy separate from implementation hierarchy

#### 3. 组合模式 | Composite Pattern
- **Intent | 意图**: Composes objects into tree structures
- **Use Case | 使用场景**: File systems, organizational structures, UI component hierarchies
- **Implementation | 实现**: Component interface with leaf and composite implementations

#### 4. 装饰器模式 | Decorator Pattern
- **Intent | 意图**: Adds behavior to objects dynamically
- **Use Case | 使用场景**: Java I/O streams, middleware chains, feature toggles
- **Implementation | 实现**: Component interface with concrete components and decorators

#### 5. 外观模式 | Facade Pattern
- **Intent | 意图**: Provides simplified interface to complex subsystem
- **Use Case | 使用场景**: API gateways, system initialization, complex library wrapping
- **Implementation | 实现**: Facade class that delegates to subsystem components

#### 6. 享元模式 | Flyweight Pattern
- **Intent | 意图**: Minimizes memory usage with shared objects
- **Use Case | 使用场景**: Text editors, game object management, caching systems
- **Implementation | 实现**: Intrinsic state sharing with extrinsic state parameters

#### 7. 代理模式 | Proxy Pattern
- **Intent | 意图**: Provides placeholder or surrogate for another object
- **Use Case | 使用场景**: Lazy loading, access control, caching, remote objects
- **Implementation | 实现**: Virtual proxy, protection proxy, caching proxy

### 行为型模式 | Behavioral Patterns (11/11)

**Purpose | 用途**: Focus on communication between objects and assignment of responsibilities.

**目的**: 专注于对象之间的通信和职责分配。

#### 1. 观察者模式 | Observer Pattern
- **Intent | 意图**: Notifies multiple objects about state changes
- **Use Case | 使用场景**: Event handling, MVC architecture, real-time notifications
- **Implementation | 实现**: Subject-observer relationship with push/pull notification strategies

#### 2. 策略模式 | Strategy Pattern
- **Intent | 意图**: Defines family of algorithms and makes them interchangeable
- **Use Case | 使用场景**: Payment processing, sorting algorithms, validation rules
- **Implementation | 实现**: Strategy interface with concrete strategy implementations

#### 3. 命令模式 | Command Pattern
- **Intent | 意图**: Encapsulates requests as objects
- **Use Case | 使用场景**: Undo/redo operations, macro commands, queuing requests
- **Implementation | 实现**: Command interface with concrete commands, invoker, and receiver

#### 4. 状态模式 | State Pattern
- **Intent | 意图**: Allows object to alter behavior when internal state changes
- **Use Case | 使用场景**: State machines, workflow systems, game character states
- **Implementation | 实现**: State interface with concrete state implementations

#### 5. 模板方法模式 | Template Method Pattern
- **Intent | 意图**: Defines skeleton of algorithm, subclasses fill in details
- **Use Case | 使用场景**: Data processing pipelines, framework hooks, test fixtures
- **Implementation | 实现**: Abstract class with template method and hook methods

#### 6. 责任链模式 | Chain of Responsibility Pattern
- **Intent | 意图**: Passes requests along a chain of handlers
- **Use Case | 使用场景**: Request processing, middleware chains, exception handling
- **Implementation | 实现**: Handler interface with next handler reference

#### 7. 迭代器模式 | Iterator Pattern
- **Intent | 意图**: Provides way to access elements sequentially
- **Use Case | 使用场景**: Collection traversal, data streaming, cursor-based navigation
- **Implementation | 实现**: Iterator interface with hasNext() and next() methods

#### 8. 中介者模式 | Mediator Pattern
- **Intent | 意图**: Defines how objects interact with each other
- **Use Case | 使用场景**: Chat systems, air traffic control, form validation
- **Implementation | 实现**: Mediator interface coordinating colleague objects

#### 9. 备忘录模式 | Memento Pattern
- **Intent | 意图**: Captures and restores object state
- **Use Case | 使用场景**: Undo operations, checkpoints, transaction rollback
- **Implementation | 实现**: Memento object storing state, originator, and caretaker

#### 10. 访问者模式 | Visitor Pattern
- **Intent | 意图**: Defines operations on objects without changing their classes
- **Use Case | 使用场景**: AST processing, reporting systems, object structure analysis
- **Implementation | 实现**: Visitor interface with element accept() method

#### 11. 解释器模式 | Interpreter Pattern
- **Intent | 意图**: Defines grammar for a language and interprets sentences
- **Use Case | 使用场景**: Expression evaluation, configuration languages, SQL parsing
- **Implementation | 实现**: Expression hierarchy with terminal and non-terminal expressions

---

## 构建和运行 | Build and Run

### 使用Maven | Using Maven
```bash
# 编译项目 | Compile project
mvn clean compile

# 运行示例 | Run examples
mvn exec:java -Dexec.mainClass="com.cuizhanming.ood.SingleResponsibilityExample"
mvn exec:java -Dexec.mainClass="com.cuizhanming.oop.creational.SingletonPattern"
```

### 使用Gradle | Using Gradle
```bash
# 编译项目 | Compile project
./gradlew clean build

# 运行示例 | Run examples
./gradlew run --args="SingleResponsibilityExample"
```

---

## 最佳实践 | Best Practices

### 设计原则 | Design Principles
- **遵循SOLID原则** | **Follow SOLID principles** for maintainable and scalable code
- **组合优于继承** | **Composition over inheritance** for better flexibility
- **接口隔离** | **Interface segregation** for loose coupling
- **依赖注入** | **Dependency injection** for testability

### 现代Java实践 | Modern Java Practices
- **使用不可变数据结构** | **Use immutable data structures** (Java records)
- **构造器注入** | **Constructor injection** for mandatory dependencies
- **数据传输对象** | **Use DTOs** for API boundaries and data transfer
- **现代Java特性** | **Leverage modern Java features**: Streams, Optionals, Pattern Matching

### 设计模式应用 | Design Pattern Application
- **适度应用** | **Apply judiciously** - don't over-engineer
- **理解动机** | **Understand motivation** before applying patterns
- **重构导向** | **Refactoring-driven** pattern introduction
- **团队约定** | **Team conventions** for consistent pattern usage

---

## 学习目标 | Learning Objectives

**完成本项目学习后，您应该理解 | After exploring this project, you should understand**:

### 设计原则 | Design Principles
1. **SOLID原则的实际应用** | How to apply SOLID principles in real Java code
2. **面向对象设计的最佳实践** | Best practices for object-oriented design
3. **代码质量和可维护性** | Code quality and maintainability principles

### 设计模式 | Design Patterns
1. **23个GoF模式的使用场景** | When and how to use each of the 23 GoF design patterns
2. **模式之间的关系** | Relationships between different patterns
3. **模式的变体和组合** | Pattern variations and combinations

### 现代Java开发 | Modern Java Development
1. **Java 17+的最佳实践** | Modern Java (17+) best practices and conventions
2. **专业Java项目结构** | How to structure a professional Java project
3. **设计原则与模式的关系** | The relationship between good design principles and patterns

### 实践技能 | Practical Skills
1. **代码重构和改进** | Code refactoring and improvement techniques
2. **架构决策制定** | Making informed architectural decisions
3. **团队协作和代码审查** | Team collaboration and code review practices

---

## 项目特色 | Key Features

### 全面性 | Comprehensive Coverage
- ✅ **5个SOLID原则** | All 5 SOLID principles with detailed explanations
- ✅ **23个GoF设计模式** | All 23 GoF design patterns with practical examples
- ✅ **双语文档** | Bilingual documentation (English/Chinese)

### 现代化 | Modern Approach
- ✅ **Java 17+特性** | Modern Java features (records, sealed classes, pattern matching)
- ✅ **最佳实践** | Industry best practices and conventions
- ✅ **实际应用场景** | Real-world application scenarios

### 教育价值 | Educational Value
- ✅ **逐步学习** | Progressive learning approach
- ✅ **实践导向** | Hands-on practical examples
- ✅ **深入理解** | Deep understanding with explanations

---

**探索示例文件，了解每个原则和模式的实际演示！**

**Explore the example files for practical demonstrations of each principle and pattern!**
