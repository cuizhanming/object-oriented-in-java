package com.cuizhanming.oop.behavioral;

/**
 * Chain of Responsibility Pattern - Passes requests along a chain of handlers
 * 责任链模式 - 沿着处理者链传递请求
 */
public class ChainOfResponsibilityPattern {

    // Abstract handler
    public abstract static class SupportHandler {
        protected SupportHandler nextHandler;

        public void setNextHandler(SupportHandler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public abstract void handleRequest(SupportRequest request);

        protected void passToNext(SupportRequest request) {
            if (nextHandler != null) {
                nextHandler.handleRequest(request);
            } else {
                System.out.println("No handler available for: " + request.getDescription());
            }
        }
    }

    // Request class
    public static class SupportRequest {
        public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
        public enum Type { TECHNICAL, BILLING, GENERAL }

        private final String description;
        private final Priority priority;
        private final Type type;
        private final String customerName;

        public SupportRequest(String description, Priority priority, Type type, String customerName) {
            this.description = description;
            this.priority = priority;
            this.type = type;
            this.customerName = customerName;
        }

        // Getters
        public String getDescription() { return description; }
        public Priority getPriority() { return priority; }
        public Type getType() { return type; }
        public String getCustomerName() { return customerName; }

        @Override
        public String toString() {
            return String.format("[%s] %s - %s (%s)", priority, type, description, customerName);
        }
    }

    // Concrete handlers
    public static class Level1SupportHandler extends SupportHandler {
        @Override
        public void handleRequest(SupportRequest request) {
            if (request.getPriority() == SupportRequest.Priority.LOW &&
                request.getType() == SupportRequest.Type.GENERAL) {
                System.out.println("Level 1 Support handling: " + request);
                System.out.println("  → Providing basic troubleshooting steps");
            } else {
                System.out.println("Level 1 Support: Escalating request");
                passToNext(request);
            }
        }
    }

    public static class Level2SupportHandler extends SupportHandler {
        @Override
        public void handleRequest(SupportRequest request) {
            if ((request.getPriority() == SupportRequest.Priority.LOW ||
                 request.getPriority() == SupportRequest.Priority.MEDIUM) &&
                request.getType() == SupportRequest.Type.TECHNICAL) {
                System.out.println("Level 2 Support handling: " + request);
                System.out.println("  → Performing advanced technical diagnosis");
            } else {
                System.out.println("Level 2 Support: Escalating request");
                passToNext(request);
            }
        }
    }

    public static class ManagerHandler extends SupportHandler {
        @Override
        public void handleRequest(SupportRequest request) {
            if (request.getPriority() == SupportRequest.Priority.HIGH ||
                request.getType() == SupportRequest.Type.BILLING) {
                System.out.println("Manager handling: " + request);
                System.out.println("  → Providing management-level resolution");
            } else {
                System.out.println("Manager: Escalating to senior management");
                passToNext(request);
            }
        }
    }

    public static class SeniorManagerHandler extends SupportHandler {
        @Override
        public void handleRequest(SupportRequest request) {
            if (request.getPriority() == SupportRequest.Priority.CRITICAL) {
                System.out.println("Senior Manager handling: " + request);
                System.out.println("  → Immediate executive attention and resolution");
            } else {
                System.out.println("Senior Manager: Request handled by appropriate level");
                passToNext(request);
            }
        }
    }

    // Support system class to manage the chain
    public static class SupportSystem {
        private final SupportHandler firstHandler;

        public SupportSystem() {
            // Build the chain
            SupportHandler level1 = new Level1SupportHandler();
            SupportHandler level2 = new Level2SupportHandler();
            SupportHandler manager = new ManagerHandler();
            SupportHandler seniorManager = new SeniorManagerHandler();

            level1.setNextHandler(level2);
            level2.setNextHandler(manager);
            manager.setNextHandler(seniorManager);

            this.firstHandler = level1;
        }

        public void processRequest(SupportRequest request) {
            System.out.println("\n=== Processing Support Request ===");
            System.out.println("Request: " + request);
            firstHandler.handleRequest(request);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Chain of Responsibility Pattern Demo ===");

        SupportSystem supportSystem = new SupportSystem();

        // Create various support requests
        SupportRequest[] requests = {
            new SupportRequest("How to reset password?",
                SupportRequest.Priority.LOW, SupportRequest.Type.GENERAL, "John Doe"),

            new SupportRequest("Application crashes on startup",
                SupportRequest.Priority.MEDIUM, SupportRequest.Type.TECHNICAL, "Jane Smith"),

            new SupportRequest("Billing discrepancy in last invoice",
                SupportRequest.Priority.HIGH, SupportRequest.Type.BILLING, "Bob Johnson"),

            new SupportRequest("Server down affecting all customers",
                SupportRequest.Priority.CRITICAL, SupportRequest.Type.TECHNICAL, "Alice Brown"),

            new SupportRequest("Feature request for new functionality",
                SupportRequest.Priority.LOW, SupportRequest.Type.TECHNICAL, "Charlie Wilson")
        };

        // Process all requests
        for (SupportRequest request : requests) {
            supportSystem.processRequest(request);
        }
    }
}
