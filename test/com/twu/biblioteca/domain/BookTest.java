package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class BookTest {
    private Book book;

    @Before
    public void setUp() {
        book = new Book("J. R. R. Tolkien", "The Lord of the Rings", 1954);
    }

    @Test
    public void shouldHaveAUUID() {
        assertThat(book.getId(), is(notNullValue()));
    }

    @Test
    public void shouldHaveATitle() {
        assertThat(book.getTitle(), is("The Lord of the Rings"));
    }

    @Test
    public void shouldHaveAnAuthor() {
        assertThat(book.getAuthor(), is("J. R. R. Tolkien"));
    }

    @Test
    public void shouldHaveAYear() {
        assertThat(book.getYear(), is(1954));
    }

    @Test
    public void shouldHaveABookType() {
        assertThat(book.getType(), is("book"));
    }

    @Test
    public void shouldChangeCheckoutStatusToTrueWhenCheckout() {
        book.checkout();

        assertThat(book.isCheckedOut(), is(true));
    }

    @Test
    public void shouldChangeCheckoutStatusToFalseWhenDeliver() {
        book.deliver();

        assertThat(book.isCheckedOut(), is(false));
    }
}
