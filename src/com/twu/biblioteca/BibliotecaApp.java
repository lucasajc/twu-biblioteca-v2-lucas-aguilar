package com.twu.biblioteca;

import com.twu.biblioteca.domain.Library;
import com.twu.biblioteca.menu.Menu;
import com.twu.biblioteca.menu.MenuConstants;
import com.twu.biblioteca.menu.MenuOption;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class BibliotecaApp {
    private static Library library;
    private static Menu menu;

    private static final String INVALID_CHECKOUT_MESSAGE = "Please select a valid ID!";

    public static void main(String[] args) {
        menu = initializeMenu();
        library = new Library();

        printWelcomeMessage();
        run();
    }

    private static Menu initializeMenu() {
        ArrayList<MenuOption> options = new ArrayList<MenuOption>();
        options.add(MenuOption.LIST_BOOKS);
        options.add(MenuOption.CHECKOUT_BOOK);
        options.add(MenuOption.RETURN_BOOK);
        options.add(MenuOption.EXIT_APPLICATION);
        Menu menu = new Menu(options);

        return menu;
    }

    private static void listBooks() {
        library.listBooks();
    }

    private static void startBookCheckout() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> Insert a book ID to checkout it: ");
        String userInput = scanner.nextLine();

        checkoutBookById(userInput);
    }

    private static void startBookReturn() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> Insert a book ID to return it: ");
        String userInput = scanner.nextLine();

        returnBookById(userInput);
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

    static void printWelcomeMessage() {
        System.out.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore.");
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

    static void returnBookById(String userInput) {
        try {
            library.returnBookById(UUID.fromString(userInput));
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + INVALID_CHECKOUT_MESSAGE);
        }
    }

    static void processUserInput() {
        switch(menu.getSelectedOption()) {
            case MenuConstants.LIST_BOOKS_KEY:
                listBooks();
                break;
            case MenuConstants.CHECKOUT_BOOK_KEY:
                startBookCheckout();
                break;
            case MenuConstants.RETURN_BOOK_KEY:
                startBookReturn();
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
}
