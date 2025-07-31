package com.cuizhanming.ood;

// Interface Segregation Principle Example
// Modern Java (17+)

public class InterfaceSegregationExample {
    public static void main(String[] args) {
        MultiFunctionPrinter printer = new MultiFunctionPrinter();
        printer.print("Document");
        printer.scan("Photo");
    }
}

interface Printer {
    void print(String content);
}

interface Scanner {
    void scan(String content);
}

class MultiFunctionPrinter implements Printer, Scanner {
    @Override
    public void print(String content) {
        System.out.println("Printing: " + content);
    }
    @Override
    public void scan(String content) {
        System.out.println("Scanning: " + content);
    }
}

// Clients depend only on the interfaces they use (Printer, Scanner), not a fat interface.
