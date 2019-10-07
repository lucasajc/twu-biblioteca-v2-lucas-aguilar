package com.twu.biblioteca.domain;

import java.util.UUID;

public class Book {
    private UUID id;
    private String author;
    private String title;
    private int year;
    private boolean checkedOut;

    public Book(String author, String title, int year) {
        this.id = UUID.randomUUID();
        this.author = author;
        this.title = title;
        this.year = year;
    }

    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
}
