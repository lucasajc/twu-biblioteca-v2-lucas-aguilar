package com.twu.biblioteca.domain;

import java.util.UUID;

public class Movie implements LibraryItem {
    private UUID id;
    private String name;
    private int year;
    private String director;
    private String rating;
    private boolean checkedOut;

    public Movie(String name, int year, String director) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.year = year;
        this.director = director;
        this.checkedOut = false;
        this.rating = "unrated";
    }

    public Movie(String name, int year, String director, double rating) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.year = year;
        this.director = director;
        this.checkedOut = false;
        if(rating > 0 && rating <=10) {
            this.rating = String.valueOf(rating);
        } else {
            this.rating = "unrated";
        }
    }

    @Override
    public void checkout() {
        this.checkedOut = true;
    }

    @Override
    public void deliver() {
        this.checkedOut = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getRating() {
        return rating;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }
}
