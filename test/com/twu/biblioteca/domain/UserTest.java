package com.twu.biblioteca.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("123-1234", "Example user 1", "user1@email.com", "+55 12 1234 1234", "password123");
    }

    @Test
    public void shouldHaveAFormattedId() {
        assertTrue(user.getId().matches("^[0-9]{3}-[0-9]{4}"));
    }

    @Test
    public void shouldHaveAName() {
        assertThat(user.getName(), is("Example user 1"));
    }

    @Test
    public void shouldHaveAnEmail() {
        assertThat(user.getEmail(), is("user1@email.com"));
    }

    @Test
    public void shouldHaveAPhoneNumber() {
        assertThat(user.getPhoneNumber(), is("+55 12 1234 1234"));
    }

    @Test
    public void shouldHaveAPassword() {
        assertThat(user.getPassword(), is("password123"));
    }
}
