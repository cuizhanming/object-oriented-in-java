package com.cuizhanming.ood;

// Single Responsibility Principle Example
// Modern Java (17+)

public class SingleResponsibilityExample {
    public static void main(String[] args) {
        Report report = new Report("SOLID Principles", "A guide to object-oriented design.");
        ReportPrinter printer = new ReportPrinter();
        printer.print(report);
    }
}

record Report(String title, String content) {}

class ReportPrinter {
    public void print(Report report) {
        System.out.println("Title: " + report.title());
        System.out.println("Content: " + report.content());
    }
}

// Report handles data, ReportPrinter handles presentation. Each class has a single responsibility.
