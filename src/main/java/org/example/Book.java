package org.example;

public class Book {
    private String title;
    private String author;
    private BookType type;
    private BookFormat format;
    private BookStatus status;
    private String seriesName;
    private double volumeNumber;
    private String genre;
    private  String themes;

    public Book(String title,
                String author,
                BookType type,
                BookFormat format,
                BookStatus status,
                String seriesName,
                double volumeNumber,
                String genre,
                String themes) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.format = format;
        this.status = status;
        this.seriesName = seriesName;
        this.volumeNumber = volumeNumber;
        this.genre = genre;
        this.themes = themes;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public BookType getType() {
        return type;
    }

    public BookFormat getFormat() {
        return format;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public double getVolumeNumber() {
        return volumeNumber;
    }

    public String getGenre() {
        return genre;
    }

    public String getThemes() {
        return themes;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setType(BookType type) {
        this.type = type;
    }
    public void setFormat(BookFormat format) {
        this.format = format;
    }
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
    public void setVolumeNumber(double volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getSeriesInfo() {
        if (type == BookType.STANDALONE) {
            return "-";
        }

        return seriesName + " #" + volumeNumber;
    }

    public String getDisplayType() {
        if (type == BookType.SERIES) {
            return "Series";
        }
        return "Standalone";
    }

    @Override
    public String toString() {
        return title + " - " + author;
    }
}