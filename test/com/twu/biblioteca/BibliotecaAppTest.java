package com.twu.biblioteca;

import com.twu.biblioteca.domain.*;
import com.twu.biblioteca.menu.Menu;
import com.twu.biblioteca.menu.MenuOption;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BibliotecaAppTest {

    private Menu menu;
    private Library library;
    private Authentication auth;

    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore.\n";
    private static final String INVALID_CHECKOUT_MESSAGE = "Please select a valid ID!";

    private void initializeMenu() {
        MenuOption listOfBooksOption = MenuOption.LIST_BOOKS;
        ArrayList<MenuOption> options = new ArrayList<MenuOption>();
        options.add(listOfBooksOption);
        menu = new Menu(options);
    }

    private void initializeLibrary() {
        library = new Library(
                new Printer(),
                new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123")
        );
    }

    private void initializeUsers() {
        ArrayList<User> users = new ArrayList<User>(
                Arrays.asList(
                        new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123"),
                        new User("123-5678", "Example user 2", "user2@email.com", "+55 12 1234 5678", "password321")
                )
        );

        auth = new Authentication(users);
    }

    @Before
    public void setUp() {
        initializeLibrary();
        initializeMenu();
        initializeUsers();

        BibliotecaApp.setLibrary(library);
        BibliotecaApp.setMenu(menu);
        BibliotecaApp.setAuthentication(auth);
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
    public void shouldPrintAnUnsuccessfulMessageWhenCheckoutBookInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.checkoutBookById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenReturnBookInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.returnBookById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenCheckoutMovieInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.checkoutMovieById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }

    @Test
    public void shouldPrintAnUnsuccessfulMessageWhenReturnMovieInputIsInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BibliotecaApp.returnMovieById("abc");

        assertThat(outContent.toString(), containsString(INVALID_CHECKOUT_MESSAGE));
    }
}