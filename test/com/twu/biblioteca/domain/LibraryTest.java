package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LibraryTest {

    private Library library;
    private ArrayList<Book> bookList = new ArrayList<Book>();

    private static final String SUCCESS_CHECKOUT_MESSAGE = "Thank you! Enjoy the book.";
    private static final String UN_SUCCESS_CHECKOUT_MESSAGE = "Sorry, that book is not available.";
    private static final String SUCCESS_RETURN_MESSAGE = "Thank you for returning the book.";
    private static final String UN_SUCCESS_RETURN_MESSAGE = "This is not a valid book to return.";

    @Before
    public void setUp() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        bookList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        library = new Library(bookList);
    }

    @Test
    public void shouldCreateACollectionFromAListOfBooks() {
        assertThat(library.getBooks().size(), is(2));
    }

    @Test
    public void shouldCreateADefaultCollectionOfBooks() {
        Library defaultLibrary = new Library();

        assertThat(defaultLibrary.getBooks().size(), is(13));
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
    public void shouldChecksIfABookExistsById() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        assertThat(library.isBookExists(key), is(true));
    }

    @Test
    public void shouldChecksIfABookDoesNotExistsById() {
        assertThat(library.isBookExists(UUID.randomUUID()), is(false));
    }

    @Test
    public void shouldCheckoutABook() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);

        assertThat(library.getBooks().get(key).isCheckedOut(), is(true));
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
    public void shouldPrintASuccessMessageOnCheckoutABook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);

        assertThat(outContent.toString(), containsString(SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenCheckoutNonExistentBook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.checkoutBookById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(UN_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldReturnABook() {
        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.returnBookById(key);

        assertThat(library.getBooks().get(key).isCheckedOut(), is(false));
    }

    @Test
    public void shouldPrintASuccessMessageOnReturnABook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, Book> entry = library.getBooks().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutBookById(key);
        library.returnBookById(key);

        assertThat(outContent.toString(), containsString(SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenReturnNonExistentBook() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.returnBookById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(UN_SUCCESS_RETURN_MESSAGE));
    }
}
