package com.cuizhanming.oop.behavioral;

import java.util.HashMap;
import java.util.Map;

/**
 * Mediator Pattern - Defines how objects interact with each other
 * 中介者模式 - 定义对象之间如何交互
 */
public class MediatorPattern {

    // Mediator interface
    public interface ChatMediator {
        void sendMessage(String message, User user);
        void addUser(User user);
        void removeUser(User user);
    }

    // Abstract colleague
    public abstract static class User {
        protected ChatMediator mediator;
        protected String name;

        public User(ChatMediator mediator, String name) {
            this.mediator = mediator;
            this.name = name;
        }

        public abstract void send(String message);
        public abstract void receive(String message, String from);

        public String getName() { return name; }
    }

    // Concrete mediator
    public static class ChatRoom implements ChatMediator {
        private final Map<String, User> users = new HashMap<>();

        @Override
        public void addUser(User user) {
            users.put(user.getName(), user);
            System.out.println(user.getName() + " joined the chat room");
            broadcast(user.getName() + " has joined the chat", null);
        }

        @Override
        public void removeUser(User user) {
            users.remove(user.getName());
            System.out.println(user.getName() + " left the chat room");
            broadcast(user.getName() + " has left the chat", null);
        }

        @Override
        public void sendMessage(String message, User sender) {
            System.out.println("[" + sender.getName() + "]: " + message);
            // Send to all users except sender
            for (User user : users.values()) {
                if (!user.equals(sender)) {
                    user.receive(message, sender.getName());
                }
            }
        }

        private void broadcast(String message, User sender) {
            System.out.println("[SYSTEM]: " + message);
            for (User user : users.values()) {
                if (!user.equals(sender)) {
                    user.receive(message, "SYSTEM");
                }
            }
        }
    }

    // Concrete colleagues
    public static class BasicUser extends User {
        public BasicUser(ChatMediator mediator, String name) {
            super(mediator, name);
        }

        @Override
        public void send(String message) {
            mediator.sendMessage(message, this);
        }

        @Override
        public void receive(String message, String from) {
            System.out.println(name + " received from " + from + ": " + message);
        }
    }

    public static class PremiumUser extends User {
        public PremiumUser(ChatMediator mediator, String name) {
            super(mediator, name);
        }

        @Override
        public void send(String message) {
            mediator.sendMessage("[PREMIUM] " + message, this);
        }

        @Override
        public void receive(String message, String from) {
            System.out.println("[VIP] " + name + " received from " + from + ": " + message);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Mediator Pattern Demo ===");

        ChatMediator chatRoom = new ChatRoom();

        // Create users
        User alice = new BasicUser(chatRoom, "Alice");
        User bob = new BasicUser(chatRoom, "Bob");
        User charlie = new PremiumUser(chatRoom, "Charlie");

        // Add users to chat room
        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        System.out.println("\n--- Chat Session ---");
        alice.send("Hello everyone!");
        bob.send("Hi Alice!");
        charlie.send("Good morning all!");

        System.out.println("\n--- User Leaving ---");
        chatRoom.removeUser(bob);
        alice.send("Where did Bob go?");
    }
}
