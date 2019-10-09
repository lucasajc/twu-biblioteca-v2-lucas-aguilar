package com.twu.biblioteca.domain;

public class CheckoutItem {
    private User user;
    private LibraryItem libraryItem;

    public CheckoutItem(User user, LibraryItem libraryItem) {
        this.user = user;
        this.libraryItem = libraryItem;
    }

    public User getUser() {
        return user;
    }

    public LibraryItem getLibraryItem() {
        return libraryItem;
    }
}
