package com.cuizhanming.oop.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern - Composes objects into tree structures
 * 组合模式 - 将对象组合成树形结构
 */
public class CompositePattern {

    // Component interface
    public interface FileSystemComponent {
        void showDetails();
        long getSize();
        String getName();
    }

    // Leaf component - File
    public static class File implements FileSystemComponent {
        private final String name;
        private final long size;

        public File(String name, long size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public void showDetails() {
            System.out.println("File: " + name + " (" + size + " bytes)");
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    // Composite component - Directory
    public static class Directory implements FileSystemComponent {
        private final String name;
        private final List<FileSystemComponent> components = new ArrayList<>();

        public Directory(String name) {
            this.name = name;
        }

        public void addComponent(FileSystemComponent component) {
            components.add(component);
        }

        public void removeComponent(FileSystemComponent component) {
            components.remove(component);
        }

        @Override
        public void showDetails() {
            System.out.println("Directory: " + name + " (" + getSize() + " bytes total)");
            for (FileSystemComponent component : components) {
                System.out.print("  ");
                component.showDetails();
            }
        }

        @Override
        public long getSize() {
            return components.stream()
                .mapToLong(FileSystemComponent::getSize)
                .sum();
        }

        @Override
        public String getName() {
            return name;
        }

        public List<FileSystemComponent> getComponents() {
            return new ArrayList<>(components);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Composite Pattern Demo ===");

        // Create files
        File readme = new File("README.md", 1024);
        File config = new File("config.json", 512);
        File app = new File("app.java", 2048);
        File test = new File("test.java", 1536);

        // Create directories
        Directory src = new Directory("src");
        Directory docs = new Directory("docs");
        Directory project = new Directory("project");

        // Build tree structure
        src.addComponent(app);
        src.addComponent(test);

        docs.addComponent(readme);

        project.addComponent(src);
        project.addComponent(docs);
        project.addComponent(config);

        // Display the entire structure
        System.out.println("File system structure:");
        project.showDetails();

        // Show individual components
        System.out.println("\nIndividual component details:");
        System.out.println("Source directory size: " + src.getSize() + " bytes");
        System.out.println("Documentation directory size: " + docs.getSize() + " bytes");
        System.out.println("Total project size: " + project.getSize() + " bytes");
    }
}
