package com.cuizhanming.oop.structural;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Pattern - Minimizes memory usage with shared objects
 * 享元模式 - 通过共享对象最小化内存使用
 */
public class FlyweightPattern {

    // Flyweight interface
    public interface CharacterFlyweight {
        void display(int row, int column, String font, int size);
    }

    // Concrete flyweight
    public static class Character implements CharacterFlyweight {
        private final char symbol; // Intrinsic state (shared)

        public Character(char symbol) {
            this.symbol = symbol;
        }

        @Override
        public void display(int row, int column, String font, int size) {
            // Extrinsic state passed as parameters
            System.out.printf("Character '%c' at (%d,%d) in %s font, size %d%n",
                symbol, row, column, font, size);
        }
    }

    // Flyweight factory
    public static class CharacterFactory {
        private static final Map<java.lang.Character, CharacterFlyweight> characters = new HashMap<>();

        public static CharacterFlyweight getCharacter(char symbol) {
            CharacterFlyweight character = characters.get(symbol);
            if (character == null) {
                character = new Character(symbol);
                characters.put(symbol, character);
                System.out.println("Creating new flyweight for: " + symbol);
            }
            return character;
        }

        public static int getCreatedFlyweightsCount() {
            return characters.size();
        }
    }

    // Context class that uses flyweights
    public static class Document {
        private static class CharacterContext {
            private final CharacterFlyweight character;
            private final int row, column;
            private final String font;
            private final int size;

            public CharacterContext(CharacterFlyweight character, int row, int column, String font, int size) {
                this.character = character;
                this.row = row;
                this.column = column;
                this.font = font;
                this.size = size;
            }

            public void display() {
                character.display(row, column, font, size);
            }
        }

        private final java.util.List<CharacterContext> characters = new java.util.ArrayList<>();

        public void addCharacter(char symbol, int row, int column, String font, int size) {
            CharacterFlyweight flyweight = CharacterFactory.getCharacter(symbol);
            characters.add(new CharacterContext(flyweight, row, column, font, size));
        }

        public void display() {
            System.out.println("Document content:");
            characters.forEach(CharacterContext::display);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Flyweight Pattern Demo ===");

        Document document = new Document();

        // Add characters to document
        String text = "Hello World! Hello Java!";
        int row = 1, column = 1;

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                column++;
                continue;
            }

            document.addCharacter(c, row, column, "Arial", 12);
            column++;

            if (column > 20) {
                row++;
                column = 1;
            }
        }

        System.out.println("\nTotal unique flyweights created: " + CharacterFactory.getCreatedFlyweightsCount());
        System.out.println("Total characters in document: " + text.replace(" ", "").length());

        System.out.println();
        document.display();
    }
}
