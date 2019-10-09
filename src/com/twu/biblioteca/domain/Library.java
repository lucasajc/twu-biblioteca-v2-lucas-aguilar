package com.twu.biblioteca.domain;

import com.twu.biblioteca.constants.LibraryItemTypes;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private HashMap<UUID, LibraryItem> items;
    private HashMap<String, User> users;
    private User loggedUser;
    private HashMap<UUID, CheckoutItem> checkoutItems;
    private Printer printer;

    public Library(Printer printer, User loggedUser) {
        ArrayList<LibraryItem> libraryItemList = new ArrayList<LibraryItem>();
        ArrayList<User> userList = new ArrayList<User>(initUsers());

        libraryItemList.addAll(initLibraryBooks());
        libraryItemList.addAll(initLibraryMovies());

        fillLibraryCollection(libraryItemList);
        fillLibraryUsers(userList);

        this.printer = printer;
        this.loggedUser = loggedUser;
        this.checkoutItems = new HashMap<UUID, CheckoutItem>();
    }

    private List<Book> initLibraryBooks() {
        return Arrays.asList(
                new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954),
                new Book("J. R. R. Tolkien", "The Hobbit", 1937),
                new Book("J. R. R. Tolkien", "The Silmarillion", 1977),
                new Book("J. R. R. Tolkien", "The Fall of Gondolin", 2018),
                new Book("Conn Iggulden", "Wolf of the Plains", 2007),
                new Book("Conn Iggulden", "Lords of the Bow", 2008),
                new Book("Conn Iggulden", "Bones of the Hills", 2008),
                new Book("Conn Iggulden", "Conqueror", 2011),
                new Book("George R. R. Martin", "A Game of Thrones", 1996),
                new Book("George R. R. Martin", "A Clash of Kings", 1999),
                new Book("George R. R. Martin", "A Storm of Swords", 2000),
                new Book("George R. R. Martin", "A Feast for Crows", 2005),
                new Book("George R. R. Martin", "A Dance with Dragons", 2011)
        );
    }

    private List<Movie> initLibraryMovies() {
        return Arrays.asList(
                new Movie("Interstellar", 2014, "Christopher Nolan", 8.6),
                new Movie("The Empire Strikes Back", 1980, "Irvin Kershner", 8.7)
        );
    }

    private List<User> initUsers() {
        return Arrays.asList(
                new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123"),
                new User("123-5678", "Example user 2", "user2@email.com", "+55 12 1234 5678", "password321")
        );
    }

    private void fillLibraryCollection(ArrayList<LibraryItem> libraryItemList) {
        this.items = new HashMap<UUID, LibraryItem>();

        for (LibraryItem libraryItem : libraryItemList) {
            this.items.put(libraryItem.getId(), libraryItem);
        }
    }

    private void fillLibraryUsers(ArrayList<User> userList) {
        this.users = new HashMap<String, User>();

        for (User user : userList) {
            this.users.put(user.getId(), user);
        }
    }

    public void listBooks() {
        printer.printBookList(getAvailableBooks());
    }

    public void listMovies() {
        printer.printMovieList(getAvailableMovies());
    }

    public void listCheckedOutBooks() {
        printer.printCheckedOutBooks(
                checkoutItems.values().stream()
                .filter(item -> item.getLibraryItem().getType().equals(LibraryItemTypes.BOOK))
                .collect(Collectors.toList())
        );
    }

    public void listCheckedOutMovies() {
        printer.printCheckedOutMovies(
                checkoutItems.values().stream()
                        .filter(item -> item.getLibraryItem().getType().equals(LibraryItemTypes.MOVIE))
                        .collect(Collectors.toList())
        );
    }

    public void checkoutBookById(UUID id) {
        if(isCheckoutValid(id, LibraryItemTypes.BOOK)) {
            printer.printUnSuccessCheckoutMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).checkout();
        checkoutItems.put(id, new CheckoutItem(loggedUser, items.get(id)));
        printer.printSuccessCheckoutMessage(LibraryItemTypes.BOOK);
    }

    public void checkoutMovieById(UUID id) {
        if(isCheckoutValid(id, LibraryItemTypes.MOVIE)) {
            printer.printUnSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).checkout();
        checkoutItems.put(id, new CheckoutItem(loggedUser, items.get(id)));
        printer.printSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
    }

    public void returnBookById(UUID id) {
        if(isReturnValid(id, LibraryItemTypes.BOOK)) {
            printer.printUnSuccessReturnMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).deliver();
        checkoutItems.remove(id);
        printer.printSuccessReturnMessage(LibraryItemTypes.BOOK);
    }

    public void returnMovieById(UUID id) {
        if(isReturnValid(id, LibraryItemTypes.MOVIE)) {
            printer.printUnSuccessReturnMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).deliver();
        checkoutItems.remove(id);
        printer.printSuccessReturnMessage(LibraryItemTypes.MOVIE);
    }

    Map<UUID, Book> getBooks() {
        return items.values().stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .collect(Collectors.toMap(item -> ((Book) item).getId(), item -> ((Book) item)));
    }

    Map<UUID, Movie> getMovies() {
        return items.values().stream()
                .filter(item -> item instanceof Movie)
                .map(item -> (Movie) item)
                .collect(Collectors.toMap(item -> ((Movie) item).getId(), item -> ((Movie) item)));
    }

    private boolean itemExists(UUID id) {
        return items.get(id) != null;
    }

    private boolean isCheckoutValid(UUID id, String itemType) {
        return !itemExists(id) || items.get(id).isCheckedOut() || !items.get(id).getType().equals(itemType);
    }

    private boolean isReturnValid(UUID id, String itemType) {
        return !itemExists(id) || !items.get(id).isCheckedOut() || !items.get(id).getType().equals(itemType);
    }

    private ArrayList<Book> getAvailableBooks() {
        return (ArrayList<Book>) getBooks().values().stream()
                .map(item -> (Book) item)
                .filter(item -> !item.isCheckedOut())
                .collect(Collectors.toList());
    }

    private ArrayList<Movie> getAvailableMovies() {
        return (ArrayList<Movie>) getMovies().values().stream()
                .map(item -> (Movie) item)
                .filter(item -> !item.isCheckedOut())
                .collect(Collectors.toList());
    }

    public HashMap<UUID, LibraryItem> getItems() {
        return items;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public HashMap<UUID, CheckoutItem> getCheckoutItems() {
        return checkoutItems;
    }
}
