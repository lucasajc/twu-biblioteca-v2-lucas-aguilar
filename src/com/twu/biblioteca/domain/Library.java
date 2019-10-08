package com.twu.biblioteca.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private HashMap<UUID, LibraryItem> items;
    private Printer printer;

    public Library(Printer printer) {
        ArrayList<LibraryItem> libraryItemList = new ArrayList<LibraryItem>();

        libraryItemList.addAll(initLibraryBooks());
        libraryItemList.addAll(initLibraryMovies());

        fillLibraryCollection(libraryItemList);

        this.printer = printer;
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

    private void fillLibraryCollection(ArrayList<LibraryItem> libraryItemList) {
        this.items = new HashMap<UUID, LibraryItem>();

        for (LibraryItem libraryItem : libraryItemList) {
            this.items.put(libraryItem.getId(), libraryItem);
        }
    }

    public void listBooks() {
        printer.printBookList(getAvailableBooks());
    }

    public void listMovies() {
        printer.printMovieList(getAvailableMovies());
    }

    public void checkoutBookById(UUID id) {
        if(isCheckoutValid(id, LibraryItemTypes.BOOK)) {
            printer.printUnSuccessCheckoutMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).checkout();
        printer.printSuccessCheckoutMessage(LibraryItemTypes.BOOK);
    }

    public void checkoutMovieById(UUID id) {
        if(isCheckoutValid(id, LibraryItemTypes.MOVIE)) {
            printer.printUnSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).checkout();
        printer.printSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
    }

    public void returnBookById(UUID id) {
        if(isReturnValid(id, LibraryItemTypes.BOOK)) {
            printer.printUnSuccessReturnMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).deliver();
        printer.printSuccessReturnMessage(LibraryItemTypes.BOOK);
    }

    public void returnMovieById(UUID id) {
        if(isReturnValid(id, LibraryItemTypes.MOVIE)) {
            printer.printUnSuccessReturnMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).deliver();
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
}
