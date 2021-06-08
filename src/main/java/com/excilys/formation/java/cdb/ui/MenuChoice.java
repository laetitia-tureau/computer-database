package com.excilys.formation.java.cdb.ui;

/** Represents choices in a menu.
 * @author Laetitia Tureau
 */
public enum MenuChoice {
    COMPUTER_LIST(1, "List computers"), COMPANY_LIST(2, "List companies"), COMPUTER_DETAIL(3, "Computer details"),
    CREATE_COMPUTER(4, "Create computer"), UPDATE_COMPUTER(5, "Update computer"), DELETE_COMPUTER(6, "Delete computer"),
    DELETE_COMPANY(7, "Delete company"), COMPUTER_NAME(1, "Computer's name"),
    INTRODUCED_DATE(2, "Computer's introduced date"), DISCONTINUED_DATE(3, "Computer's discontinued date"),
    MANUFACTURER(4, "Computer's company"), QUIT(0, "Exit"), ERROR(9, "Error");

    private final int choice;
    private final String dialog;

    /** Creates a company with the specified id and message.
     * @param menuChoice The choice's number
     * @param dialogChoice The choice's message
     */
    MenuChoice(int menuChoice, String dialogChoice) {
        this.choice = menuChoice;
        this.dialog = dialogChoice;
    }

    /**
     * Gets the choice's number.
     * @return this.choice
     */
    public int getChoice() {
        return this.choice;
    }

    /**
     * Gets the choice's message.
     * @return this.dialog
     */
    public String getDialog() {
        return this.dialog;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.choice).append(". ").append(this.dialog).toString();
    }

    /**
     * Retrieve a choice in main manu corresponding to the entry.
     * @param entry A given choice's number
     * @return A MenuChoice resulting
     */
    public static MenuChoice fromEntry(int entry) {
        switch (entry) {
        case 1:
            return COMPUTER_LIST;
        case 2:
            return COMPANY_LIST;
        case 3:
            return COMPUTER_DETAIL;
        case 4:
            return CREATE_COMPUTER;
        case 5:
            return UPDATE_COMPUTER;
        case 6:
            return DELETE_COMPUTER;
        case 7:
            return DELETE_COMPANY;
        case 0:
            return QUIT;
        default:
            return ERROR;
        }
    }

    /**
     * Retrieve a choice in update menu corresponding to the entry.
     * @param entry A given choice's number
     * @return A MenuChoice resulting
     */
    public static MenuChoice fromEntryUpdate(int entry) {
        switch (entry) {
        case 1:
            return COMPUTER_NAME;
        case 2:
            return INTRODUCED_DATE;
        case 3:
            return DISCONTINUED_DATE;
        case 4:
            return MANUFACTURER;
        case 0:
            return QUIT;
        default:
            return ERROR;
        }
    }
}
