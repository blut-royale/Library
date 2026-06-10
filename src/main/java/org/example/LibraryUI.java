package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryUI {
    public static void main(String[] args) {
        Library library = new Library();

        JFrame frame = new JFrame("My Library");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Title", "Author", "Genre", "Themes", "Type", "Series", "Format", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);
        table.getColumnModel().getColumn(3)
                        .setCellRenderer(new MultiLineCellRenderer());
        table.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer()); //Title
        table.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer()); //Author
        table.getColumnModel().getColumn(5).setCellRenderer(new MultiLineCellRenderer()); //Series
        javax.swing.table.DefaultTableCellRenderer centerRenderer =
                new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); //Series
        table.setRowHeight(50);

        table.getColumnModel().getColumn(0).setPreferredWidth(220); // Title
        table.getColumnModel().getColumn(1).setPreferredWidth(160); // Author
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Genre
        table.getColumnModel().getColumn(3).setPreferredWidth(260); // Themes
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Type
        table.getColumnModel().getColumn(5).setPreferredWidth(160); // Series
        table.getColumnModel().getColumn(6).setPreferredWidth(110); // Format
        table.getColumnModel().getColumn(7).setPreferredWidth(100); // Status

        JScrollPane scrollPane = new JScrollPane(table);

        table.setOpaque(false);
        table.setBackground(new Color(255, 255, 255, 120));

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton addButton = new JButton("Add Book");
        JButton editButton = new JButton("Edit selected");
        JButton deleteButton = new JButton("Delete book");
        JButton showAllButton = new JButton("Show all");
        JButton showReadButton = new JButton("Show all read");
        JButton showUnreadButton = new JButton("Show all unread");
        JTextField genreFilterField = new JTextField(10);
        JTextField themeFilterField = new JTextField(10);
        JButton filterGenreButton = new JButton("Filter genre");
        JButton filterThemesButton = new JButton("Filter themes");

        JLabel statusLabel = new JLabel();
        updateTable(model, library, null, statusLabel);

        addButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(frame, "Title");

            if (title == null || title.isBlank()) {
                return;
            }

            String author = JOptionPane.showInputDialog(frame, "Author");

            if (author == null || author.isBlank()) {
                return;
            }

            String typeInput = JOptionPane.showInputDialog(frame, "Type (Standalone/Series):");
            if (typeInput == null || typeInput.isBlank()) {
                return;
            }
            BookType type = BookType.valueOf(typeInput.toUpperCase());

            String seriesName = "";
            double volumeNumber = 0;

            if (type == BookType.SERIES) {
                seriesName = JOptionPane.showInputDialog(frame, "Series name:");
                if (seriesName == null || seriesName.isBlank()) {
                    return;
                }

                String volumeInput = JOptionPane.showInputDialog(frame, "Volume number:");
                if (volumeInput == null || volumeInput.isBlank()) {
                    return;
                }

                volumeNumber = Double.parseDouble(volumeInput);
            }

            String formatInput = JOptionPane.showInputDialog(frame, "Format (Paperback/Hardcover)");
            if (formatInput == null || formatInput.isBlank()) {
                return;
            }
            BookFormat format = BookFormat.valueOf(formatInput.toUpperCase());

            String statusInput = JOptionPane.showInputDialog(frame, "Status (Read/Unread/Abamdoned):");
            if (statusInput == null || statusInput.isBlank()) {
                return;
            }
            BookStatus status = BookStatus.valueOf(statusInput.toUpperCase());


            String genre = JOptionPane.showInputDialog(frame, "Genre:");
            if (genre == null) {
                return;
            }

            String themes = JOptionPane.showInputDialog(frame, "Themes:");
            if (themes == null) {
                return;
            }

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

            model.addRow(new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getThemes(),
                    book.getDisplayType(),
                    book.getSeriesInfo(),
                    book.getFormat(),
                    book.getStatus()
            });

            CsvLibraryStorage.saveBooks((library.getBooks()));
            JOptionPane.showMessageDialog(frame, "Book added and saved.");

        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a book.");
                return;
            }

            Book book = library.getBooks().get(selectedRow);
            String newTitle = JOptionPane.showInputDialog(frame, "New title:", book.getTitle());
            if (newTitle == null || newTitle.isBlank()) {
                return;
            }

            String newAuthor = JOptionPane.showInputDialog(frame, "New author:", book.getAuthor());
            if (newAuthor == null || newAuthor.isBlank()) {
                return;
            }

            String newTypeInput = JOptionPane.showInputDialog(frame, "New type (STANDALONE or SERIES):", book.getType());
            if (newTypeInput == null || newTypeInput.isBlank()) {
                return;
            }
            BookType newType = BookType.valueOf(newTypeInput.toUpperCase());

            String newSeriesName = "";
            double newVolumeNumber = 0;
            if (newType == BookType.SERIES) {
                newSeriesName = JOptionPane.showInputDialog(frame, "New series name:", book.getSeriesName());
                if (newSeriesName == null || newSeriesName.isBlank()) {
                    return;
                }

                String newVolumeInput = JOptionPane.showInputDialog(frame, "New volume number:", book.getVolumeNumber());
                if (newVolumeInput == null || newVolumeInput.isBlank()) {
                    return;
                }

                newVolumeNumber = Double.parseDouble(newVolumeInput);
            }

            String newFormatInput = JOptionPane.showInputDialog(frame, "New format (PAPERBACK or HARDCOVER):", book.getFormat());
            if (newFormatInput == null || newFormatInput.isBlank()) {
                return;
            }
            BookFormat newFormat = BookFormat.valueOf(newFormatInput.toUpperCase());

            String newStatusInput = JOptionPane.showInputDialog(frame, "New status (READ / UNREAD / ABANDONED):", book.getStatus());
            if (newStatusInput == null || newStatusInput.isBlank()) {
                return;
            }
            BookStatus newStatus = BookStatus.valueOf(newStatusInput.toUpperCase());
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setType(newType);
            book.setSeriesName(newSeriesName);
            book.setVolumeNumber(newVolumeNumber);
            book.setFormat(newFormat);
            book.setStatus(newStatus);
            model.setValueAt(book.getTitle(), selectedRow, 0);
            model.setValueAt(book.getAuthor(), selectedRow, 1);
            model.setValueAt(book.getGenre(), selectedRow, 2);
            model.setValueAt(book.getThemes(), selectedRow, 3);
            model.setValueAt(book.getDisplayType(), selectedRow, 4);
            model.setValueAt(book.getSeriesInfo(), selectedRow, 5);
            model.setValueAt(book.getFormat(), selectedRow, 6);
            model.setValueAt(book.getStatus(), selectedRow, 7);

            CsvLibraryStorage.saveBooks(library.getBooks());

            JOptionPane.showMessageDialog(frame, "Book updated and saved.");
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog( frame,
                    "Are you sure you want to delete the selected book?",
                    "Confirm delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            library.getBooks().remove(selectedRow);
            model.removeRow(selectedRow);
            CsvLibraryStorage.saveBooks(library.getBooks());
            JOptionPane.showMessageDialog(frame, "Book deleted");
        });

        showAllButton.addActionListener(e -> {
            updateTable(model, library, null, statusLabel);
        });
        showReadButton.addActionListener(e -> {
            updateTable(model, library, BookStatus.READ, statusLabel);
        });
        showUnreadButton.addActionListener(e -> {
            updateTable(model, library, BookStatus.UNREAD, statusLabel);
        });

        filterGenreButton.addActionListener(e -> {
            String genreFilter = genreFilterField.getText();
            updateTableByGenre(model, library, genreFilter, statusLabel);
        });

        filterThemesButton.addActionListener(e -> {
            String themesFilter = themeFilterField.getText();
            updateTableByThemes(model, library, themesFilter, statusLabel);
        });

        BackgroundPanel backgroundPanel = new BackgroundPanel();

        frame.setContentPane(backgroundPanel);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showAllButton);
        buttonPanel.add(showReadButton);
        buttonPanel.add(showUnreadButton);
        buttonPanel.add(new JLabel("Genre:"));
        buttonPanel.add(genreFilterField);
        buttonPanel.add(filterGenreButton);
        buttonPanel.add(new JLabel("Theme:"));
        buttonPanel.add(themeFilterField);
        buttonPanel.add(filterThemesButton);
        buttonPanel.add(statusLabel);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    private static void updateTable(DefaultTableModel model, Library library, BookStatus filter, JLabel statusLabel) {
        model.setRowCount(0);
        int visibleCount = 0;
        int read = 0;
        int unread = 0;

        for (Book book : library.getBooks()) {
            if (book.getStatus() == BookStatus.READ) {
                read++;
            }
            if (book.getStatus() == BookStatus.UNREAD) {
                unread++;
            }
            if (filter == null || book.getStatus() == filter) {
                model.addRow(new Object[]{
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getThemes(),
                        book.getDisplayType(),
                        book.getSeriesInfo(),
                        book.getFormat(),
                        book.getStatus()
                });
                visibleCount++;
            }
        }
        statusLabel.setText("Total: " + visibleCount + " | Read: " + read + " | Unread: " + unread);
    }
    private static void updateTableByGenre(DefaultTableModel model, Library library, String genreFilter, JLabel statusLabel) {
        model.setRowCount(0);

        int visibleCount = 0;
        int read = 0;
        int unread = 0;

        for (Book book : library.getBooks()) {
            if (book.getStatus() == BookStatus.READ) {
                read++;
            }

            if (book.getStatus() == BookStatus.UNREAD) {
                unread++;
            }

            if (book.getGenre().equalsIgnoreCase(genreFilter)) {
                model.addRow(new Object[]{
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getThemes(),
                        book.getDisplayType(),
                        book.getSeriesInfo(),
                        book.getFormat(),
                        book.getStatus()
                });
                visibleCount++;
            }
        }

        statusLabel.setText("Total: " + visibleCount + " | Read: " + read + " | Unread: " + unread);
    }

    private static void updateTableByThemes(DefaultTableModel model, Library library, String themesFilter, JLabel statusLabel) {
        model.setRowCount(0);

        int visibleCount = 0;
        int read = 0;
        int unread = 0;

        for (Book book : library.getBooks()) {
            if (book.getStatus() == BookStatus.READ) {
                read++;
            }

            if (book.getStatus() == BookStatus.UNREAD) {
                unread++;
            }

            if (book.getThemes().toLowerCase().contains(themesFilter.toLowerCase())) {
                model.addRow(new Object[]{
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getThemes(),
                        book.getDisplayType(),
                        book.getSeriesInfo(),
                        book.getFormat(),
                        book.getStatus()
                });
                visibleCount++;
            }
        }

        statusLabel.setText("Total: " + visibleCount + " | Read: " + read + " | Unread: " + unread);
    }
}

