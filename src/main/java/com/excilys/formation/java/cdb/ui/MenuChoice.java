package com.excilys.formation.java.cdb.ui;

public enum MenuChoice {
	COMPUTER_LIST (1, "List computers"),
	COMPANY_LIST (2, "List companies"),
	COMPUTER_DETAIL (3, "Computer details"),
	CREATE_COMPUTER (4, "Create computer"),
	UPDATE_COMPUTER (5, "Update computer"),
	DELETE_COMPUTER (6, "Delete computer"),
	QUIT(8, "Exit"),
	ERROR(9, "Error");
	
	private final int choice;
	private final String dialog;
	
	MenuChoice(int _choice, String _dialog) {
		this.choice = _choice;
		this.dialog = _dialog;
	}
	
	public int getChoice() {
		return this.choice;
	}
	
	public String getDialog() {
		return this.dialog;
	}
	
	public String toString() {
		return new StringBuilder().append(this.choice).append(". ").append(this.dialog).toString();
	}
	
	public static MenuChoice fromEntry(int entry) {
		switch(entry) {
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
			case 8:
				return QUIT;
			default:
				return ERROR;
		}
	}
}
