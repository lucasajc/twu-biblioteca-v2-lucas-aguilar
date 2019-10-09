package com.twu.biblioteca.domain;

import java.util.ArrayList;
import java.util.List;

public class Printer {
    private static final String LIST_BOOKS_FORMAT = "%-25s%25s%25s%25s%n";
    private static final String LIST_MOVIES_FORMAT = "%-25s%25s%25s%25s%25s%n";
    private static final String LIST_CHECKOUT_ITEMS_FORMAT = "%-30s%30s%25s%30s%30s%30s%n";
    private static final String[] LIST_CHECKOUT_BOOKS_HEADER = new String[] { "Book ID", "Title", "User ID", "User name", "User e-mail", "User phone number" };
    private static final String[] LIST_CHECKOUT_MOVIES_HEADER = new String[] { "Movie ID", "Name", "User ID", "User name", "User e-mail", "User phone number" };
    private static final String[] LIST_BOOKS_HEADER = new String[] { "Id", "Author", "Title", "Year published" };
    private static final String[] LIST_MOVIES_HEADER = new String[] { "Id", "Name", "Year", "Director", "Rating" };

    void printSuccessCheckoutMessage(String itemType) {
        System.out.println("\nThank you! Enjoy the "+ itemType + ".");
    }

    void printUnSuccessCheckoutMessage(String itemType) {
        System.out.println("Sorry, that "+ itemType +" is not available.");
    }

    void printSuccessReturnMessage(String itemType) {
        System.out.println("Thank you for returning the "+ itemType +".");
    }

    void printUnSuccessReturnMessage(String itemType) {
        System.out.println("This is not a valid "+ itemType +" to return.");
    }

    void printBookList(ArrayList<Book> books) {
        System.out.println("\nList of books:\n");
        System.out.format(LIST_BOOKS_FORMAT, LIST_BOOKS_HEADER);

        for(Book book : books) {
            System.out.format(LIST_BOOKS_FORMAT,
                    book.getId(),
                    book.getAuthor(),
                    book.getTitle(),
                    book.getYear());
        }
    }

    void printMovieList(ArrayList<Movie> movies) {
        System.out.println("\nList of movies:\n");
        System.out.format(LIST_MOVIES_FORMAT, LIST_MOVIES_HEADER);

        for(Movie movie : movies) {
            System.out.format(LIST_MOVIES_FORMAT,
                    movie.getId(),
                    movie.getName(),
                    movie.getYear(),
                    movie.getDirector(),
                    movie.getRating());
        }
    }

    public void printCheckedOutBooks(List<CheckoutItem> checkoutItems) {
        System.out.println("\nList of checked out books:\n");
        System.out.format(LIST_CHECKOUT_ITEMS_FORMAT, LIST_CHECKOUT_BOOKS_HEADER);

        for(CheckoutItem checkoutItem : checkoutItems) {
            Book book = (Book) checkoutItem.getLibraryItem();

            System.out.format(LIST_CHECKOUT_ITEMS_FORMAT,
                    checkoutItem.getLibraryItem().getId(),
                    book.getTitle(),
                    checkoutItem.getUser().getId(),
                    checkoutItem.getUser().getName(),
                    checkoutItem.getUser().getEmail(),
                    checkoutItem.getUser().getPhoneNumber());
        }
    }

    public void printCheckedOutMovies(List<CheckoutItem> checkoutItems) {
        System.out.println("\nList of checked out movies:\n");
        System.out.format(LIST_CHECKOUT_ITEMS_FORMAT, LIST_CHECKOUT_MOVIES_HEADER);

        for(CheckoutItem checkoutItem : checkoutItems) {
            Movie movie = (Movie) checkoutItem.getLibraryItem();

            System.out.format(LIST_CHECKOUT_ITEMS_FORMAT,
                    checkoutItem.getLibraryItem().getId(),
                    movie.getName(),
                    checkoutItem.getUser().getId(),
                    checkoutItem.getUser().getName(),
                    checkoutItem.getUser().getEmail(),
                    checkoutItem.getUser().getPhoneNumber());
        }
    }

    public void printLoginHeader() {
        System.out.println("\n---------------- Login ---------------\n");
    }

    public void printLoginUsernameRequest() {
        System.out.print("Username (e-mail): ");
    }

    public void printLoginPasswordRequest() {
        System.out.print("Password: ");
    }

    public void printInvalidLoginCredentialsMessage() {
        System.out.println("\nInvalid credentials. Please try again.");
    }

    public void printUserInformation(User user) {
        System.out.println("\n----------- My information -----------");

        System.out.print("\nID: "+ user.getId());
        System.out.print("\nName: "+ user.getName());
        System.out.print("\nE-mail: "+ user.getEmail());
        System.out.print("\nPhone number: "+ user.getPhoneNumber() +"\n");
    }
}
