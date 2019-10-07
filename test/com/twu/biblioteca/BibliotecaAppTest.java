package com.twu.biblioteca;

import com.twu.biblioteca.domain.Book;
import com.twu.biblioteca.domain.Library;
import com.twu.biblioteca.menu.Menu;
import com.twu.biblioteca.menu.MenuOption;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BibliotecaAppTest {

    private Menu menu;
    private Library library;
    private ArrayList<Book> bookList = new ArrayList<Book>();

    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore.\n";
    private static final String INVALID_CHECKOUT_MESSAGE = "Please select a valid ID!";

    private void initializeMenu() {
        MenuOption listOfBooksOption = MenuOption.LIST_BOOKS;
        ArrayList<MenuOption> options = new ArrayList<MenuOption>();
        options.add(listOfBooksOption);
        menu = new Menu(options);
    }

    private void initializeLibrary() {
        bookList = new ArrayList<Book>();
        bookList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        bookList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        library = new Library(bookList);
    }

    @Before
    public void setUp() {
        initializeLibrary();
        initializeMenu();

        BibliotecaApp.setLibrary(library);
        BibliotecaApp.setMenu(menu);
    }

    @Test
    public void shouldPrintAWelcomingMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.printWelcomeMessage();

        assertThat(outContent.toString(), is(WELCOME_MESSAGE));
    }

    @Test
    public void shouldListBooksWhenUserChoosesItOnMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.selectMenuOption("1");
        BibliotecaApp.processUserInput();


        assertThat(outContent.toString(), containsString("The Lord of the Rings"));
        assertThat(outContent.toString(), containsString("The Hobbit"));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenCheckoutInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.checkoutBookById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenReturnInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.returnBookById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }
}