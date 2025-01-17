package com.twu.biblioteca;

import com.twu.biblioteca.constants.LibraryItemTypes;
import com.twu.biblioteca.domain.*;
import com.twu.biblioteca.menu.Menu;
import com.twu.biblioteca.menu.MenuConstants;
import com.twu.biblioteca.menu.MenuOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class BibliotecaApp {
    private static Library library;
    private static Menu menu;
    private static Authentication authentication;
    private static Printer printer;

    private static final String INVALID_CHECKOUT_MESSAGE = "Please select a valid ID!";

    public static void main(String[] args) {
        menu = initializeMenu();
        authentication = initializeAuthentication();
        printer = new Printer();

        printer.printWelcomeMessage();

        while(!authentication.isLoggedIn()) {
            handleLogin();
            if(!authentication.isLoggedIn()) {
                printer.printInvalidLoginCredentialsMessage();
            }
        }

        library = new Library(new Printer(), authentication.getLoggedUser());

        run();
    }

    private static Menu initializeMenu() {
        ArrayList<MenuOption> options = new ArrayList<MenuOption>(
                Arrays.asList(
                    MenuOption.LIST_BOOKS,
                    MenuOption.LIST_MOVIES,
                    MenuOption.CHECKOUT_BOOK,
                    MenuOption.RETURN_BOOK,
                    MenuOption.CHECKOUT_MOVIE,
                    MenuOption.RETURN_MOVIE,
                    MenuOption.LIST_CHECKED_OUT_BOOKS,
                    MenuOption.LIST_CHECKED_OUT_MOVIES,
                    MenuOption.PRINT_USER_INFORMATION,
                    MenuOption.EXIT_APPLICATION
                )
        );

        menu = new Menu(options);

        return menu;
    }

    private static Authentication initializeAuthentication() {
        ArrayList<User> users = new ArrayList<User>(
                Arrays.asList(
                        new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "123"),
                        new User("123-5678", "Example user 2", "user2@email.com", "+55 12 1234 5678", "321")
                )
        );

        return new Authentication(users);
    }

    private static void handleLogin() {
        Scanner scanner = new Scanner(System.in);
        String usernameInput = "";
        String passwordInput = "";

        printer.printLoginHeader();

        printer.printLoginUsernameRequest();
        usernameInput = scanner.nextLine();

        printer.printLoginPasswordRequest();
        passwordInput = scanner.nextLine();

        authentication.login(usernameInput, passwordInput);
    }

    private static void startItemCheckout(String itemType) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> Insert a "+ itemType +" ID to checkout it: ");
        String userInput = scanner.nextLine();

        if(itemType.equals(LibraryItemTypes.BOOK)) {
            checkoutBookById(userInput);
            return;
        }
        checkoutMovieById(userInput);
    }

    private static void startItemReturn(String itemType) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> Insert a "+ itemType +" ID to return it: ");
        String userInput = scanner.nextLine();

        if(itemType.equals(LibraryItemTypes.BOOK)) {
            returnBookById(userInput);
            return;
        }
        returnMovieById(userInput);
    }

    private static void exitApplication() {
        System.exit(0);
    }

    private static void run() {
        menu.show();
        do {
            menu.printMenuSelectUserInput();
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            selectMenuOption(userInput);
        } while(!menu.isSelectedOptionValid());

        processUserInput();
        run();
    }

    static void selectMenuOption(String userInput) {
        menu.selectMenuOption(userInput);
    }

    static void checkoutBookById(String userInput) {
        try {
            library.checkoutBookById(UUID.fromString(userInput));
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + INVALID_CHECKOUT_MESSAGE);
        }
    }

    static void checkoutMovieById(String userInput) {
        try {
            library.checkoutMovieById(UUID.fromString(userInput));
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + INVALID_CHECKOUT_MESSAGE);
        }
    }

    static void returnBookById(String userInput) {
        try {
            library.returnBookById(UUID.fromString(userInput));
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + INVALID_CHECKOUT_MESSAGE);
        }
    }

    static void returnMovieById(String userInput) {
        try {
            library.returnMovieById(UUID.fromString(userInput));
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + INVALID_CHECKOUT_MESSAGE);
        }
    }

    static void processUserInput() {
        switch(menu.getSelectedOption()) {
            case MenuConstants.LIST_BOOKS_KEY:
                library.listBooks();
                break;
            case MenuConstants.LIST_MOVIES_KEY:
                library.listMovies();
                break;
            case MenuConstants.CHECKOUT_BOOK_KEY:
                startItemCheckout(LibraryItemTypes.BOOK);
                break;
            case MenuConstants.RETURN_BOOK_KEY:
                startItemReturn(LibraryItemTypes.BOOK);
                break;
            case MenuConstants.CHECKOUT_MOVIE_KEY:
                startItemCheckout(LibraryItemTypes.MOVIE);
                break;
            case MenuConstants.RETURN_MOVIE_KEY:
                startItemReturn(LibraryItemTypes.MOVIE);
                break;
            case MenuConstants.LIST_CHECKED_OUT_BOOKS_KEY:
                library.listCheckedOutBooks();
                break;
            case MenuConstants.LIST_CHECKED_OUT_MOVIES_KEY:
                library.listCheckedOutMovies();
                break;
            case MenuConstants.PRINT_USER_INFORMATION_KEY:
                printer.printUserInformation(authentication.getLoggedUser());
                break;
            case MenuConstants.EXIT_APPLICATION_KEY:
                exitApplication();
        }
    }

    static void setLibrary(Library library) {
        BibliotecaApp.library = library;
    }

    static void setMenu(Menu menu) {
        BibliotecaApp.menu = menu;
    }

    static void setAuthentication(Authentication authentication) {
        BibliotecaApp.authentication = authentication;
    }
}
