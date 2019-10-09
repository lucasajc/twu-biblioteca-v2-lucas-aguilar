package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CheckoutItemTest {
    private User user;
    private Book book;
    private CheckoutItem checkoutItem;

    @Before
    public void setUp() {
        user = new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123");
        book = new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954);

        checkoutItem = new CheckoutItem(user, book);
    }

    @Test
    public void shouldHaveAnUser() {
        assertThat(checkoutItem.getUser(), is(user));
    }

    @Test
    public void shouldHaveALibraryItem() {
        assertThat(checkoutItem.getLibraryItem(), is(book));
    }
}
