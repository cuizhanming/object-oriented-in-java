package com.cuizhanming.oop.behavioral;

/**
 * Memento Pattern - Captures and restores object state
 * 备忘录模式 - 捕获和恢复对象状态
 */
public class MementoPattern {

    // Memento class
    public static class TextMemento {
        private final String content;
        private final int cursorPosition;
        private final long timestamp;

        public TextMemento(String content, int cursorPosition) {
            this.content = content;
            this.cursorPosition = cursorPosition;
            this.timestamp = System.currentTimeMillis();
        }

        public String getContent() { return content; }
        public int getCursorPosition() { return cursorPosition; }
        public long getTimestamp() { return timestamp; }
    }

    // Originator class
    public static class TextEditor {
        private String content = "";
        private int cursorPosition = 0;

        public void write(String text) {
            String before = content.substring(0, cursorPosition);
            String after = content.substring(cursorPosition);
            content = before + text + after;
            cursorPosition += text.length();
            System.out.println("Wrote: '" + text + "' | Content: '" + content + "'");
        }

        public void setCursor(int position) {
            this.cursorPosition = Math.max(0, Math.min(position, content.length()));
            System.out.println("Cursor moved to position: " + cursorPosition);
        }

        public void delete(int count) {
            int start = Math.max(0, cursorPosition - count);
            String before = content.substring(0, start);
            String after = content.substring(cursorPosition);
            content = before + after;
            cursorPosition = start;
            System.out.println("Deleted " + count + " characters | Content: '" + content + "'");
        }

        public TextMemento save() {
            System.out.println("Saving state...");
            return new TextMemento(content, cursorPosition);
        }

        public void restore(TextMemento memento) {
            this.content = memento.getContent();
            this.cursorPosition = memento.getCursorPosition();
            System.out.println("Restored to: '" + content + "' (cursor at " + cursorPosition + ")");
        }

        public void showStatus() {
            System.out.println("Content: '" + content + "' | Cursor: " + cursorPosition);
        }
    }

    // Caretaker class
    public static class EditorHistory {
        private final java.util.Stack<TextMemento> history = new java.util.Stack<>();
        private final int maxHistory;

        public EditorHistory(int maxHistory) {
            this.maxHistory = maxHistory;
        }

        public void saveState(TextEditor editor) {
            if (history.size() >= maxHistory) {
                history.remove(0); // Remove oldest
            }
            history.push(editor.save());
            System.out.println("State saved. History size: " + history.size());
        }

        public void undo(TextEditor editor) {
            if (!history.isEmpty()) {
                TextMemento memento = history.pop();
                editor.restore(memento);
                System.out.println("Undo completed. History size: " + history.size());
            } else {
                System.out.println("No more states to undo");
            }
        }

        public boolean canUndo() {
            return !history.isEmpty();
        }

        public int getHistorySize() {
            return history.size();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Memento Pattern Demo ===");

        TextEditor editor = new TextEditor();
        EditorHistory history = new EditorHistory(5);

        // Initial state
        editor.showStatus();
        history.saveState(editor);

        // Edit operations
        System.out.println("\n--- Editing Operations ---");
        editor.write("Hello");
        history.saveState(editor);

        editor.write(" World");
        history.saveState(editor);

        editor.setCursor(5);
        editor.write(" Java");
        history.saveState(editor);

        editor.setCursor(editor.content.length());
        editor.write("!");
        history.saveState(editor);

        System.out.println("\n--- Current State ---");
        editor.showStatus();

        // Undo operations
        System.out.println("\n--- Undo Operations ---");
        while (history.canUndo()) {
            history.undo(editor);
        }

        // Try to undo when no history
        System.out.println("\n--- Trying to undo with no history ---");
        history.undo(editor);
    }
}
