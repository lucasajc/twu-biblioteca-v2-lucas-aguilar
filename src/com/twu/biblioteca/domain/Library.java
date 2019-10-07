package com.twu.biblioteca.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class Library {
    private HashMap<UUID, Book> books;
    private static final String LIST_FORMAT = "%-25s%25s%25s%25s%n";
    private static final String[] LIST_HEADER = new String[] { "Id", "Author", "Title", "Year published" };
    private static final String SUCCESS_CHECKOUT_MESSAGE = "\nThank you! Enjoy the book.";
    private static final String UN_SUCCESS_CHECKOUT_MESSAGE = "Sorry, that book is not available.";
    private static final String SUCCESS_RETURN_MESSAGE = "Thank you for returning the book.";
    private static final String UN_SUCCESS_RETURN_MESSAGE = "This is not a valid book to return.";

    public Library(ArrayList<Book> bookList) {
        fillBookCollection(bookList);
    }

    public Library() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList = new ArrayList<Book>();
        bookList.add(new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954));
        bookList.add(new Book("J. R. R. Tolkien", "The Hobbit", 1937));
        bookList.add(new Book("J. R. R. Tolkien", "The Silmarillion", 1977));
        bookList.add(new Book("J. R. R. Tolkien", "The Fall of Gondolin", 2018));
        bookList.add(new Book("Conn Iggulden", "Wolf of the Plains", 2007));
        bookList.add(new Book("Conn Iggulden", "Lords of the Bow", 2008));
        bookList.add(new Book("Conn Iggulden", "Bones of the Hills", 2008));
        bookList.add(new Book("Conn Iggulden", "Conqueror", 2011));
        bookList.add(new Book("George R. R. Martin", "A Game of Thrones", 1996));
        bookList.add(new Book("George R. R. Martin", "A Clash of Kings", 1999));
        bookList.add(new Book("George R. R. Martin", "A Storm of Swords", 2000));
        bookList.add(new Book("George R. R. Martin", "A Feast for Crows", 2005));
        bookList.add(new Book("George R. R. Martin", "A Dance with Dragons", 2011));

        fillBookCollection(bookList);
    }

    private void fillBookCollection(ArrayList<Book> bookList) {
        this.books = new HashMap<UUID, Book>();

        for (Book book : bookList) {
            this.books.put(book.getId(), book);
        }
    }

    public void listBooks() {
        System.out.println("\nList of books:\n");
        System.out.format(LIST_FORMAT, LIST_HEADER);

        for(Book book : getAvailableBooks()) {
            System.out.format(LIST_FORMAT,
                    book.getId(),
                    book.getAuthor(),
                    book.getTitle(),
                    book.getYear());
        }
    }

    boolean isBookExists(UUID id) {
        return books.get(id) != null;
    }

    public void checkoutBookById(UUID id) {
        if(!isBookExists(id) || books.get(id).isCheckedOut()) {
            System.out.println(UN_SUCCESS_CHECKOUT_MESSAGE);
            return;
        }
        books.get(id).setCheckedOut(true);
        System.out.println(SUCCESS_CHECKOUT_MESSAGE);
    }

    public void returnBookById(UUID id) {
        if(!isBookExists(id) || !books.get(id).isCheckedOut()) {
            System.out.println(UN_SUCCESS_RETURN_MESSAGE);
            return;
        }
        books.get(id).setCheckedOut(false);
        System.out.println(SUCCESS_RETURN_MESSAGE);
    }

    public ArrayList<Book> getAvailableBooks() {
        return (ArrayList<Book>) books.values().stream()
                .filter(book -> !book.isCheckedOut())
                .collect(Collectors.toList());
    }

    public HashMap<UUID, Book> getBooks() {
        return books;
    }
}
