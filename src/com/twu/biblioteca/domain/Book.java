package com.twu.biblioteca.domain;

import com.twu.biblioteca.constants.LibraryItemTypes;

import java.util.UUID;

public class Book implements LibraryItem {
    private UUID id;
    private String author;
    private String title;
    private int year;
    private boolean checkedOut;
    private String type;

    public Book(String author, String title, int year) {
        this.id = UUID.randomUUID();
        this.author = author;
        this.title = title;
        this.year = year;
        this.type = LibraryItemTypes.BOOK;
    }

    @Override
    public void checkout() {
        this.checkedOut = true;
    }

    @Override
    public void deliver() {
        this.checkedOut = false;
    }

    @Override
    public String getType() {
        return type;
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
}
