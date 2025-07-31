package com.cuizhanming.oop.structural;

/**
 * Facade Pattern - Provides simplified interface to complex subsystem
 * 外观模式 - 为复杂子系统提供简化接口
 */
public class FacadePattern {

    // Complex subsystem classes
    public static class CPU {
        public void freeze() {
            System.out.println("CPU: Freezing processor");
        }

        public void jump(long position) {
            System.out.println("CPU: Jumping to position " + position);
        }

        public void execute() {
            System.out.println("CPU: Executing instructions");
        }
    }

    public static class Memory {
        public void load(long position, byte[] data) {
            System.out.println("Memory: Loading " + data.length + " bytes at position " + position);
        }
    }

    public static class HardDrive {
        public byte[] read(long lba, int size) {
            System.out.println("HardDrive: Reading " + size + " bytes from sector " + lba);
            return new byte[size];
        }
    }

    public static class GraphicsCard {
        public void initialize() {
            System.out.println("Graphics: Initializing graphics card");
        }

        public void renderFrame() {
            System.out.println("Graphics: Rendering frame");
        }
    }

    public static class SoundCard {
        public void initialize() {
            System.out.println("Sound: Initializing sound card");
        }

        public void playSound() {
            System.out.println("Sound: Playing system sound");
        }
    }

    // Facade class
    public static class ComputerFacade {
        private final CPU cpu;
        private final Memory memory;
        private final HardDrive hardDrive;
        private final GraphicsCard graphics;
        private final SoundCard sound;

        public ComputerFacade() {
            this.cpu = new CPU();
            this.memory = new Memory();
            this.hardDrive = new HardDrive();
            this.graphics = new GraphicsCard();
            this.sound = new SoundCard();
        }

        public void startComputer() {
            System.out.println("=== Starting Computer ===");
            cpu.freeze();

            byte[] bootData = hardDrive.read(0, 1024);
            memory.load(0, bootData);

            cpu.jump(0);
            cpu.execute();

            graphics.initialize();
            sound.initialize();

            System.out.println("Computer started successfully!");
        }

        public void shutdownComputer() {
            System.out.println("=== Shutting Down Computer ===");
            sound.playSound(); // Shutdown sound
            graphics.renderFrame(); // Final frame
            cpu.freeze();
            System.out.println("Computer shutdown complete!");
        }

        public void runApplication() {
            System.out.println("=== Running Application ===");
            cpu.execute();
            graphics.renderFrame();
            sound.playSound();
            System.out.println("Application running!");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Facade Pattern Demo ===");

        // Without facade, client would need to interact with all subsystems
        System.out.println("Without Facade (complex):");
        CPU cpu = new CPU();
        Memory memory = new Memory();
        HardDrive hardDrive = new HardDrive();

        cpu.freeze();
        byte[] data = hardDrive.read(0, 1024);
        memory.load(0, data);
        cpu.jump(0);
        cpu.execute();

        System.out.println("\nWith Facade (simplified):");
        // With facade, client has simple interface
        ComputerFacade computer = new ComputerFacade();
        computer.startComputer();
        System.out.println();
        computer.runApplication();
        System.out.println();
        computer.shutdownComputer();
    }
}
