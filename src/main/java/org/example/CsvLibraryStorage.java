package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class CsvLibraryStorage {
    private static final String FILE_NAME = "books.csv";

    public static void saveBooks(ArrayList<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.println(
                        book.getTitle() + "," +
                                book.getAuthor() + "," +
                                book.getGenre() + "," +
                                book.getThemes() + "," +
                                book.getType() + "," +
                                book.getSeriesName() + "," +
                                book.getVolumeNumber() + "," +
                                book.getFormat() + "," +
                                book.getStatus()
                );
            }
        } catch (IOException e) {
            System.out.println("Could not save books.");

        }
    }

    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Book book;

                if (data.length == 7) {
                    book = new Book(
                        data[0],
                        data[1],
                        BookType.valueOf(data[2]),
                        BookFormat.valueOf(data[5]),
                        BookStatus.valueOf(data[6]),
                        data[3],
                        Double.parseDouble(data[4]),
                        "",
                        "");
            } else{
                book = new Book(
                        data[0],
                        data[1],
                        BookType.valueOf(data[4]),
                        BookFormat.valueOf(data[7]),
                        BookStatus.valueOf(data[8]),
                        data[5],
                        Double.parseDouble(data[6]),
                        data[2],
                        data[3]);
            }
            books.add(book);

        }
    } catch (IOException e) {
    }
    return books;
}
}


