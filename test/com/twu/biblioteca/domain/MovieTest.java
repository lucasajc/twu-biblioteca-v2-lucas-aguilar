package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MovieTest {
    private Movie movie;

    @Before
    public void setUp() {
        movie = new Movie("Interstellar", 2014, "Christopher Nolan", 8.6);
    }

    @Test
    public void shouldHaveAnUUID() {
        assertThat(movie.getId(), is(notNullValue()));
    }

    @Test
    public void shouldHaveAName() {
        assertThat(movie.getName(), is("Interstellar"));
    }

    @Test
    public void shouldHaveAnYear() {
        assertThat(movie.getYear(), is(2014));
    }

    @Test
    public void shouldHaveADirector() {
        assertThat(movie.getDirector(), is("Christopher Nolan"));
    }

    @Test
    public void shouldHaveARating() {
        assertThat(movie.getRating(), is("8.6"));
    }

    @Test
    public void shouldHaveAUnratedStatusWhenItIsNotInformed() {
        movie = new Movie("Interstellar", 2014, "Christopher Nolan");

        assertThat(movie.getRating(), is("unrated"));
    }

    @Test
    public void shouldHaveAUnratedStatusWhenItIsInsertedANegativeValue() {
        movie = new Movie("Interstellar", 2014, "Christopher Nolan", -1);

        assertThat(movie.getRating(), is("unrated"));
    }

    @Test
    public void shouldHaveAUnratedStatusWhenItIsInsertedAnAboveLimitValue() {
        movie = new Movie("Interstellar", 2014, "Christopher Nolan", 10.1);

        assertThat(movie.getRating(), is("unrated"));
    }

    @Test
    public void shouldHaveACheckedOutInformation() {
        assertThat(movie.isCheckedOut(), is(false));
    }

    @Test
    public void shouldChangeCheckoutStatusToTrueWhenCheckout() {
        movie.checkout();

        assertThat(movie.isCheckedOut(), is(true));
    }

    @Test
    public void shouldChangeCheckoutStatusToFalseWhenDeliver() {
        movie.deliver();

        assertThat(movie.isCheckedOut(), is(false));
    }
}
