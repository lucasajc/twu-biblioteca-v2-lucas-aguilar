package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class PrinterTest {

    private Printer printer;

    @Before
    public void setUp() {
        printer = new Printer();
    }

    @Test
    public void shouldPrintSuccessCheckoutMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.printSuccessCheckoutMessage(LibraryItemTypes.BOOK);
        assertThat(outContent.toString(), containsString("\nThank you! Enjoy the book."));

        printer.printSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
        assertThat(outContent.toString(), containsString("\nThank you! Enjoy the movie."));
    }

    @Test
    public void shouldPrintUnSuccessCheckoutMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.printUnSuccessCheckoutMessage(LibraryItemTypes.BOOK);
        assertThat(outContent.toString(), containsString("Sorry, that book is not available."));

        printer.printUnSuccessCheckoutMessage(LibraryItemTypes.MOVIE);
        assertThat(outContent.toString(), containsString("Sorry, that movie is not available."));
    }

    @Test
    public void shouldPrintSuccessReturnMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.printSuccessReturnMessage(LibraryItemTypes.BOOK);
        assertThat(outContent.toString(), containsString("Thank you for returning the book."));

        printer.printSuccessReturnMessage(LibraryItemTypes.MOVIE);
        assertThat(outContent.toString(), containsString("Thank you for returning the movie."));
    }

    @Test
    public void shouldPrintUnSuccessReturnMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        printer.printUnSuccessReturnMessage(LibraryItemTypes.BOOK);
        assertThat(outContent.toString(), containsString("This is not a valid book to return."));

        printer.printUnSuccessReturnMessage(LibraryItemTypes.MOVIE);
        assertThat(outContent.toString(), containsString("This is not a valid movie to return."));
    }

    @Test
    public void shouldListABookCollection() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ArrayList<Book> books = new ArrayList<>();

        books.addAll(Arrays.asList(
                new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954),
                new Book("J. R. R. Tolkien", "The Hobbit", 1937)
        ));

        printer.printBookList(books);

        assertThat(outContent.toString(), containsString("The Lord of the Rings"));
        assertThat(outContent.toString(), containsString("The Hobbit"));
    }

    @Test
    public void shouldListAMovieCollection() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ArrayList<Movie> movies = new ArrayList<>();

        movies.addAll(Arrays.asList(
                new Movie("Interstellar", 2014, "Christopher Nolan", 8.6),
                new Movie("The Empire Strikes Back", 1980, "Irvin Kershner", 8.7)
        ));

        printer.printMovieList(movies);

        assertThat(outContent.toString(), containsString("Interstellar"));
        assertThat(outContent.toString(), containsString("The Empire Strikes Back"));
    }
}
