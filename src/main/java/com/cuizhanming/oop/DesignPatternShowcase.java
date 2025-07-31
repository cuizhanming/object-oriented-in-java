package com.cuizhanming.oop;

import com.cuizhanming.oop.behavioral.*;
import com.cuizhanming.oop.creational.*;
import com.cuizhanming.oop.structural.*;

/**
 * Main demonstration class showcasing all GoF Design Patterns
 * 主演示类展示所有GoF设计模式
 */
public class DesignPatternShowcase {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("🎯 DESIGN PATTERNS SHOWCASE | 设计模式展示");
        System.out.println("=".repeat(60));

        demonstrateCreationalPatterns();
        demonstrateStructuralPatterns();
        demonstrateBehavioralPatterns();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ All 23 GoF Design Patterns Demonstrated!");
        System.out.println("✅ 所有23个GoF设计模式演示完成！");
        System.out.println("=".repeat(60));
    }

    private static void demonstrateCreationalPatterns() {
        System.out.println("\n🏗️  CREATIONAL PATTERNS | 创建型模式 (5/5)");
        System.out.println("-".repeat(50));

        // 1. Singleton Pattern
        System.out.println("\n1. Singleton Pattern | 单例模式");
        SingletonPattern.DatabaseConnection.INSTANCE.connect();

        // 2. Factory Method Pattern
        System.out.println("\n2. Factory Method Pattern | 工厂方法模式");
        var carFactory = new FactoryMethodPattern.CarFactory();
        carFactory.operateVehicle();

        // 3. Abstract Factory Pattern
        System.out.println("\n3. Abstract Factory Pattern | 抽象工厂模式");
        var windowsFactory = new AbstractFactoryPattern.WindowsUIFactory();
        var windowsApp = new AbstractFactoryPattern.Application(windowsFactory);
        windowsApp.render();

        // 4. Builder Pattern
        System.out.println("\n4. Builder Pattern | 建造者模式");
        var computer = new BuilderPattern.Computer.Builder()
            .cpu("Intel i7")
            .ram("16GB")
            .storage("1TB SSD")
            .withWifi()
            .build();
        System.out.println("Built: " + computer.toString().split("\n")[1]); // Show CPU line

        // 5. Prototype Pattern
        System.out.println("\n5. Prototype Pattern | 原型模式");
        var registry = new PrototypePattern.ShapeRegistry();
        var circle = new PrototypePattern.Circle();
        circle.setColor("Blue");
        registry.registerPrototype("blue-circle", circle);
        var clonedCircle = registry.createShape("blue-circle");
        clonedCircle.draw();
    }

    private static void demonstrateStructuralPatterns() {
        System.out.println("\n🏛️  STRUCTURAL PATTERNS | 结构型模式 (7/7)");
        System.out.println("-".repeat(50));

        // 1. Adapter Pattern
        System.out.println("\n1. Adapter Pattern | 适配器模式");
        var audioPlayer = new AdapterPattern.AudioPlayer();
        audioPlayer.play("mp4", "movie.mp4");

        // 2. Bridge Pattern
        System.out.println("\n2. Bridge Pattern | 桥接模式");
        var circle = new BridgePattern.Circle(5, 10, 3, new BridgePattern.DrawingAPI1());
        circle.draw();

        // 3. Composite Pattern
        System.out.println("\n3. Composite Pattern | 组合模式");
        var directory = new CompositePattern.Directory("project");
        directory.addComponent(new CompositePattern.File("main.java", 1024));
        System.out.println("Directory size: " + directory.getSize() + " bytes");

        // 4. Decorator Pattern
        System.out.println("\n4. Decorator Pattern | 装饰器模式");
        var coffee = new DecoratorPattern.MilkDecorator(
            new DecoratorPattern.SimpleCoffee()
        );
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // 5. Facade Pattern
        System.out.println("\n5. Facade Pattern | 外观模式");
        var computer = new FacadePattern.ComputerFacade();
        computer.startComputer();

        // 6. Flyweight Pattern
        System.out.println("\n6. Flyweight Pattern | 享元模式");
        var flyweight = FlyweightPattern.CharacterFactory.getCharacter('A');
        flyweight.display(1, 1, "Arial", 12);

        // 7. Proxy Pattern
        System.out.println("\n7. Proxy Pattern | 代理模式");
        var imageProxy = new ProxyPattern.ImageProxy("photo.jpg");
        System.out.println(imageProxy.getInfo());
    }

    private static void demonstrateBehavioralPatterns() {
        System.out.println("\n🔄 BEHAVIORAL PATTERNS | 行为型模式 (11/11)");
        System.out.println("-".repeat(50));

        // 1. Observer Pattern
        System.out.println("\n1. Observer Pattern | 观察者模式");
        var weatherStation = new ObserverPattern.WeatherStation();
        var display = new ObserverPattern.CurrentConditionsDisplay();
        weatherStation.registerObserver(display);
        weatherStation.setMeasurements(25.0f, 65.0f, 30.4f);

        // 2. Strategy Pattern
        System.out.println("\n2. Strategy Pattern | 策略模式");
        var cart = new StrategyPattern.ShoppingCart();
        cart.addItem(new StrategyPattern.ShoppingCart.Item("Book", 29.99));
        cart.setPaymentStrategy(new StrategyPattern.CreditCardStrategy("1234567890123456", "123", "12/25"));
        cart.checkout();

        // 3. Command Pattern
        System.out.println("\n3. Command Pattern | 命令模式");
        var light = new CommandPattern.Light("Living Room");
        var lightOn = new CommandPattern.LightOnCommand(light);
        var remote = new CommandPattern.RemoteControl(1);
        remote.setCommand(0, lightOn, new CommandPattern.LightOffCommand(light));
        remote.onButtonPressed(0);

        // 4. State Pattern
        System.out.println("\n4. State Pattern | 状态模式");
        var vendingMachine = new StatePattern.VendingMachine();
        vendingMachine.insertCoin();
        vendingMachine.selectProduct();

        // 5. Template Method Pattern
        System.out.println("\n5. Template Method Pattern | 模板方法模式");
        var csvProcessor = new TemplateMethodPattern.CSVDataProcessor();
        csvProcessor.processData();

        // 6. Chain of Responsibility Pattern
        System.out.println("\n6. Chain of Responsibility Pattern | 责任链模式");
        var supportSystem = new ChainOfResponsibilityPattern.SupportSystem();
        var request = new ChainOfResponsibilityPattern.SupportRequest(
            "Password reset help",
            ChainOfResponsibilityPattern.SupportRequest.Priority.LOW,
            ChainOfResponsibilityPattern.SupportRequest.Type.GENERAL,
            "John Doe"
        );
        supportSystem.processRequest(request);

        // 7. Iterator Pattern
        System.out.println("\n7. Iterator Pattern | 迭代器模式");
        var library = new IteratorPattern.BookCollection();
        library.addBook(new IteratorPattern.Book("Design Patterns", "GoF", "123", 1994));
        var iterator = library.createIterator();
        if (iterator.hasNext()) {
            System.out.println("First book: " + iterator.next());
        }

        // 8. Mediator Pattern
        System.out.println("\n8. Mediator Pattern | 中介者模式");
        var chatRoom = new MediatorPattern.ChatRoom();
        var alice = new MediatorPattern.BasicUser(chatRoom, "Alice");
        chatRoom.addUser(alice);
        alice.send("Hello from mediator demo!");

        // 9. Memento Pattern
        System.out.println("\n9. Memento Pattern | 备忘录模式");
        var editor = new MementoPattern.TextEditor();
        var history = new MementoPattern.EditorHistory(5);
        editor.write("Hello");
        history.saveState(editor);
        editor.write(" World");
        history.undo(editor);

        // 10. Visitor Pattern
        System.out.println("\n10. Visitor Pattern | 访问者模式");
        var drawing = new VisitorPattern.Drawing();
        drawing.addShape(new VisitorPattern.Circle(5.0, 10.0, 15.0));
        var areaCalculator = new VisitorPattern.AreaCalculatorVisitor();
        drawing.accept(areaCalculator);
        System.out.println("Total area: " + String.format("%.2f", areaCalculator.getTotalArea()));

        // 11. Interpreter Pattern
        System.out.println("\n11. Interpreter Pattern | 解释器模式");
        var calculator = new InterpreterPattern.Calculator();
        calculator.setVariable("x", 10);
        calculator.setVariable("y", 5);
        int result = calculator.evaluate("x y +");
        System.out.println("Expression result: " + result);
    }
}
