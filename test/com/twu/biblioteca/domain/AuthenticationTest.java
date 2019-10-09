package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AuthenticationTest {
    private Authentication auth;

    @Before
    public void setUp() {
        ArrayList<User> users = new ArrayList<User>(
                Arrays.asList(
                        new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123"),
                        new User("123-5678", "Example user 2", "user2@email.com", "+55 12 1234 5678", "password321")
                )
        );

        auth = new Authentication(users);
    }

    @Test
    public void shouldHaveAListOfUsers() {
        assertThat(auth.getUsers().size(), is(2));
    }

    @Test
    public void shouldStartNotLoggedIn() {
        assertThat(auth.isLoggedIn(), is(false));
    }

    @Test
    public void shouldLoginWhenInsertCorrectCredentials() {
        auth.login("user1@email.com", "password123");

        assertThat(auth.isLoggedIn(), is(true));
    }

    @Test
    public void shouldLoginWhenInsertIncorrectCredentials() {
        auth.login("user1@email.com", "password1234");
        assertThat(auth.isLoggedIn(), is(false));

        auth.login("userXXX@email.com", "password123");
        assertThat(auth.isLoggedIn(), is(false));
    }
}
