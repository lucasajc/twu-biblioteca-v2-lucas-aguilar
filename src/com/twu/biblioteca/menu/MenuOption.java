package com.twu.biblioteca.menu;

public enum MenuOption {
    LIST_BOOKS(MenuConstants.LIST_BOOKS_KEY,"List of books"),
    CHECKOUT_BOOK(MenuConstants.CHECKOUT_BOOK_KEY, "Checkout a book"),
    RETURN_BOOK(MenuConstants.RETURN_BOOK_KEY, "Return a book"),
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
