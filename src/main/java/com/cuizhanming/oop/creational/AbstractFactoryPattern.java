package com.cuizhanming.oop.creational;

/**
 * Abstract Factory Pattern - Creates families of related objects
 * 抽象工厂模式 - 创建相关对象的家族
 */
public class AbstractFactoryPattern {

    // Abstract products
    public interface Button {
        void render();
        void onClick();
    }

    public interface TextBox {
        void render();
        void setText(String text);
    }

    // Windows family products
    public static class WindowsButton implements Button {
        @Override
        public void render() {
            System.out.println("Rendering Windows-style button");
        }

        @Override
        public void onClick() {
            System.out.println("Windows button clicked");
        }
    }

    public static class WindowsTextBox implements TextBox {
        @Override
        public void render() {
            System.out.println("Rendering Windows-style text box");
        }

        @Override
        public void setText(String text) {
            System.out.println("Windows text box: " + text);
        }
    }

    // macOS family products
    public static class MacButton implements Button {
        @Override
        public void render() {
            System.out.println("Rendering macOS-style button");
        }

        @Override
        public void onClick() {
            System.out.println("macOS button clicked");
        }
    }

    public static class MacTextBox implements TextBox {
        @Override
        public void render() {
            System.out.println("Rendering macOS-style text box");
        }

        @Override
        public void setText(String text) {
            System.out.println("macOS text box: " + text);
        }
    }

    // Abstract factory
    public interface UIFactory {
        Button createButton();
        TextBox createTextBox();
    }

    // Concrete factories
    public static class WindowsUIFactory implements UIFactory {
        @Override
        public Button createButton() {
            return new WindowsButton();
        }

        @Override
        public TextBox createTextBox() {
            return new WindowsTextBox();
        }
    }

    public static class MacUIFactory implements UIFactory {
        @Override
        public Button createButton() {
            return new MacButton();
        }

        @Override
        public TextBox createTextBox() {
            return new MacTextBox();
        }
    }

    // Client code
    public static class Application {
        private final Button button;
        private final TextBox textBox;

        public Application(UIFactory factory) {
            this.button = factory.createButton();
            this.textBox = factory.createTextBox();
        }

        public void render() {
            button.render();
            textBox.render();
            textBox.setText("Hello World!");
            button.onClick();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Abstract Factory Pattern Demo ===");

        // Create Windows application
        System.out.println("\nWindows Application:");
        UIFactory windowsFactory = new WindowsUIFactory();
        Application windowsApp = new Application(windowsFactory);
        windowsApp.render();

        // Create macOS application
        System.out.println("\nmacOS Application:");
        UIFactory macFactory = new MacUIFactory();
        Application macApp = new Application(macFactory);
        macApp.render();
    }
}
