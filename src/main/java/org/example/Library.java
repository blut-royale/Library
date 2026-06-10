package org.example;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public Library() {
        books = CsvLibraryStorage.loadBooks();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void showBooksTable() {
        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.printf("%-25s %-25s %-15s %-25s %-15s %-15s%n",
                "Title", "Author", "Type", "Series", "Format", "Status");

        System.out.println("----------------------------------------------------------------------------------------------------------------");

        for (Book book : books) {
            System.out.printf("%-25s %-25s %-15s %-25s %-15s %-15s%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getType(),
                    book.getSeriesInfo(),
                    book.getFormat(),
                    book.getStatus());
        }
    }
}