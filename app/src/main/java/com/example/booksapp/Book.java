package com.example.booksapp;

public class Book {
    private String title;
    private String author;
    private String description;
    private String thumbnailUrl;

    public Book(String title, String author, String description, String thumbnailUrl) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}
