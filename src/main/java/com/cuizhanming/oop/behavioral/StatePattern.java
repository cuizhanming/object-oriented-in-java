package com.cuizhanming.oop.behavioral;

/**
 * State Pattern - Allows object to alter behavior when internal state changes
 * 状态模式 - 允许对象在内部状态改变时改变其行为
 */
public class StatePattern {

    // State interface
    public interface VendingMachineState {
        void insertCoin();
        void selectProduct();
        void dispenseProduct();
        void refund();
        String getStateName();
    }

    // Context class
    public static class VendingMachine {
        private VendingMachineState currentState;
        private final VendingMachineState idleState;
        private final VendingMachineState coinInsertedState;
        private final VendingMachineState productSelectedState;
        private final VendingMachineState soldState;
        private final VendingMachineState soldOutState;

        private int coinCount = 0;
        private int productCount = 5;

        public VendingMachine() {
            this.idleState = new IdleState(this);
            this.coinInsertedState = new CoinInsertedState(this);
            this.productSelectedState = new ProductSelectedState(this);
            this.soldState = new SoldState(this);
            this.soldOutState = new SoldOutState(this);

            this.currentState = productCount > 0 ? idleState : soldOutState;
        }

        public void insertCoin() {
            currentState.insertCoin();
        }

        public void selectProduct() {
            currentState.selectProduct();
        }

        public void dispenseProduct() {
            currentState.dispenseProduct();
        }

        public void refund() {
            currentState.refund();
        }

        // State management
        public void setState(VendingMachineState state) {
            System.out.println("State changed to: " + state.getStateName());
            this.currentState = state;
        }

        // Getters for states
        public VendingMachineState getIdleState() { return idleState; }
        public VendingMachineState getCoinInsertedState() { return coinInsertedState; }
        public VendingMachineState getProductSelectedState() { return productSelectedState; }
        public VendingMachineState getSoldState() { return soldState; }
        public VendingMachineState getSoldOutState() { return soldOutState; }

        // Business logic methods
        public void addCoin() {
            coinCount++;
            System.out.println("Coin added. Total coins: " + coinCount);
        }

        public void returnCoins() {
            if (coinCount > 0) {
                System.out.println("Returning " + coinCount + " coins");
                coinCount = 0;
            }
        }

        public void releaseProduct() {
            if (productCount > 0) {
                productCount--;
                coinCount = 0;
                System.out.println("Product dispensed! Remaining products: " + productCount);
            }
        }

        public boolean hasCoins() { return coinCount > 0; }
        public boolean hasProducts() { return productCount > 0; }
        public int getCoinCount() { return coinCount; }
        public int getProductCount() { return productCount; }

        public void showStatus() {
            System.out.println("Status: " + currentState.getStateName() +
                ", Coins: " + coinCount + ", Products: " + productCount);
        }
    }

    // Concrete states
    public static class IdleState implements VendingMachineState {
        private final VendingMachine machine;

        public IdleState(VendingMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertCoin() {
            machine.addCoin();
            machine.setState(machine.getCoinInsertedState());
        }

        @Override
        public void selectProduct() {
            System.out.println("Insert coin first!");
        }

        @Override
        public void dispenseProduct() {
            System.out.println("Insert coin and select product first!");
        }

        @Override
        public void refund() {
            System.out.println("No coins to refund");
        }

        @Override
        public String getStateName() {
            return "Idle";
        }
    }

    public static class CoinInsertedState implements VendingMachineState {
        private final VendingMachine machine;

        public CoinInsertedState(VendingMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertCoin() {
            machine.addCoin();
            System.out.println("Additional coin added");
        }

        @Override
        public void selectProduct() {
            System.out.println("Product selected");
            machine.setState(machine.getProductSelectedState());
        }

        @Override
        public void dispenseProduct() {
            System.out.println("Select a product first!");
        }

        @Override
        public void refund() {
            machine.returnCoins();
            machine.setState(machine.getIdleState());
        }

        @Override
        public String getStateName() {
            return "Coin Inserted";
        }
    }

    public static class ProductSelectedState implements VendingMachineState {
        private final VendingMachine machine;

        public ProductSelectedState(VendingMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertCoin() {
            machine.addCoin();
            System.out.println("Additional coin added");
        }

        @Override
        public void selectProduct() {
            System.out.println("Product already selected");
        }

        @Override
        public void dispenseProduct() {
            machine.setState(machine.getSoldState());
            machine.releaseProduct();

            if (machine.hasProducts()) {
                machine.setState(machine.getIdleState());
            } else {
                machine.setState(machine.getSoldOutState());
            }
        }

        @Override
        public void refund() {
            machine.returnCoins();
            machine.setState(machine.getIdleState());
        }

        @Override
        public String getStateName() {
            return "Product Selected";
        }
    }

    public static class SoldState implements VendingMachineState {
        private final VendingMachine machine;

        public SoldState(VendingMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertCoin() {
            System.out.println("Please wait, dispensing product");
        }

        @Override
        public void selectProduct() {
            System.out.println("Please wait, dispensing product");
        }

        @Override
        public void dispenseProduct() {
            System.out.println("Product already being dispensed");
        }

        @Override
        public void refund() {
            System.out.println("Cannot refund, product already dispensed");
        }

        @Override
        public String getStateName() {
            return "Sold";
        }
    }

    public static class SoldOutState implements VendingMachineState {
        private final VendingMachine machine;

        public SoldOutState(VendingMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertCoin() {
            System.out.println("Machine is sold out, coin rejected");
        }

        @Override
        public void selectProduct() {
            System.out.println("No products available");
        }

        @Override
        public void dispenseProduct() {
            System.out.println("No products to dispense");
        }

        @Override
        public void refund() {
            machine.returnCoins();
        }

        @Override
        public String getStateName() {
            return "Sold Out";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== State Pattern Demo ===");

        VendingMachine machine = new VendingMachine();
        machine.showStatus();

        // Test normal flow
        System.out.println("\n--- Normal Purchase Flow ---");
        machine.insertCoin();
        machine.selectProduct();
        machine.dispenseProduct();
        machine.showStatus();

        // Test refund
        System.out.println("\n--- Refund Flow ---");
        machine.insertCoin();
        machine.insertCoin();
        machine.refund();
        machine.showStatus();

        // Test invalid operations
        System.out.println("\n--- Invalid Operations ---");
        machine.selectProduct(); // Should require coin first
        machine.dispenseProduct(); // Should require coin and selection first

        // Exhaust all products
        System.out.println("\n--- Exhausting Products ---");
        for (int i = 0; i < 5; i++) {
            machine.insertCoin();
            machine.selectProduct();
            machine.dispenseProduct();
            machine.showStatus();
        }

        // Try to buy when sold out
        System.out.println("\n--- Trying to Buy When Sold Out ---");
        machine.insertCoin();
        machine.selectProduct();
        machine.showStatus();
    }
}
