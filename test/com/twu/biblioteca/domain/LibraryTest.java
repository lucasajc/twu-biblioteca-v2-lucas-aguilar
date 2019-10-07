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
    private ArrayList<LibraryItem> libraryItemList;

    private static final String SUCCESS_CHECKOUT_MESSAGE = "Thank you! Enjoy the book.";
    private static final String UN_SUCCESS_CHECKOUT_MESSAGE = "Sorry, that book is not available.";
    private static final String SUCCESS_RETURN_MESSAGE = "Thank you for returning the book.";
    private static final String UN_SUCCESS_RETURN_MESSAGE = "This is not a valid book to return.";

    @Before
    public void setUp() {
        libraryItemList = new ArrayList<LibraryItem>();

        libraryItemList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        libraryItemList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        libraryItemList.add(new Movie("Interstellar", 2014, "Christopher Nolan", 8.6));
        libraryItemList.add(new Movie("The Empire Strikes Back", 1980, "Irvin Kershner", 8.7));

        library = new Library(libraryItemList);
    }

    @Test
    public void shouldCreateACollectionFromAListOfItems() {
        assertThat(library.getItems().size(), is(4));
    }

    @Test
    public void shouldCreateADefaultCollectionOfLibraryItems() {
        Library defaultLibrary = new Library();

        assertThat(defaultLibrary.getItems().size(), is(15));
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
    public void shouldChecksIfAnItemExistsById() {
        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        assertThat(library.isItemExists(key), is(true));
    }

    @Test
    public void shouldChecksIfAnItemDoesNotExistsById() {
        assertThat(library.isItemExists(UUID.randomUUID()), is(false));
    }

    @Test
    public void shouldCheckoutAnItem() {
        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutItemById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(true));
    }

    @Test
    public void shouldNotListACheckedOutItem() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutItemById(key);
        library.listBooks();

        assertThat(outContent.toString(), not(containsString(key.toString())));
    }

    @Test
    public void shouldPrintASuccessMessageOnCheckoutAnItem() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutItemById(key);

        assertThat(outContent.toString(), containsString(SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenCheckoutNonExistentItem() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.checkoutItemById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(UN_SUCCESS_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldReturnAnItem() {
        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutItemById(key);
        library.returnItemById(key);

        assertThat(library.getItems().get(key).isCheckedOut(), is(false));
    }

    @Test
    public void shouldPrintASuccessMessageOnReturnAnItem() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HashMap.Entry<UUID, LibraryItem> entry = library.getItems().entrySet().iterator().next();
        UUID key = entry.getKey();

        library.checkoutItemById(key);
        library.returnItemById(key);

        assertThat(outContent.toString(), containsString(SUCCESS_RETURN_MESSAGE));
    }

    @Test
    public void shouldPrintAUnSuccessfulMessageWhenReturnNonExistentItem() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        library.returnItemById(UUID.randomUUID());

        assertThat(outContent.toString(), containsString(UN_SUCCESS_RETURN_MESSAGE));
    }
}
