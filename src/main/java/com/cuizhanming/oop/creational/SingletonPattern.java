package com.cuizhanming.oop.creational;

/**
 * Singleton Pattern - Ensures only one instance of a class exists
 * 单例模式 - 确保类只有一个实例
 */
public class SingletonPattern {

    // Thread-safe Singleton using enum (Effective Java recommendation)
    public enum DatabaseConnection {
        INSTANCE;

        private String connectionString;

        DatabaseConnection() {
            this.connectionString = "jdbc:mysql://localhost:3306/mydb";
        }

        public void connect() {
            System.out.println("Connecting to: " + connectionString);
        }

        public void disconnect() {
            System.out.println("Disconnecting from database");
        }
    }

    // Traditional thread-safe Singleton with lazy initialization
    public static class Logger {
        private static volatile Logger instance;
        private static final Object lock = new Object();

        private Logger() {
            // Private constructor to prevent instantiation
        }

        public static Logger getInstance() {
            if (instance == null) {
                synchronized (lock) {
                    if (instance == null) {
                        instance = new Logger();
                    }
                }
            }
            return instance;
        }

        public void log(String message) {
            System.out.println("[LOG] " + message);
        }
    }

    public static void main(String[] args) {
        // Using enum singleton
        DatabaseConnection.INSTANCE.connect();
        DatabaseConnection.INSTANCE.disconnect();

        // Using traditional singleton
        Logger logger = Logger.getInstance();
        logger.log("Application started");

        // Verify same instance
        Logger logger2 = Logger.getInstance();
        System.out.println("Same instance: " + (logger == logger2));
    }
}
