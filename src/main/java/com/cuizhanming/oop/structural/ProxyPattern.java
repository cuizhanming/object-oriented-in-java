package com.cuizhanming.oop.structural;

import java.util.HashMap;
import java.util.Map;

/**
 * Proxy Pattern - Provides placeholder or surrogate for another object
 * 代理模式 - 为其他对象提供占位符或代理
 */
public class ProxyPattern {

    // Subject interface
    public interface Image {
        void display();
        String getInfo();
    }

    // Real subject
    public static class RealImage implements Image {
        private final String fileName;
        private final long fileSize;

        public RealImage(String fileName) {
            this.fileName = fileName;
            this.fileSize = (long) (Math.random() * 1000000); // Simulate file size
            loadFromDisk();
        }

        private void loadFromDisk() {
            System.out.println("Loading image: " + fileName + " (" + fileSize + " bytes)");
            // Simulate expensive loading operation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void display() {
            System.out.println("Displaying image: " + fileName);
        }

        @Override
        public String getInfo() {
            return "Image: " + fileName + " (" + fileSize + " bytes)";
        }
    }

    // Virtual Proxy - Lazy loading
    public static class ImageProxy implements Image {
        private final String fileName;
        private RealImage realImage;

        public ImageProxy(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(fileName);
            }
            realImage.display();
        }

        @Override
        public String getInfo() {
            if (realImage == null) {
                return "Image proxy: " + fileName + " (not loaded)";
            }
            return realImage.getInfo();
        }
    }

    // Caching Proxy
    public static class CachingImageProxy implements Image {
        private static final Map<String, RealImage> cache = new HashMap<>();
        private final String fileName;

        public CachingImageProxy(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void display() {
            RealImage image = cache.get(fileName);
            if (image == null) {
                image = new RealImage(fileName);
                cache.put(fileName, image);
                System.out.println("Image cached");
            } else {
                System.out.println("Image loaded from cache");
            }
            image.display();
        }

        @Override
        public String getInfo() {
            RealImage image = cache.get(fileName);
            if (image == null) {
                return "Cached image proxy: " + fileName + " (not in cache)";
            }
            return image.getInfo() + " (cached)";
        }
    }

    // Protection Proxy
    public static class ProtectedImageProxy implements Image {
        private final String fileName;
        private final String userRole;
        private RealImage realImage;

        public ProtectedImageProxy(String fileName, String userRole) {
            this.fileName = fileName;
            this.userRole = userRole;
        }

        private boolean hasAccess() {
            return "admin".equals(userRole) || "user".equals(userRole);
        }

        @Override
        public void display() {
            if (!hasAccess()) {
                System.out.println("Access denied: Insufficient permissions to view " + fileName);
                return;
            }

            if (realImage == null) {
                realImage = new RealImage(fileName);
            }
            realImage.display();
        }

        @Override
        public String getInfo() {
            if (!hasAccess()) {
                return "Protected image: " + fileName + " (access denied)";
            }

            if (realImage == null) {
                realImage = new RealImage(fileName);
            }
            return realImage.getInfo();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Proxy Pattern Demo ===");

        // Virtual Proxy - Lazy loading
        System.out.println("\n1. Virtual Proxy (Lazy Loading):");
        Image image1 = new ImageProxy("photo1.jpg");
        System.out.println(image1.getInfo()); // Not loaded yet
        image1.display(); // Now it loads
        System.out.println(image1.getInfo()); // Now shows loaded info

        // Caching Proxy
        System.out.println("\n2. Caching Proxy:");
        Image image2 = new CachingImageProxy("photo2.jpg");
        image2.display(); // First time - loads and caches
        image2.display(); // Second time - from cache

        // Protection Proxy
        System.out.println("\n3. Protection Proxy:");
        Image adminImage = new ProtectedImageProxy("secret.jpg", "admin");
        Image guestImage = new ProtectedImageProxy("secret.jpg", "guest");

        adminImage.display(); // Has access
        guestImage.display(); // Access denied

        System.out.println(adminImage.getInfo());
        System.out.println(guestImage.getInfo());
    }
}
