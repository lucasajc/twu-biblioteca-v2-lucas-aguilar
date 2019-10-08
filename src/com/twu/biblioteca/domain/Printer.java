package com.twu.biblioteca.domain;

import java.util.ArrayList;

public class Printer {
    private static final String LIST_BOOKS_FORMAT = "%-25s%25s%25s%25s%n";
    private static final String LIST_MOVIES_FORMAT = "%-25s%25s%25s%25s%25s%n";
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
}
