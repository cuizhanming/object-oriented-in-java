package com.cuizhanming.oop.behavioral;

/**
 * Strategy Pattern - Defines family of algorithms and makes them interchangeable
 * 策略模式 - 定义算法族并使它们可以互换
 */
public class StrategyPattern {

    // Strategy interface
    public interface PaymentStrategy {
        void pay(double amount);
        boolean isValid();
        String getPaymentType();
    }

    // Concrete strategies
    public static class CreditCardStrategy implements PaymentStrategy {
        private final String cardNumber;
        private final String cvv;
        private final String expiryDate;

        public CreditCardStrategy(String cardNumber, String cvv, String expiryDate) {
            this.cardNumber = cardNumber;
            this.cvv = cvv;
            this.expiryDate = expiryDate;
        }

        @Override
        public void pay(double amount) {
            if (isValid()) {
                System.out.printf("Paid $%.2f using Credit Card ending in %s%n",
                    amount, cardNumber.substring(cardNumber.length() - 4));
            } else {
                System.out.println("Invalid credit card details!");
            }
        }

        @Override
        public boolean isValid() {
            return cardNumber.length() == 16 && cvv.length() == 3;
        }

        @Override
        public String getPaymentType() {
            return "Credit Card";
        }
    }

    public static class PayPalStrategy implements PaymentStrategy {
        private final String email;
        private final String password;

        public PayPalStrategy(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public void pay(double amount) {
            if (isValid()) {
                System.out.printf("Paid $%.2f using PayPal account: %s%n", amount, email);
            } else {
                System.out.println("Invalid PayPal credentials!");
            }
        }

        @Override
        public boolean isValid() {
            return email.contains("@") && password.length() >= 6;
        }

        @Override
        public String getPaymentType() {
            return "PayPal";
        }
    }

    public static class BankTransferStrategy implements PaymentStrategy {
        private final String accountNumber;
        private final String routingNumber;

        public BankTransferStrategy(String accountNumber, String routingNumber) {
            this.accountNumber = accountNumber;
            this.routingNumber = routingNumber;
        }

        @Override
        public void pay(double amount) {
            if (isValid()) {
                System.out.printf("Paid $%.2f using Bank Transfer from account ending in %s%n",
                    amount, accountNumber.substring(accountNumber.length() - 4));
            } else {
                System.out.println("Invalid bank account details!");
            }
        }

        @Override
        public boolean isValid() {
            return accountNumber.length() >= 10 && routingNumber.length() == 9;
        }

        @Override
        public String getPaymentType() {
            return "Bank Transfer";
        }
    }

    // Context class
    public static class ShoppingCart {
        private final java.util.List<Item> items = new java.util.ArrayList<>();
        private PaymentStrategy paymentStrategy;

        public static class Item {
            private final String name;
            private final double price;

            public Item(String name, double price) {
                this.name = name;
                this.price = price;
            }

            public String getName() { return name; }
            public double getPrice() { return price; }
        }

        public void addItem(Item item) {
            items.add(item);
            System.out.println("Added: " + item.getName() + " - $" + item.getPrice());
        }

        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
            System.out.println("Payment method set to: " + strategy.getPaymentType());
        }

        public void checkout() {
            double total = calculateTotal();
            System.out.printf("Total amount: $%.2f%n", total);

            if (paymentStrategy == null) {
                System.out.println("Please select a payment method!");
                return;
            }

            paymentStrategy.pay(total);
        }

        private double calculateTotal() {
            return items.stream().mapToDouble(Item::getPrice).sum();
        }

        public void showCart() {
            System.out.println("Shopping Cart:");
            items.forEach(item ->
                System.out.printf("  %s - $%.2f%n", item.getName(), item.getPrice()));
            System.out.printf("Total: $%.2f%n", calculateTotal());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern Demo ===");

        ShoppingCart cart = new ShoppingCart();

        // Add items to cart
        cart.addItem(new ShoppingCart.Item("Laptop", 999.99));
        cart.addItem(new ShoppingCart.Item("Mouse", 29.99));
        cart.addItem(new ShoppingCart.Item("Keyboard", 79.99));

        System.out.println();
        cart.showCart();

        // Try different payment strategies
        System.out.println("\n--- Trying Credit Card Payment ---");
        cart.setPaymentStrategy(new CreditCardStrategy("1234567890123456", "123", "12/25"));
        cart.checkout();

        System.out.println("\n--- Trying PayPal Payment ---");
        cart.setPaymentStrategy(new PayPalStrategy("user@example.com", "password123"));
        cart.checkout();

        System.out.println("\n--- Trying Bank Transfer Payment ---");
        cart.setPaymentStrategy(new BankTransferStrategy("1234567890", "123456789"));
        cart.checkout();

        System.out.println("\n--- Trying Invalid Credit Card ---");
        cart.setPaymentStrategy(new CreditCardStrategy("123", "12", "12/25"));
        cart.checkout();
    }
}
