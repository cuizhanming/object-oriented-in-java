package com.cuizhanming.ood;

// Dependency Inversion Principle Example
// Modern Java (17+)

public class DependencyInversionExample {
    public static void main(String[] args) {
        MessageService service = new EmailService();
        Notification notification = new Notification(service);
        notification.send("Hello SOLID!");
    }
}

interface MessageService {
    void sendMessage(String message);
}

class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

class Notification {
    private final MessageService service;
    public Notification(MessageService service) {
        this.service = service;
    }
    public void send(String message) {
        service.sendMessage(message);
    }
}

// High-level module (Notification) depends on abstraction (MessageService), not concrete implementation (EmailService).
