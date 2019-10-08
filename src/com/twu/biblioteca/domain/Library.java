package com.twu.biblioteca.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Library {
    private HashMap<UUID, LibraryItem> items;
    private static final String LIST_BOOKS_FORMAT = "%-25s%25s%25s%25s%n";
    private static final String LIST_MOVIES_FORMAT = "%-25s%25s%25s%25s%25s%n";
    private static final String[] LIST_BOOKS_HEADER = new String[] { "Id", "Author", "Title", "Year published" };
    private static final String[] LIST_MOVIES_HEADER = new String[] { "Id", "Name", "Year", "Director", "Rating" };

    public Library(ArrayList<LibraryItem> libraryItemList) {
        fillLibraryCollection(libraryItemList);
    }

    public Library() {
        ArrayList<LibraryItem> libraryItemList = new ArrayList<LibraryItem>();

        libraryItemList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        libraryItemList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        libraryItemList.add(new Book("J. R. R. Tolkien", "The Silmarillion", 1977));
        libraryItemList.add(new Book("J. R. R. Tolkien", "The Fall of Gondolin", 2018));
        libraryItemList.add(new Book("Conn Iggulden", "Wolf of the Plains", 2007));
        libraryItemList.add(new Book("Conn Iggulden", "Lords of the Bow", 2008));
        libraryItemList.add(new Book("Conn Iggulden", "Bones of the Hills", 2008));
        libraryItemList.add(new Book("Conn Iggulden", "Conqueror", 2011));
        libraryItemList.add(new Book("George R. R. Martin", "A Game of Thrones", 1996));
        libraryItemList.add(new Book("George R. R. Martin", "A Clash of Kings", 1999));
        libraryItemList.add(new Book("George R. R. Martin", "A Storm of Swords", 2000));
        libraryItemList.add(new Book("George R. R. Martin", "A Feast for Crows", 2005));
        libraryItemList.add(new Book("George R. R. Martin", "A Dance with Dragons", 2011));
        libraryItemList.add(new Movie("Interstellar", 2014, "Christopher Nolan", 8.6));
        libraryItemList.add(new Movie("The Empire Strikes Back", 1980, "Irvin Kershner", 8.7));

        fillLibraryCollection(libraryItemList);
    }

    private void fillLibraryCollection(ArrayList<LibraryItem> libraryItemList) {
        this.items = new HashMap<UUID, LibraryItem>();

        for (LibraryItem libraryItem : libraryItemList) {
            this.items.put(libraryItem.getId(), libraryItem);
        }
    }

    public void listBooks() {
        System.out.println("\nList of books:\n");
        System.out.format(LIST_BOOKS_FORMAT, LIST_BOOKS_HEADER);

        for(Book book : getAvailableBooks()) {
            System.out.format(LIST_BOOKS_FORMAT,
                    book.getId(),
                    book.getAuthor(),
                    book.getTitle(),
                    book.getYear());
        }
    }

    public void listMovies() {
        System.out.println("\nList of movies:\n");
        System.out.format(LIST_MOVIES_FORMAT, LIST_MOVIES_HEADER);

        for(Movie movie : getAvailableMovies()) {
            System.out.format(LIST_MOVIES_FORMAT,
                    movie.getId(),
                    movie.getName(),
                    movie.getYear(),
                    movie.getDirector(),
                    movie.getRating());
        }
    }

    public void checkoutBookById(UUID id) {
        if(isCheckoutValid(id)) {
            printUnSuccessCheckoutMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).checkout();
        printSuccessCheckoutMessage(LibraryItemTypes.BOOK);
    }

    public void checkoutMovieById(UUID id) {
        if(isCheckoutValid(id)) {
            printUnSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).checkout();
        printSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
    }

    public void returnBookById(UUID id) {
        if(isReturnValid(id)) {
            printUnSuccessReturnMessage(LibraryItemTypes.BOOK);
            return;
        }
        items.get(id).deliver();
        printSuccessReturnMessage(LibraryItemTypes.BOOK);
    }

    public void returnMovieById(UUID id) {
        if(isReturnValid(id)) {
            printUnSuccessReturnMessage(LibraryItemTypes.MOVIE);
            return;
        }
        items.get(id).deliver();
        printSuccessReturnMessage(LibraryItemTypes.MOVIE);
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

    boolean isItemExists(UUID id) {
        return items.get(id) != null;
    }

    private boolean isCheckoutValid(UUID id) {
        return !isItemExists(id) || items.get(id).isCheckedOut();
    }

    private boolean isReturnValid(UUID id) {
        return !isItemExists(id) || !items.get(id).isCheckedOut();
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

    private void printSuccessCheckoutMessage(String itemType) {
        System.out.println("\nThank you! Enjoy the "+ itemType + ".");
    }

    private void printUnSuccessCheckoutMessage(String itemType) {
        System.out.println("Sorry, that "+ itemType +" is not available.");
    }

    private void printSuccessReturnMessage(String itemType) {
        System.out.println("Thank you for returning the "+ itemType +".");
    }

    private void printUnSuccessReturnMessage(String itemType) {
        System.out.println("This is not a valid "+ itemType +" to return.");
    }

    public HashMap<UUID, LibraryItem> getItems() {
        return items;
    }
}
