package com.cuizhanming.oop.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * Iterator Pattern - Provides way to access elements sequentially
 * 迭代器模式 - 提供顺序访问元素的方法
 */
public class IteratorPattern {

    // Iterator interface
    public interface Iterator<T> {
        boolean hasNext();
        T next();
        void remove();
    }

    // Aggregate interface
    public interface Iterable<T> {
        Iterator<T> createIterator();
    }

    // Concrete aggregate
    public static class BookCollection implements Iterable<Book> {
        private final List<Book> books = new ArrayList<>();

        public void addBook(Book book) {
            books.add(book);
        }

        public void removeBook(Book book) {
            books.remove(book);
        }

        public int size() {
            return books.size();
        }

        @Override
        public Iterator<Book> createIterator() {
            return new BookIterator();
        }

        // Concrete iterator
        private class BookIterator implements Iterator<Book> {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < books.size();
            }

            @Override
            public Book next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                return books.get(position++);
            }

            @Override
            public void remove() {
                if (position <= 0) {
                    throw new IllegalStateException("Cannot remove before next() is called");
                }
                books.remove(--position);
            }
        }
    }

    // Element class
    public static class Book {
        private final String title;
        private final String author;
        private final String isbn;
        private final int year;

        public Book(String title, String author, String isbn, int year) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.year = year;
        }

        @Override
        public String toString() {
            return String.format("\"%s\" by %s (%d) - ISBN: %s", title, author, year, isbn);
        }

        // Getters
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getIsbn() { return isbn; }
        public int getYear() { return year; }
    }

    public static void main(String[] args) {
        System.out.println("=== Iterator Pattern Demo ===");

        BookCollection library = new BookCollection();

        // Add books to collection
        library.addBook(new Book("Design Patterns", "Gang of Four", "978-0201633610", 1994));
        library.addBook(new Book("Clean Code", "Robert Martin", "978-0132350884", 2008));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2017));
        library.addBook(new Book("Spring in Action", "Craig Walls", "978-1617294945", 2018));

        System.out.println("Library has " + library.size() + " books\n");

        // Iterate through books
        System.out.println("All books in library:");
        Iterator<Book> iterator = library.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println("  " + book);
        }

        // Demonstrate removal during iteration
        System.out.println("\nRemoving books published before 2010:");
        iterator = library.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getYear() < 2010) {
                System.out.println("  Removing: " + book.getTitle());
                iterator.remove();
            }
        }

        System.out.println("\nRemaining books:");
        iterator = library.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println("  " + book);
        }

        System.out.println("\nLibrary now has " + library.size() + " books");
    }
}
