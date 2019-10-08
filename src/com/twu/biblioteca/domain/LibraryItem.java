package com.twu.biblioteca.domain;

import java.util.UUID;

public interface LibraryItem {
    public UUID getId();
    public String getType();
    public boolean isCheckedOut();
    public void checkout();
    public void deliver();
}
