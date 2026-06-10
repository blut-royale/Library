package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n=== MY LIBRARY ===");
            System.out.println("1. Add book");
            System.out.println("2. Show books");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Title: ");
                String title = "";
                while (title.isBlank()) {
                    System.out.println("Ttitle: ");
                    title = scanner.nextLine();

                    if (title.isBlank()) {
                        System.out.println("Ttitle cannot be empty.");
                    }
                }

                System.out.print("Author: ");
                String author = "";
                while (author.isBlank()) {
                    System.out.println("Author: ");
                    author = scanner.nextLine();

                    if (author.isBlank()) {
                        System.out.println("Author cannot be empty.");
                    }
                }

                System.out.println("Type (Standalone/Series): ");
                BookType type = null;

                while (type ==null) {
                    System.out.println("Type (Standalone/Series): ");
                    String typeInput = scanner.nextLine().toUpperCase();

                    try {
                        type = BookType.valueOf(typeInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid type.");
                    }
                }

                String seriesName = "";
                int volumeNumber = 0;

                if (type == BookType.SERIES) {
                    System.out.println("Series name: ");
                    seriesName = scanner.nextLine();

                    System.out.println("Volume number: ");
                    volumeNumber = scanner.nextInt();
                    scanner.nextLine();
                }
                System.out.println("Format (Paperback/Hardcover): ");
                BookFormat format = null;

                while (format ==null) {
                    System.out.println("Format (Paperback/Hardcover): ");
                    String formatInput = scanner.nextLine().toUpperCase();

                    try {
                        format = BookFormat.valueOf(formatInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid format.");
                    }
                }

                System.out.print("Status (Read/Unread/Abandoned): ");
                BookStatus status = null;

                while (status == null) {
                    System.out.println("Status (Read/Unread/Abandoned): ");
                    String statusInput = scanner.nextLine().toUpperCase();

                    try {
                        status = BookStatus.valueOf(statusInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Add a status.");
                    }
                }

                System.out.println("Genre: ");
                String genre = scanner.nextLine();

                System.out.println("Themes: ");
                String themes = scanner.nextLine();

                Book book = new Book(
                        title,
                        author,
                        type,
                        format,
                        status,
                        seriesName,
                        volumeNumber,
                        genre,
                        themes);
                library.addBook(book);

                System.out.println("Book added.");
            } else if (option == 2) {
                library.showBooksTable();
            } else if (option == 3) {
                CsvLibraryStorage.saveBooks(library.getBooks());
                running = false;
                System.out.println("Library updated.");
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}