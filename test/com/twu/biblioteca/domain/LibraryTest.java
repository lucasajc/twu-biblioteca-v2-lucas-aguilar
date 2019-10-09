package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LibraryTest {
    private User user;
    private Library library;
    private ArrayList<LibraryItem> libraryItemList;

    private static final String BOOK_SUCCESS_CHECKOUT_MESSAGE = "Thank you! Enjoy the book.";
    private static final String BOOK_UN_SUCCESS_CHECKOUT_MESSAGE = "Sorry, that book is not available.";
    private static final String BOOK_SUCCESS_RETURN_MESSAGE = "Thank you for returning the book.";
    private static final String BOOK_UN_SUCCESS_RETURN_MESSAGE = "This is not a valid book to return.";
    private static final String MOVIE_SUCCESS_CHECKOUT_MESSAGE = "Thank you! Enjoy the movie.";
    private static final String MOVIE_UN_SUCCESS_CHECKOUT_MESSAGE = "Sorry, that movie is not available.";
    private static final String MOVIE_SUCCESS_RETURN_MESSAGE = "Thank you for returning the movie.";
    private static final String MOVIE_UN_SUCCESS_RETURN_MESSAGE = "This is not a valid movie to return.";

    @Before
    public void setUp() {
        library = new Library(
                new Printer(),
                new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123")
        );
    }

    @Test
    public void shouldCreateACollectionFromAListOfItems() {
        assertThat(library.getItems().size(), is(15));
    }

    @Test
    public void shouldListEntireBookCollection() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.listBooks();

        assertThat(outContent.toString(), containsString("The Lord of the Rings"));
        assertThat(outContent.toString(), containsString("The Hobbit"));
    }

    @Test
    public void shouldListEntireMovieCollection() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.listMovies();

        assertThat(outContent.toString(), containsString("Interstellar"));
        assertThat(outContent.toString(), containsString("The Empire Strikes Back"));
    }

    @Test
    public void shouldListABookAuthor() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.listBooks();

        assertThat(outContent.toString(), containsString("J. R. R. Tolkien"));
    }

    @Test
    public void shouldListABookYear() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.listBooks();

        assertThat(outContent.toString(), containsString(String.valueOf(1954)));
    }

    @Test
    public void shouldCheckoutABook() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(true));
        assertThat(library.getCheckoutItems().get(key).getUser().getId(), is(library.getLoggedUser().getId()));
        assertThat(library.getCheckoutItems().get(key).getLibraryItem().getId(), is(key));
    }

    @Test
    public void shouldCheckoutAMovie() {
        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(true));
        assertThat(library.getCheckoutItems().get(key).getUser().getId(), is(library.getLoggedUser().getId()));
        assertThat(library.getCheckoutItems().get(key).getLibraryItem().getId(), is(key));
    }

    @Test
    public void shouldNotListACheckedOutBook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.listBooks();

        assertThat(outContent.toString(), not(containsString(key.toString())));
    }

    @Test
    public void shouldNotListACheckedOutMovie() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);
        library.listMovies();

        assertThat(outContent.toString(), not(containsString(key.toString())));
    }

    @Test
    public void shouldListCheckedOutBooksInformation() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.listCheckedOutBooks();

        assertThat(outContent.toString(), containsString(entry.getValue().getTitle()));
        assertThat(outContent.toString(), containsString(library.getLoggedUser().getName()));
    }

    @Test
    public void shouldListCheckedOutMoviesInformation() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);
        library.listCheckedOutMovies();

        assertThat(outContent.toString(), containsString(entry.getValue().getName()));
        assertThat(outContent.toString(), containsString(library.getLoggedUser().getName()));
    }

    @Test
    public void shouldPrintASuccessMessageOnCheckoutABook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);

        assertThat(outContent.toString(), containsString(BOOK_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintASuccessMessageOnCheckoutAMovie() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);

        assertThat(outContent.toString(), containsString(MOVIE_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenCheckoutNonExistentBook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.checkoutBookById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(BOOK_UN_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenCheckoutNonExistentMovie() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.checkoutMovieById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(MOVIE_UN_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldReturnABook() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.returnBookById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(false));
        assertThat(library.getCheckoutItems().get(key), is(nullValue()));
    }

    @Test
    public void shouldReturnAMovie() {
        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);
        library.returnMovieById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(false));
        assertThat(library.getCheckoutItems().get(key), is(nullValue()));
    }

    @Test
    public void shouldPrintASuccessMessageOnReturnABook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.returnBookById(key);

        assertThat(outContent.toString(), containsString(BOOK_SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldPrintASuccessMessageOnReturnAMovie() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);
        library.returnMovieById(key);

        assertThat(outContent.toString(), containsString(MOVIE_SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenReturnNonExistentBook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.returnBookById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(BOOK_UN_SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenReturnNonExistentMovie() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.returnMovieById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(MOVIE_UN_SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldNotCheckoutAMovieOnCallBookCheckout() {
        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(false));
    }

    @Test
    public void shouldNotCheckoutABookOnCallMovieCheckout() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(false));
    }

    @Test
    public void shouldNotReturnAMovieOnCallBookReturn() {
        HashMap.Entry<UUID, Movie> entry = library.getMovies().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutMovieById(key);
        library.checkoutBookById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(true));
    }

    @Test
    public void shouldNotReturnABookOnCallMovieReturn() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.returnMovieById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(true));
    }
}
