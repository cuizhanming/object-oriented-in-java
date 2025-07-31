package com.cuizhanming.oop.behavioral;

import java.util.Stack;

/**
 * Command Pattern - Encapsulates requests as objects
 * 命令模式 - 将请求封装为对象
 */
public class CommandPattern {

    // Command interface
    public interface Command {
        void execute();
        void undo();
        String getDescription();
    }

    // Receiver classes
    public static class Light {
        private boolean isOn = false;
        private final String location;

        public Light(String location) {
            this.location = location;
        }

        public void turnOn() {
            isOn = true;
            System.out.println(location + " light is ON");
        }

        public void turnOff() {
            isOn = false;
            System.out.println(location + " light is OFF");
        }

        public boolean isOn() { return isOn; }
        public String getLocation() { return location; }
    }

    public static class Fan {
        private int speed = 0; // 0=off, 1=low, 2=medium, 3=high
        private final String location;

        public Fan(String location) {
            this.location = location;
        }

        public void setSpeed(int speed) {
            this.speed = Math.max(0, Math.min(3, speed));
            String[] levels = {"OFF", "LOW", "MEDIUM", "HIGH"};
            System.out.println(location + " fan speed: " + levels[this.speed]);
        }

        public int getSpeed() { return speed; }
        public String getLocation() { return location; }
    }

    // Concrete commands
    public static class LightOnCommand implements Command {
        private final Light light;

        public LightOnCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOn();
        }

        @Override
        public void undo() {
            light.turnOff();
        }

        @Override
        public String getDescription() {
            return "Turn on " + light.getLocation() + " light";
        }
    }

    public static class LightOffCommand implements Command {
        private final Light light;

        public LightOffCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOff();
        }

        @Override
        public void undo() {
            light.turnOn();
        }

        @Override
        public String getDescription() {
            return "Turn off " + light.getLocation() + " light";
        }
    }

    public static class FanSpeedCommand implements Command {
        private final Fan fan;
        private final int newSpeed;
        private int previousSpeed;

        public FanSpeedCommand(Fan fan, int speed) {
            this.fan = fan;
            this.newSpeed = speed;
        }

        @Override
        public void execute() {
            previousSpeed = fan.getSpeed();
            fan.setSpeed(newSpeed);
        }

        @Override
        public void undo() {
            fan.setSpeed(previousSpeed);
        }

        @Override
        public String getDescription() {
            String[] levels = {"OFF", "LOW", "MEDIUM", "HIGH"};
            return "Set " + fan.getLocation() + " fan to " + levels[newSpeed];
        }
    }

    // Macro command (composite)
    public static class MacroCommand implements Command {
        private final Command[] commands;

        public MacroCommand(Command[] commands) {
            this.commands = commands.clone();
        }

        @Override
        public void execute() {
            System.out.println("Executing macro command...");
            for (Command command : commands) {
                command.execute();
            }
        }

        @Override
        public void undo() {
            System.out.println("Undoing macro command...");
            // Undo in reverse order
            for (int i = commands.length - 1; i >= 0; i--) {
                commands[i].undo();
            }
        }

        @Override
        public String getDescription() {
            return "Macro: Multiple commands";
        }
    }

    // Null object pattern for empty slots
    public static class NoCommand implements Command {
        @Override
        public void execute() {}

        @Override
        public void undo() {}

        @Override
        public String getDescription() {
            return "No command";
        }
    }

    // Invoker
    public static class RemoteControl {
        private final Command[] onCommands;
        private final Command[] offCommands;
        private final Stack<Command> commandHistory;
        private final int slots;

        public RemoteControl(int slots) {
            this.slots = slots;
            this.onCommands = new Command[slots];
            this.offCommands = new Command[slots];
            this.commandHistory = new Stack<>();

            Command noCommand = new NoCommand();
            for (int i = 0; i < slots; i++) {
                onCommands[i] = noCommand;
                offCommands[i] = noCommand;
            }
        }

        public void setCommand(int slot, Command onCommand, Command offCommand) {
            if (slot >= 0 && slot < slots) {
                onCommands[slot] = onCommand;
                offCommands[slot] = offCommand;
                System.out.println("Slot " + slot + " configured: " + onCommand.getDescription());
            }
        }

        public void onButtonPressed(int slot) {
            if (slot >= 0 && slot < slots) {
                onCommands[slot].execute();
                commandHistory.push(onCommands[slot]);
            }
        }

        public void offButtonPressed(int slot) {
            if (slot >= 0 && slot < slots) {
                offCommands[slot].execute();
                commandHistory.push(offCommands[slot]);
            }
        }

        public void undoButtonPressed() {
            if (!commandHistory.isEmpty()) {
                Command lastCommand = commandHistory.pop();
                lastCommand.undo();
                System.out.println("Undoing: " + lastCommand.getDescription());
            } else {
                System.out.println("No commands to undo");
            }
        }

        public void showConfiguration() {
            System.out.println("\n--- Remote Control Configuration ---");
            for (int i = 0; i < slots; i++) {
                System.out.printf("Slot %d: %s | %s%n",
                    i, onCommands[i].getDescription(), offCommands[i].getDescription());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Command Pattern Demo ===");

        // Create receivers
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Fan bedroomFan = new Fan("Bedroom");

        // Create commands
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
        FanSpeedCommand fanHigh = new FanSpeedCommand(bedroomFan, 3);
        FanSpeedCommand fanOff = new FanSpeedCommand(bedroomFan, 0);

        // Create macro command
        Command[] partyModeCommands = {livingRoomLightOn, kitchenLightOn, fanHigh};
        MacroCommand partyModeOn = new MacroCommand(partyModeCommands);
        Command[] partyModeOffCommands = {livingRoomLightOff, kitchenLightOff, fanOff};
        MacroCommand partyModeOff = new MacroCommand(partyModeOffCommands);

        // Create remote control
        RemoteControl remote = new RemoteControl(4);

        // Configure remote
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, fanHigh, fanOff);
        remote.setCommand(3, partyModeOn, partyModeOff);

        remote.showConfiguration();

        // Test commands
        System.out.println("\n--- Testing Commands ---");
        remote.onButtonPressed(0);  // Living room light on
        remote.onButtonPressed(1);  // Kitchen light on
        remote.onButtonPressed(2);  // Fan high

        System.out.println("\n--- Testing Undo ---");
        remote.undoButtonPressed(); // Undo fan
        remote.undoButtonPressed(); // Undo kitchen light
        remote.undoButtonPressed(); // Undo living room light

        System.out.println("\n--- Testing Macro Command ---");
        remote.onButtonPressed(3);  // Party mode on
        remote.undoButtonPressed(); // Undo party mode
    }
}
