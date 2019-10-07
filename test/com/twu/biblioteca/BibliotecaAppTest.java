package com.twu.biblioteca;

import com.twu.biblioteca.domain.Book;
import com.twu.biblioteca.domain.LibraryItem;
import com.twu.biblioteca.domain.Library;
import com.twu.biblioteca.domain.Movie;
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
    private ArrayList<LibraryItem> libraryItemList;

    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore.\n";
    private static final String INVALID_CHECKOUT_MESSAGE = "Please select a valid ID!";

    private void initializeMenu() {
        MenuOption listOfBooksOption = MenuOption.LIST_BOOKS;
        ArrayList<MenuOption> options = new ArrayList<MenuOption>();
        options.add(listOfBooksOption);
        menu = new Menu(options);
    }

    private void initializeLibrary() {
        libraryItemList = new ArrayList<LibraryItem>();

        libraryItemList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        libraryItemList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        libraryItemList.add(new Movie("Interstellar", 2014, "Christopher Nolan", 8.6));
        libraryItemList.add(new Movie("The Empire Strikes Back", 1980, "Irvin Kershner", 8.7));

        library = new Library(libraryItemList);
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
    public void shouldListMoviesWhenUserChoosesItOnMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.selectMenuOption("2");
        BibliotecaApp.processUserInput();


        assertThat(outContent.toString(), containsString("Interstellar"));
        assertThat(outContent.toString(), containsString("The Empire Strikes Back"));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenCheckoutInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.checkoutItemById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenReturnInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.returnItemById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }
}