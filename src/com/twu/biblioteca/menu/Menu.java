package com.twu.biblioteca.menu;

import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuOption> options;
    private int selectedOption;
    private boolean isSelectedOptionValid;

    public Menu(ArrayList<MenuOption> options) {
        this.options = options;
        this.selectedOption = 0;
        this.isSelectedOptionValid = false;
    }

    public void show() {
        System.out.println("\n---------------- Menu ----------------");
        for(MenuOption option : this.options) {
            System.out.println("[" + option.getMenuKey() + "] " + option.getDescription());
        }
        System.out.println("--------------------------------------\n");
    }

    public void printMenuSelectUserInput() {
        System.out.print("> Select a menu option: ");
    }

    public void selectMenuOption(String userInput) {
        try {
            selectedOption = Integer.parseInt(userInput);
            for (MenuOption menuOption : MenuOption.values()) {
                if(menuOption.getMenuKey() == selectedOption) {
                    isSelectedOptionValid = true;
                    return;
                }
            }
            rejectInvalidOption();
        } catch(NumberFormatException e) {
            rejectInvalidOption();
        }
    }

    private void rejectInvalidOption() {
        System.out.println("\nPlease select a valid option!\n");
        isSelectedOptionValid = false;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public boolean isSelectedOptionValid() {
        return isSelectedOptionValid;
    }
}
