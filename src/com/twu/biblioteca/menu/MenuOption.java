package com.twu.biblioteca.menu;

public enum MenuOption {
    LIST_BOOKS(MenuConstants.LIST_BOOKS_KEY,"List of books"),
    LIST_MOVIES(MenuConstants.LIST_MOVIES_KEY, "List of movies"),
    CHECKOUT_BOOK(MenuConstants.CHECKOUT_BOOK_KEY, "Checkout a book"),
    RETURN_BOOK(MenuConstants.RETURN_BOOK_KEY, "Return a book"),
    CHECKOUT_MOVIE(MenuConstants.CHECKOUT_MOVIE_KEY, "Checkout a movie"),
    RETURN_MOVIE(MenuConstants.RETURN_MOVIE_KEY, "Return a movie"),
    LIST_CHECKED_OUT_BOOKS(MenuConstants.LIST_CHECKED_OUT_BOOKS_KEY, "List checked out books"),
    LIST_CHECKED_OUT_MOVIES(MenuConstants.LIST_CHECKED_OUT_MOVIES_KEY, "List checked out movies"),
    PRINT_USER_INFORMATION(MenuConstants.PRINT_USER_INFORMATION_KEY, "My information"),
    EXIT_APPLICATION(MenuConstants.EXIT_APPLICATION_KEY, "Exit application");

    private int menuKey;
    private String description;

    MenuOption(int menuKey, String description) {
        this.menuKey = menuKey;
        this.description = description;
    }

    public int getMenuKey() {
        return menuKey;
    }

    public String getDescription() {
        return description;
    }
}
